package com.nextfeet.nextfeet.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Marca;

@Repository
public class MarcaRepository implements MarcaDAO {

    private final JdbcTemplate jdbcTemplate;

    public MarcaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Marca> mapper = (rs, rowNum) -> {
        Marca m = new Marca();
        m.setId(rs.getInt("id"));
        m.setNombre(rs.getString("nombre"));
        return m;
    };

    @Override
    public List<Marca> listar(String busqueda) {
        if (busqueda == null || busqueda.isBlank()) {
            return jdbcTemplate.query("SELECT id, nombre FROM marca ORDER BY nombre", mapper);
        }
        return jdbcTemplate.query("SELECT id, nombre FROM marca WHERE LOWER(nombre) LIKE ? ORDER BY nombre",
                mapper, "%" + busqueda.trim().toLowerCase() + "%");
    }

    @Override
    public Marca buscarPorId(int id) {
        List<Marca> r = jdbcTemplate.query("SELECT id, nombre FROM marca WHERE id = ?", mapper, id);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public Marca buscarPorNombre(String nombre) {
        List<Marca> r = jdbcTemplate.query("SELECT id, nombre FROM marca WHERE LOWER(nombre) = LOWER(?)", mapper, nombre);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public void crear(String nombre) {
        jdbcTemplate.update("INSERT INTO marca (nombre) VALUES (?)", nombre);
    }

    @Override
    public void actualizar(int id, String nombre) {
        jdbcTemplate.update("UPDATE marca SET nombre = ? WHERE id = ?", nombre, id);
    }

    @Override
    public void renombrarEnZapatillas(String nombreAnterior, String nombreNuevo) {
        // Las zapatillas guardan el nombre de la marca, por eso se actualiza también ahí
        jdbcTemplate.update("UPDATE zapatilla SET marca = ? WHERE marca = ?", nombreNuevo, nombreAnterior);
    }

}
