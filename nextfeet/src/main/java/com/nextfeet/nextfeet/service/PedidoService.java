package com.nextfeet.nextfeet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.nextfeet.nextfeet.model.Pedido;
import com.nextfeet.nextfeet.util.Pagina;

public interface PedidoService {

    Pedido confirmarCompra(int idUsuario, String direccion, String telefono);

    List<Pedido> historialCliente(int idUsuario);

    Pedido detalleCliente(int idUsuario, int idPedido);

    Pedido seguimiento(int idUsuario, int idPedido);

    Pagina<Pedido> listarGlobal(String estado, String busqueda, int pagina);

    Pedido detalleAdmin(int idPedido);

    void actualizarEstado(int idPedido, String nuevoEstado);

    void anular(int idPedido, String motivo);

    void anularPorCliente(int idUsuario, int idPedido, String motivo);

    List<Map<String, Object>> ventasPorPeriodo(String periodo);

    BigDecimal totalVentas();

    int totalPedidosVendidos();

}
