package com.nextfeet.nextfeet.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.Descuento;


@Repository
public class CarritoRepository implements CarritoDAO {

    private final JdbcTemplate jdbcTemplate;

    public CarritoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int obtenerOCrearCarrito(int idUsuario) {
        List<Integer> r = jdbcTemplate.queryForList(
                "SELECT id FROM carrito WHERE id_usuario = ?", Integer.class, idUsuario);
        if (!r.isEmpty()) {
            return r.get(0);
        }
        jdbcTemplate.update("INSERT INTO carrito (id_usuario, fecha_actualizacion) VALUES (?, ?)",
                idUsuario, java.sql.Timestamp.valueOf(LocalDateTime.now()));
        return jdbcTemplate.queryForObject("SELECT id FROM carrito WHERE id_usuario = ?", Integer.class, idUsuario);
    }

    private final RowMapper<CarritoItem> itemMapper = (rs, rowNum) -> {
        CarritoItem i = new CarritoItem();
        i.setId(rs.getInt("id"));
        i.setIdCarrito(rs.getInt("id_carrito"));
        i.setIdZapatilla(rs.getInt("id_zapatilla"));
        i.setTalla(rs.getString("talla"));
        i.setCantidad(rs.getInt("cantidad"));
        i.setNombre(rs.getString("nombre"));
        i.setMarca(rs.getString("marca"));
        i.setImagenUrl(rs.getString("imagen_url"));
        // El cliente paga el precio con el descuento de la zapatilla (si tiene uno vigente)
        i.setPrecioUnitario(Descuento.aplicar(rs.getBigDecimal("precio"), rs.getBigDecimal("pct_descuento")));
        i.setStockDisponible(rs.getInt("stock"));
        return i;
    };

    @Override
    public Carrito obtenerCarrito(int idUsuario) {
        int idCarrito = obtenerOCrearCarrito(idUsuario);
        List<CarritoItem> items = jdbcTemplate.query(
                "SELECT ci.id, ci.id_carrito, ci.id_zapatilla, ci.talla, ci.cantidad, " +
                        "z.nombre, z.marca, z.imagen_url, z.precio, " +
                        "CASE WHEN d.activo = TRUE AND CURRENT_DATE BETWEEN d.fecha_inicio AND d.fecha_fin " +
                        "THEN d.porcentaje ELSE 0 END AS pct_descuento, " +
                        "COALESCE((SELECT t.stock FROM zapatilla_talla t WHERE t.id_zapatilla = z.id AND t.talla = ci.talla), 0) AS stock " +
                        "FROM carrito_item ci JOIN zapatilla z ON z.id = ci.id_zapatilla " +
                        "LEFT JOIN descuento d ON d.id = z.id_descuento " +
                        "WHERE ci.id_carrito = ? ORDER BY ci.id",
                itemMapper, idCarrito);
        Carrito c = new Carrito();
        c.setId(idCarrito);
        c.setIdUsuario(idUsuario);
        c.setItems(items);
        return c;
    }

    @Override
    public void agregarOIncrementarItem(int idCarrito, int idZapatilla, String talla, int cantidad) {
        int existentes = contarItems(idCarrito, idZapatilla, talla);
        if (existentes > 0) {
            jdbcTemplate.update(
                    "UPDATE carrito_item SET cantidad = cantidad + ? WHERE id_carrito = ? AND id_zapatilla = ? AND talla = ?",
                    cantidad, idCarrito, idZapatilla, talla);
        } else {
            jdbcTemplate.update(
                    "INSERT INTO carrito_item (id_carrito, id_zapatilla, talla, cantidad) VALUES (?, ?, ?, ?)",
                    idCarrito, idZapatilla, talla, cantidad);
        }
        tocar(idCarrito);
    }

    @Override
    public void actualizarCantidad(int idCarrito, int idZapatilla, String talla, int cantidad) {
        jdbcTemplate.update(
                "UPDATE carrito_item SET cantidad = ? WHERE id_carrito = ? AND id_zapatilla = ? AND talla = ?",
                cantidad, idCarrito, idZapatilla, talla);
        tocar(idCarrito);
    }

    @Override
    public void eliminarItem(int idCarrito, int idZapatilla, String talla) {
        jdbcTemplate.update("DELETE FROM carrito_item WHERE id_carrito = ? AND id_zapatilla = ? AND talla = ?",
                idCarrito, idZapatilla, talla);
        tocar(idCarrito);
    }

    @Override
    public void vaciar(int idCarrito) {
        jdbcTemplate.update("DELETE FROM carrito_item WHERE id_carrito = ?", idCarrito);
        tocar(idCarrito);
    }

    @Override
    public int contarItems(int idCarrito, int idZapatilla, String talla) {
        Long n = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM carrito_item WHERE id_carrito = ? AND id_zapatilla = ? AND talla = ?",
                Long.class, idCarrito, idZapatilla, talla);
        return n == null ? 0 : n.intValue();
    }

    @Override
    public int cantidadEnItem(int idCarrito, int idZapatilla, String talla) {
        List<Integer> r = jdbcTemplate.queryForList(
                "SELECT cantidad FROM carrito_item WHERE id_carrito = ? AND id_zapatilla = ? AND talla = ?",
                Integer.class, idCarrito, idZapatilla, talla);
        return r.isEmpty() ? 0 : r.get(0);
    }

    private void tocar(int idCarrito) {
        jdbcTemplate.update("UPDATE carrito SET fecha_actualizacion = ? WHERE id = ?",
                java.sql.Timestamp.valueOf(LocalDateTime.now()), idCarrito);
    }

}
