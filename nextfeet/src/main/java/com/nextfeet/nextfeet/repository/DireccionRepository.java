package com.nextfeet.nextfeet.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Direccion;

@Repository
public class DireccionRepository implements DireccionDAO {

    private final JdbcTemplate jdbcTemplate;

    public DireccionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Direccion> mapper = (rs, rowNum) -> {
        Direccion d = new Direccion();
        d.setId(rs.getInt("id"));
        d.setIdUsuario(rs.getInt("id_usuario"));
        d.setDepartamento(rs.getString("departamento"));
        d.setProvincia(rs.getString("provincia"));
        d.setDistrito(rs.getString("distrito"));
        d.setCalle(rs.getString("calle"));
        d.setPrincipal(rs.getBoolean("principal"));
        return d;
    };

    private static final String SELECT = "SELECT id, id_usuario, departamento, provincia, distrito, calle, principal FROM direccion ";

    @Override
    public List<Direccion> listarPorUsuario(int idUsuario) {
        // La principal aparece primero
        return jdbcTemplate.query(SELECT + "WHERE id_usuario = ? ORDER BY principal DESC, id", mapper, idUsuario);
    }

    @Override
    public Direccion buscarPorId(int id) {
        List<Direccion> r = jdbcTemplate.query(SELECT + "WHERE id = ?", mapper, id);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public void crear(Direccion d) {
        jdbcTemplate.update(
                "INSERT INTO direccion (id_usuario, departamento, provincia, distrito, calle, principal) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                d.getIdUsuario(), d.getDepartamento(), d.getProvincia(), d.getDistrito(), d.getCalle(), d.isPrincipal());
    }

    @Override
    public void actualizar(Direccion d) {
        jdbcTemplate.update(
                "UPDATE direccion SET departamento = ?, provincia = ?, distrito = ?, calle = ? WHERE id = ?",
                d.getDepartamento(), d.getProvincia(), d.getDistrito(), d.getCalle(), d.getId());
    }

    @Override
    public void quitarPrincipal(int idUsuario) {
        jdbcTemplate.update("UPDATE direccion SET principal = FALSE WHERE id_usuario = ?", idUsuario);
    }

    @Override
    public void marcarPrincipal(int id) {
        jdbcTemplate.update("UPDATE direccion SET principal = TRUE WHERE id = ?", id);
    }

}
