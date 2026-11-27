package com.nextfeet.nextfeet.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nextfeet.nextfeet.model.DetallePedido;
import com.nextfeet.nextfeet.model.Pedido;
import com.nextfeet.nextfeet.model.PedidoHistorial;
import com.nextfeet.nextfeet.util.Pagina;

@Repository
public class PedidoRepository implements PedidoDAO {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_PEDIDO =
            "SELECT p.id, p.codigo, p.id_usuario, p.direccion, p.telefono, p.fecha, p.subtotal, p.descuento, p.envio, p.total, " +
            "p.estado, p.motivo_anulacion, u.nombres, u.apellidos, u.correo " +
            "FROM pedido p JOIN usuario u ON u.id = p.id_usuario ";

    private final RowMapper<Pedido> mapper = (rs, rowNum) -> {
        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setCodigo(rs.getString("codigo"));
        p.setIdUsuario(rs.getInt("id_usuario"));
        p.setDireccion(rs.getString("direccion"));
        p.setTelefono(rs.getString("telefono"));
        p.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        p.setSubtotal(rs.getBigDecimal("subtotal"));
        p.setDescuento(rs.getBigDecimal("descuento"));
        p.setEnvio(rs.getBigDecimal("envio"));
        p.setTotal(rs.getBigDecimal("total"));
        p.setEstado(rs.getString("estado"));
        p.setMotivoAnulacion(rs.getString("motivo_anulacion"));
        p.setNombreCliente(rs.getString("nombres") + " " + rs.getString("apellidos"));
        p.setCorreoCliente(rs.getString("correo"));
        return p;
    };

    @Override
    public int crear(Pedido p) {
        jdbcTemplate.update(
                "INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, descuento, envio, total, estado, " +
                        "motivo_anulacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                p.getCodigo(), p.getIdUsuario(), p.getDireccion(), p.getTelefono(), java.sql.Timestamp.valueOf(p.getFecha()),
                p.getSubtotal(), p.getDescuento(), p.getEnvio(), p.getTotal(), p.getEstado(), "");
        return jdbcTemplate.queryForObject("SELECT id FROM pedido WHERE codigo = ?", Integer.class, p.getCodigo());
    }

    @Override
    public void crearDetalle(int idPedido, DetallePedido d) {
        jdbcTemplate.update(
                "INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, " +
                        "precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)",
                idPedido, d.getIdZapatilla(), d.getNombreZapatilla(), d.getTalla(), d.getCantidad(),
                d.getPrecioUnitario(), d.getSubtotal());
    }

    @Override
    public void registrarHistorial(int idPedido, String estado) {
        jdbcTemplate.update("INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (?, ?, ?)",
                idPedido, estado, java.sql.Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public Pedido buscarPorId(int id) {
        List<Pedido> r = jdbcTemplate.query(SELECT_PEDIDO + "WHERE p.id = ?", mapper, id);
        if (r.isEmpty()) {
            return null;
        }
        Pedido p = r.get(0);
        cargarDetalleYHistorial(p);
        return p;
    }

    private void cargarDetalleYHistorial(Pedido p) {
        p.setDetalles(jdbcTemplate.query(
                "SELECT id, id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal " +
                        "FROM detalle_pedido WHERE id_pedido = ?",
                (rs, rowNum) -> {
                    DetallePedido d = new DetallePedido();
                    d.setId(rs.getInt("id"));
                    d.setIdPedido(rs.getInt("id_pedido"));
                    d.setIdZapatilla(rs.getInt("id_zapatilla"));
                    d.setNombreZapatilla(rs.getString("nombre_zapatilla"));
                    d.setTalla(rs.getString("talla"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                    d.setSubtotal(rs.getBigDecimal("subtotal"));
                    return d;
                }, p.getId()));

        p.setHistorial(jdbcTemplate.query(
                "SELECT id, id_pedido, estado, fecha FROM pedido_historial WHERE id_pedido = ? ORDER BY fecha",
                (rs, rowNum) -> {
                    PedidoHistorial h = new PedidoHistorial();
                    h.setId(rs.getInt("id"));
                    h.setIdPedido(rs.getInt("id_pedido"));
                    h.setEstado(rs.getString("estado"));
                    h.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    return h;
                }, p.getId()));
    }

    @Override
    public List<Pedido> listarPorCliente(int idUsuario) {
        return jdbcTemplate.query(SELECT_PEDIDO + "WHERE p.id_usuario = ? ORDER BY p.fecha DESC", mapper, idUsuario);
    }

    @Override
    public Pagina<Pedido> listarGlobal(String estado, String busqueda, int pagina, int tamano) {
        StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if (estado != null && !estado.isBlank()) {
            where.append("AND p.estado = ? ");
            params.add(estado);
        }
        if (busqueda != null && !busqueda.isBlank()) {
            where.append("AND (LOWER(p.codigo) LIKE ? OR LOWER(u.nombres) LIKE ? OR LOWER(u.apellidos) LIKE ?) ");
            String like = "%" + busqueda.trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pedido p JOIN usuario u ON u.id = p.id_usuario " + where,
                Long.class, params.toArray());
        pagina = Math.max(pagina, 1);
        List<Object> paramsPagina = new ArrayList<>(params);
        paramsPagina.add(tamano);
        paramsPagina.add((pagina - 1) * tamano);
        List<Pedido> contenido = jdbcTemplate.query(
                SELECT_PEDIDO + where + "ORDER BY p.fecha DESC LIMIT ? OFFSET ?", mapper, paramsPagina.toArray());
        return new Pagina<>(contenido, pagina, tamano, total == null ? 0 : total);
    }

    @Override
    public void actualizarEstado(int id, String nuevoEstado) {
        jdbcTemplate.update("UPDATE pedido SET estado = ? WHERE id = ?", nuevoEstado, id);
        registrarHistorial(id, nuevoEstado);
    }

    @Override
    public void anular(int id, String motivo) {
        jdbcTemplate.update("UPDATE pedido SET estado = 'ANULADO', motivo_anulacion = ? WHERE id = ?", motivo, id);
        registrarHistorial(id, "ANULADO");
    }

    @Override
    public boolean existeCodigo(String codigo) {
        Long n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pedido WHERE codigo = ?", Long.class, codigo);
        return n != null && n > 0;
    }

    @Override
    public List<Map<String, Object>> ventasRegistradas() {
        return jdbcTemplate.queryForList(
                "SELECT fecha, total FROM pedido WHERE estado NOT IN ('ANULADO', 'REEMBOLSADO') ORDER BY fecha");
    }

    @Override
    public BigDecimal totalVentas() {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(total), 0) FROM pedido WHERE estado NOT IN ('ANULADO', 'REEMBOLSADO')",
                BigDecimal.class);
    }

    @Override
    public int totalPedidosVendidos() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM pedido WHERE estado NOT IN ('ANULADO', 'REEMBOLSADO')", Integer.class);
    }

}
