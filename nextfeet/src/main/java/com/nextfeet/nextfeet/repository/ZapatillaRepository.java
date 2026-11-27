package com.nextfeet.nextfeet.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.dto.FiltroCatalogo;
import com.nextfeet.nextfeet.model.HistorialStock;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.util.Pagina;

@Repository
public class ZapatillaRepository implements ZapatillaDAO {

    private final JdbcTemplate jdbcTemplate;

    public ZapatillaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_BASE =
            "SELECT z.id, z.sku, z.nombre, z.marca, z.descripcion, z.materiales, z.color, z.precio, " +
            "z.imagen_url, z.id_categoria, cat.nombre AS nombre_categoria, z.activo, z.fecha_creacion, " +
            "COALESCE((SELECT SUM(t.stock) FROM zapatilla_talla t WHERE t.id_zapatilla = z.id), 0) AS stock_total, " +
            // El descuento solo cuenta si está activo y hoy está dentro de sus fechas
            "CASE WHEN d.activo = TRUE AND CURRENT_DATE BETWEEN d.fecha_inicio AND d.fecha_fin " +
            "THEN d.porcentaje ELSE 0 END AS pct_descuento " +
            "FROM zapatilla z " +
            "JOIN categoria cat ON cat.id = z.id_categoria " +
            "LEFT JOIN descuento d ON d.id = z.id_descuento ";

    private final RowMapper<Zapatilla> mapper = (rs, rowNum) -> {
        Zapatilla z = new Zapatilla();
        z.setId(rs.getInt("id"));
        z.setSku(rs.getString("sku"));
        z.setNombre(rs.getString("nombre"));
        z.setMarca(rs.getString("marca"));
        z.setDescripcion(rs.getString("descripcion"));
        z.setMateriales(rs.getString("materiales"));
        z.setColor(rs.getString("color"));
        BigDecimal precio = rs.getBigDecimal("precio");
        z.setPrecio(precio);
        z.setImagenUrl(rs.getString("imagen_url"));
        z.setIdCategoria(rs.getInt("id_categoria"));
        z.setNombreCategoria(rs.getString("nombre_categoria"));
        z.setActivo(rs.getBoolean("activo"));
        z.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        z.setStockTotal(rs.getInt("stock_total"));
        z.setPorcentajeDescuento(rs.getBigDecimal("pct_descuento"));
        return z;
    };

    @Override
    public Zapatilla buscarPorId(int id) {
        List<Zapatilla> r = jdbcTemplate.query(SELECT_BASE + " WHERE z.id = ?", mapper, id);
        if (r.isEmpty()) {
            return null;
        }
        Zapatilla z = r.get(0);
        z.setTallas(listarTallas(id));
        return z;
    }

    @Override
    public Pagina<Zapatilla> buscarCatalogo(FiltroCatalogo f, int tamano, boolean soloActivas) {
        StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if (soloActivas) {
            where.append("AND z.activo = TRUE ");
        }
        if (f.getQ() != null && !f.getQ().isBlank()) {
            where.append("AND (LOWER(z.nombre) LIKE ? OR LOWER(z.marca) LIKE ?) ");
            String like = "%" + f.getQ().trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
        }
        if (f.getCategoriaId() != null) {
            where.append("AND z.id_categoria = ? ");
            params.add(f.getCategoriaId());
        }
        if (f.getMarca() != null && !f.getMarca().isBlank()) {
            where.append("AND LOWER(z.marca) = LOWER(?) ");
            params.add(f.getMarca());
        }
        if (f.getPrecioMin() != null) {
            where.append("AND z.precio >= ? ");
            params.add(f.getPrecioMin());
        }
        if (f.getPrecioMax() != null) {
            where.append("AND z.precio <= ? ");
            params.add(f.getPrecioMax());
        }
        if (f.getTalla() != null && !f.getTalla().isBlank()) {
            where.append("AND EXISTS (SELECT 1 FROM zapatilla_talla t WHERE t.id_zapatilla = z.id ")
                    .append("AND t.talla = ? AND t.stock > 0) ");
            params.add(f.getTalla());
        }
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM zapatilla z " + where, Long.class,
                params.toArray());

