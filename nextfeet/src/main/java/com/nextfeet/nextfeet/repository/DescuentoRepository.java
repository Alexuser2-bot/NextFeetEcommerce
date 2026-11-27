package com.nextfeet.nextfeet.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Descuento;
import com.nextfeet.nextfeet.model.Zapatilla;

@Repository
public class DescuentoRepository implements DescuentoDAO {

    private final JdbcTemplate jdbcTemplate;

    public DescuentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT =
            "SELECT d.id, d.nombre, d.porcentaje, d.fecha_inicio, d.fecha_fin, d.activo, " +
            "(SELECT COUNT(*) FROM zapatilla z WHERE z.id_descuento = d.id) AS cantidad_productos " +
            "FROM descuento d ";

    private final RowMapper<Descuento> mapper = (rs, rowNum) -> {
        Descuento d = new Descuento();
        d.setId(rs.getInt("id"));
        d.setNombre(rs.getString("nombre"));
        d.setPorcentaje(rs.getBigDecimal("porcentaje"));
        d.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        d.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        d.setActivo(rs.getBoolean("activo"));
        d.setCantidadProductos(rs.getInt("cantidad_productos"));
        return d;
    };

    @Override
    public List<Descuento> listar() {
        return jdbcTemplate.query(SELECT + "ORDER BY d.id DESC", mapper);
    }

    @Override
    public Descuento buscarPorId(int id) {
        List<Descuento> r = jdbcTemplate.query(SELECT + "WHERE d.id = ?", mapper, id);
        if (r.isEmpty()) {
            return null;
        }
        Descuento d = r.get(0);
        d.setIdsZapatillas(jdbcTemplate.queryForList("SELECT id FROM zapatilla WHERE id_descuento = ?", Integer.class, id));
        return d;
    }

    @Override
    public Descuento buscarPorNombre(String nombre) {
        List<Descuento> r = jdbcTemplate.query(SELECT + "WHERE LOWER(d.nombre) = LOWER(?)", mapper, nombre);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public int crear(Descuento d) {
        jdbcTemplate.update(
                "INSERT INTO descuento (nombre, porcentaje, fecha_inicio, fecha_fin, activo) VALUES (?, ?, ?, ?, TRUE)",
                d.getNombre(), d.getPorcentaje(), Date.valueOf(d.getFechaInicio()), Date.valueOf(d.getFechaFin()));
        return jdbcTemplate.queryForObject("SELECT id FROM descuento WHERE nombre = ?", Integer.class, d.getNombre());
    }

    @Override
    public void actualizar(Descuento d) {
        jdbcTemplate.update(
                "UPDATE descuento SET nombre = ?, porcentaje = ?, fecha_inicio = ?, fecha_fin = ?, activo = ? WHERE id = ?",
                d.getNombre(), d.getPorcentaje(), Date.valueOf(d.getFechaInicio()), Date.valueOf(d.getFechaFin()),
                d.isActivo(), d.getId());
    }

    @Override
    public void asignarZapatillas(int idDescuento, List<Integer> idsZapatillas) {
        jdbcTemplate.update("UPDATE zapatilla SET id_descuento = NULL WHERE id_descuento = ?", idDescuento);
        for (Integer idZapatilla : idsZapatillas) {
            jdbcTemplate.update("UPDATE zapatilla SET id_descuento = ? WHERE id = ?", idDescuento, idZapatilla);
        }
    }

    @Override
    public List<Zapatilla> listarZapatillas() {
        return jdbcTemplate.query("SELECT id, nombre, marca FROM zapatilla ORDER BY nombre", (rs, rowNum) -> {
            Zapatilla z = new Zapatilla();
            z.setId(rs.getInt("id"));
            z.setNombre(rs.getString("nombre"));
            z.setMarca(rs.getString("marca"));
            return z;
        });
    }

}
