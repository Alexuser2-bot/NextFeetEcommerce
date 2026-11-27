package com.nextfeet.nextfeet.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Categoria;

@Repository
public class CategoriaRepository implements CategoriaDAO {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Categoria> mapper = (rs, rowNum) -> {
        Categoria c = new Categoria();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setActivo(rs.getBoolean("activo"));
        c.setTotalZapatillas(rs.getInt("total_zapatillas"));
        return c;
    };

    private static final String SELECT =
            "SELECT c.id, c.nombre, c.descripcion, c.activo, " +
            "(SELECT COUNT(*) FROM zapatilla z WHERE z.id_categoria = c.id) AS total_zapatillas " +
            "FROM categoria c ";

    @Override
    public List<Categoria> listarTodas() {
        return jdbcTemplate.query(SELECT + "ORDER BY c.nombre", mapper);
    }

    @Override
    public Categoria buscarPorId(int id) {
        List<Categoria> r = jdbcTemplate.query(SELECT + "WHERE c.id = ?", mapper, id);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public Categoria buscarPorNombre(String nombre) {
        List<Categoria> r = jdbcTemplate.query(SELECT + "WHERE LOWER(c.nombre) = LOWER(?)", mapper, nombre);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public int crear(Categoria c) {
        jdbcTemplate.update("INSERT INTO categoria (nombre, descripcion, activo) VALUES (?, ?, ?)",
                c.getNombre(), c.getDescripcion() == null ? "" : c.getDescripcion(), true);
        return jdbcTemplate.queryForObject("SELECT id FROM categoria WHERE nombre = ?", Integer.class, c.getNombre());
    }

    @Override
    public void actualizar(Categoria c) {
        jdbcTemplate.update("UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?",
                c.getNombre(), c.getDescripcion(), c.getId());
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        jdbcTemplate.update("UPDATE categoria SET activo = ? WHERE id = ?", activo, id);
    }

    @Override
    public void eliminar(int id) {
        jdbcTemplate.update("DELETE FROM categoria WHERE id = ?", id);
    }

    @Override
    public long contarZapatillas(int idCategoria) {
        Long n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM zapatilla WHERE id_categoria = ?", Long.class,
                idCategoria);
        return n == null ? 0 : n;
    }

}