        int pagina = Math.max(f.getPagina(), 1);
        List<Object> paramsPagina = new ArrayList<>(params);
        paramsPagina.add(tamano);
        paramsPagina.add((pagina - 1) * tamano);
        List<Zapatilla> contenido = jdbcTemplate.query(
                SELECT_BASE + where + "ORDER BY z.id DESC LIMIT ? OFFSET ?", mapper, paramsPagina.toArray());
        return new Pagina<>(contenido, pagina, tamano, total == null ? 0 : total);
    }

    @Override
    public Pagina<Zapatilla> buscarInventario(String termino, int pagina, int tamano) {
        StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if (termino != null && !termino.isBlank()) {
            where.append("AND (LOWER(z.nombre) LIKE ? OR LOWER(z.sku) LIKE ?) ");
            String like = "%" + termino.trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
        }
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM zapatilla z " + where, Long.class,
                params.toArray());
        pagina = Math.max(pagina, 1);
        List<Object> paramsPagina = new ArrayList<>(params);
        paramsPagina.add(tamano);
        paramsPagina.add((pagina - 1) * tamano);
        List<Zapatilla> contenido = jdbcTemplate.query(
                SELECT_BASE + where + "ORDER BY z.id DESC LIMIT ? OFFSET ?", mapper, paramsPagina.toArray());
        return new Pagina<>(contenido, pagina, tamano, total == null ? 0 : total);
    }

    @Override
    public List<String> listarMarcas() {
        return jdbcTemplate.queryForList(
                "SELECT DISTINCT marca FROM zapatilla WHERE activo = TRUE ORDER BY marca", String.class);
    }

    @Override
    public int crear(Zapatilla z) {
        jdbcTemplate.update(
                "INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, " +
                        "id_categoria, activo, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                z.getSku(), z.getNombre(), z.getMarca(), z.getDescripcion(), z.getMateriales(), z.getColor(),
                z.getPrecio(), z.getImagenUrl(), z.getIdCategoria(), true, java.sql.Date.valueOf(LocalDate.now()));
        int id = jdbcTemplate.queryForObject("SELECT id FROM zapatilla WHERE sku = ?", Integer.class, z.getSku());
        if (z.getTallas() != null) {
            guardarTallas(id, z.getTallas());
        }
        return id;
    }

    @Override
    public void actualizar(Zapatilla z) {
        jdbcTemplate.update(
                "UPDATE zapatilla SET nombre = ?, marca = ?, descripcion = ?, materiales = ?, color = ?, " +
                        "precio = ?, imagen_url = ?, id_categoria = ? WHERE id = ?",
                z.getNombre(), z.getMarca(), z.getDescripcion(), z.getMateriales(), z.getColor(), z.getPrecio(),
                z.getImagenUrl(), z.getIdCategoria(), z.getId());
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        jdbcTemplate.update("UPDATE zapatilla SET activo = ? WHERE id = ?", activo, id);
    }

    @Override
    public void eliminar(int id) {
        jdbcTemplate.update("DELETE FROM zapatilla WHERE id = ?", id);
    }

    @Override
    public boolean tienePedidosAsociados(int id) {
        Long n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM detalle_pedido WHERE id_zapatilla = ?",
                Long.class, id);
        return n != null && n > 0;
    }

    @Override
    public List<ZapatillaTalla> listarTallas(int idZapatilla) {
        return jdbcTemplate.query(
                "SELECT id, id_zapatilla, talla, stock FROM zapatilla_talla WHERE id_zapatilla = ? ORDER BY talla",
                (rs, rowNum) -> {
                    ZapatillaTalla t = new ZapatillaTalla();
                    t.setId(rs.getInt("id"));
                    t.setIdZapatilla(rs.getInt("id_zapatilla"));
                    t.setTalla(rs.getString("talla"));
                    t.setStock(rs.getInt("stock"));
                    return t;
                }, idZapatilla);
    }

    @Override
    public ZapatillaTalla buscarTalla(int idZapatilla, String talla) {
        List<ZapatillaTalla> r = jdbcTemplate.query(
                "SELECT id, id_zapatilla, talla, stock FROM zapatilla_talla WHERE id_zapatilla = ? AND talla = ?",
                (rs, rowNum) -> {
                    ZapatillaTalla t = new ZapatillaTalla();
                    t.setId(rs.getInt("id"));
                    t.setIdZapatilla(rs.getInt("id_zapatilla"));
                    t.setTalla(rs.getString("talla"));
                    t.setStock(rs.getInt("stock"));
                    return t;
                }, idZapatilla, talla);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public void guardarTallas(int idZapatilla, List<ZapatillaTalla> tallas) {
        jdbcTemplate.update("DELETE FROM zapatilla_talla WHERE id_zapatilla = ?", idZapatilla);
        for (ZapatillaTalla t : tallas) {
            jdbcTemplate.update("INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (?, ?, ?)",
                    idZapatilla, t.getTalla(), t.getStock());
        }
    }

    @Override
    public void actualizarStock(int idZapatilla, String talla, int nuevoStock) {
        jdbcTemplate.update("UPDATE zapatilla_talla SET stock = ? WHERE id_zapatilla = ? AND talla = ?",
                nuevoStock, idZapatilla, talla);
    }

    @Override
    public void registrarHistorialStock(int idZapatilla, String talla, int anterior, int nuevo, String motivo) {
        jdbcTemplate.update(
                "INSERT INTO historial_stock (id_zapatilla, talla, cantidad_anterior, cantidad_nueva, motivo, fecha) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                idZapatilla, talla, anterior, nuevo, motivo, java.sql.Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public List<HistorialStock> listarHistorialStock(int idZapatilla) {
        return jdbcTemplate.query(
                "SELECT id, id_zapatilla, talla, cantidad_anterior, cantidad_nueva, motivo, fecha " +
                        "FROM historial_stock WHERE id_zapatilla = ? ORDER BY fecha DESC",
                (rs, rowNum) -> {
                    HistorialStock h = new HistorialStock();
                    h.setId(rs.getInt("id"));
                    h.setIdZapatilla(rs.getInt("id_zapatilla"));
                    h.setTalla(rs.getString("talla"));
                    h.setCantidadAnterior(rs.getInt("cantidad_anterior"));
                    h.setCantidadNueva(rs.getInt("cantidad_nueva"));
                    h.setMotivo(rs.getString("motivo"));
                    h.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    return h;
                }, idZapatilla);
    }


}
