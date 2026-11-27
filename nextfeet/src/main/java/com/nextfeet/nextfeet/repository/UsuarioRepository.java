package com.nextfeet.nextfeet.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.util.Pagina;

@Repository
public class UsuarioRepository implements UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombres(rs.getString("nombres"));
        u.setApellidos(rs.getString("apellidos"));
        u.setCorreo(rs.getString("correo"));
        u.setPassword(rs.getString("password"));
        u.setTelefono(rs.getString("telefono"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        u.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
        return u;
    };

    private static final String SELECT =
            "SELECT id, nombres, apellidos, correo, password, telefono, rol, activo, fecha_registro FROM usuario ";

    @Override
    public Usuario buscarPorId(int id) {
        List<Usuario> r = jdbcTemplate.query(SELECT + "WHERE id = ?", usuarioRowMapper, id);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        List<Usuario> r = jdbcTemplate.query(SELECT + "WHERE LOWER(correo) = LOWER(?)", usuarioRowMapper, correo);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public int crear(Usuario u) {
        jdbcTemplate.update(
                "INSERT INTO usuario (nombres, apellidos, correo, password, telefono, rol, activo, fecha_registro) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                u.getNombres(), u.getApellidos(), u.getCorreo(), u.getPassword(),
                u.getTelefono() == null ? "" : u.getTelefono(), u.getRol(), u.isActivo(),
                java.sql.Date.valueOf(LocalDate.now()));
        return jdbcTemplate.queryForObject("SELECT id FROM usuario WHERE correo = ?", Integer.class, u.getCorreo());
    }

    @Override
    public void actualizarDatos(Usuario u) {
        jdbcTemplate.update("UPDATE usuario SET nombres = ?, apellidos = ?, telefono = ? WHERE id = ?",
                u.getNombres(), u.getApellidos(), u.getTelefono(), u.getId());
    }

    @Override
    public void actualizarPassword(int id, String passwordCifrada) {
        jdbcTemplate.update("UPDATE usuario SET password = ? WHERE id = ?", passwordCifrada, id);
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        jdbcTemplate.update("UPDATE usuario SET activo = ? WHERE id = ?", activo, id);
    }

    @Override
    public List<Usuario> listarPorRol(String rol) {
        return jdbcTemplate.query(SELECT + "WHERE rol = ? ORDER BY id", usuarioRowMapper, rol);
    }

    @Override
    public long contarActivosPorRol(String rol) {
        Long n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario WHERE rol = ? AND activo = TRUE",
                Long.class, rol);
        return n == null ? 0 : n;
    }

    @Override
    public Pagina<Usuario> listarPorRolPaginado(String rol, String busqueda, Boolean activo, int pagina, int tamano) {
        StringBuilder where = new StringBuilder("WHERE rol = ? ");
        List<Object> params = new ArrayList<>();
        params.add(rol);
        if (busqueda != null && !busqueda.isBlank()) {
            where.append("AND (LOWER(nombres) LIKE ? OR LOWER(apellidos) LIKE ? OR LOWER(correo) LIKE ?) ");
            String like = "%" + busqueda.trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (activo != null) {
            where.append("AND activo = ? ");
            params.add(activo);
        }
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario " + where, Long.class, params.toArray());

        List<Object> paramsPagina = new ArrayList<>(params);
        paramsPagina.add(tamano);
        paramsPagina.add((pagina - 1) * tamano);
        List<Usuario> contenido = jdbcTemplate.query(SELECT + where + "ORDER BY id LIMIT ? OFFSET ?",
                usuarioRowMapper, paramsPagina.toArray());
        return new Pagina<>(contenido, pagina, tamano, total == null ? 0 : total);
    }

}
