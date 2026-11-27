package com.nextfeet.nextfeet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.cart.Carrito;
import com.nextfeet.nextfeet.cart.CarritoDAO;
import com.nextfeet.nextfeet.cart.CarritoItem;
import com.nextfeet.nextfeet.model.DetallePedido;
import com.nextfeet.nextfeet.model.Direccion;
import com.nextfeet.nextfeet.model.Pedido;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.repository.PedidoDAO;
import com.nextfeet.nextfeet.util.VentasPorPeriodo;
import com.nextfeet.nextfeet.util.Pagina;

@Service
public class PedidoServiceImpl implements PedidoService {

    private static final List<String> FLUJO_ESTADOS =
            List.of("PENDIENTE", "EN_PREPARACION", "EN_CAMINO", "ENTREGADO");
    private static final int TAMANO_PAGINA = 10;

    private final PedidoDAO pedidoDAO;
    private final CarritoDAO carritoDAO;
    private final ZapatillaService zapatillaService;
    private final DireccionService direccionService;

    public PedidoServiceImpl(PedidoDAO pedidoDAO, CarritoDAO carritoDAO,
            ZapatillaService zapatillaService, DireccionService direccionService) {
        this.pedidoDAO = pedidoDAO;
        this.carritoDAO = carritoDAO;
        this.zapatillaService = zapatillaService;
        this.direccionService = direccionService;
    }

    @Override
    public Pedido confirmarCompra(int idUsuario, String direccion, String telefono) {
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalStateException("Ingresa la dirección de entrega.");
        }
        if (direccion.trim().length() > 240) {
            throw new IllegalStateException("La dirección no puede superar los 240 caracteres.");
        }
        if (telefono == null || !telefono.trim().matches("[0-9]{9}")) {
            throw new IllegalStateException("El número de celular debe tener 9 dígitos.");
        }
        Carrito carrito = carritoDAO.obtenerCarrito(idUsuario);
        if (carrito.isVacio()) {
            throw new IllegalStateException("Tu carrito está vacío.");
        }

        for (CarritoItem item : carrito.getItems()) {
            zapatillaService.verificarDisponibilidad(item.getIdZapatilla(), item.getTalla(), item.getCantidad());
        }

        // RN-05: el cliente debe tener una dirección registrada y el pedido usa una de las suyas
        List<Direccion> guardadas = direccionService.listar(idUsuario);
        if (guardadas.isEmpty()) {
            throw new IllegalStateException("Registra una dirección de entrega antes de pagar.");
        }
        if (guardadas.stream().noneMatch(d -> d.getTextoCompleto().equals(direccion.trim()))) {
            throw new IllegalStateException("Elige una de tus direcciones guardadas.");
        }

        BigDecimal subtotal = carrito.getTotal();
        BigDecimal descuento = BigDecimal.ZERO;
        BigDecimal envio = carrito.getEnvio();
        BigDecimal total = carrito.getTotalPagar();

        Pedido pedido = new Pedido();
        pedido.setCodigo(generarCodigo());
        pedido.setIdUsuario(idUsuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setSubtotal(subtotal);
        pedido.setDescuento(descuento);
        pedido.setEnvio(envio);
        pedido.setTotal(total);
        pedido.setDireccion(direccion.trim());
        pedido.setTelefono(telefono.trim());
        pedido.setEstado("PENDIENTE");
        int idPedido = pedidoDAO.crear(pedido);
        pedidoDAO.registrarHistorial(idPedido, "PENDIENTE");

        for (CarritoItem item : carrito.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setIdZapatilla(item.getIdZapatilla());
            detalle.setNombreZapatilla(item.getNombre());
            detalle.setTalla(item.getTalla());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubtotal(item.getSubtotal());
            pedidoDAO.crearDetalle(idPedido, detalle);

            ZapatillaTalla t = zapatillaService.verificarDisponibilidad(item.getIdZapatilla(), item.getTalla(), item.getCantidad());
            zapatillaService.actualizarStock(item.getIdZapatilla(), item.getTalla(),
                    t.getStock() - item.getCantidad(), "Venta pedido " + pedido.getCodigo());
        }

        carritoDAO.vaciar(carrito.getId());
        return pedidoDAO.buscarPorId(idPedido);
    }

    @Override
    public List<Pedido> historialCliente(int idUsuario) {
        return pedidoDAO.listarPorCliente(idUsuario);
    }

    @Override
    public Pedido detalleCliente(int idUsuario, int idPedido) {
        Pedido p = detalleAdmin(idPedido);
        if (p.getIdUsuario() != idUsuario) {
            throw new IllegalStateException("El pedido no pertenece a tu cuenta.");
        }
        return p;
    }

    @Override
    public Pedido seguimiento(int idUsuario, int idPedido) {
        return detalleCliente(idUsuario, idPedido);
    }

    @Override
    public Pagina<Pedido> listarGlobal(String estado, String busqueda, int pagina) {
        return pedidoDAO.listarGlobal(estado, busqueda, pagina, TAMANO_PAGINA);
    }

    @Override
    public Pedido detalleAdmin(int idPedido) {
        Pedido p = pedidoDAO.buscarPorId(idPedido);
        if (p == null) {
            throw new IllegalStateException("El pedido no existe.");
        }
        return p;
    }

    @Override
    public void actualizarEstado(int idPedido, String nuevoEstado) {
        Pedido p = detalleAdmin(idPedido);
        if ("ANULADO".equals(p.getEstado()) || "REEMBOLSADO".equals(p.getEstado())) {
            throw new IllegalStateException("El pedido ya está finalizado y no puede cambiar de estado.");
        }
        if (!FLUJO_ESTADOS.contains(nuevoEstado) && !"REEMBOLSADO".equals(nuevoEstado)) {
            throw new IllegalStateException("Estado de pedido no reconocido.");
        }
        if (FLUJO_ESTADOS.contains(nuevoEstado)) {
            int actual = FLUJO_ESTADOS.indexOf(p.getEstado());
            int nuevo = FLUJO_ESTADOS.indexOf(nuevoEstado);
            if (nuevo < actual) {
                throw new IllegalStateException("No se puede retroceder el estado del pedido.");
            }
        }
        pedidoDAO.actualizarEstado(idPedido, nuevoEstado);
    }

    @Override
    public void anular(int idPedido, String motivo) {
        Pedido p = detalleAdmin(idPedido);
        if ("ANULADO".equals(p.getEstado())) {
            throw new IllegalStateException("El pedido ya se encuentra anulado.");
        }
        if ("ENTREGADO".equals(p.getEstado())) {
            throw new IllegalStateException("No se puede anular un pedido ya entregado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalStateException("Indica el motivo de la anulación.");
        }
        for (DetallePedido d : p.getDetalles()) {
            ZapatillaTalla t = zapatillaService.verificarDisponibilidadSinLanzar(d.getIdZapatilla(), d.getTalla());
            int stockActual = t == null ? 0 : t.getStock();
            zapatillaService.actualizarStock(d.getIdZapatilla(), d.getTalla(), stockActual + d.getCantidad(),
                    "Anulación pedido " + p.getCodigo());
        }
        pedidoDAO.anular(idPedido, motivo);
    }

    @Override
    public void anularPorCliente(int idUsuario, int idPedido, String motivo) {
        Pedido p = detalleCliente(idUsuario, idPedido);
        if (!"PENDIENTE".equals(p.getEstado()) && !"EN_PREPARACION".equals(p.getEstado())) {
            throw new IllegalStateException("Solo puedes anular pedidos pendientes o en preparación.");
        }
        anular(idPedido, motivo);
    }

    private String generarCodigo() {
        String codigo;
        do {
            codigo = "NF-" + System.currentTimeMillis();
        } while (pedidoDAO.existeCodigo(codigo));
        return codigo;
    }

    @Override
    public List<Map<String, Object>> ventasPorPeriodo(String periodo) {
        return VentasPorPeriodo.agrupar(pedidoDAO.ventasRegistradas(), periodo);
    }

    @Override
    public BigDecimal totalVentas() {
        return pedidoDAO.totalVentas();
    }

    @Override
    public int totalPedidosVendidos() {
        return pedidoDAO.totalPedidosVendidos();
    }

}
