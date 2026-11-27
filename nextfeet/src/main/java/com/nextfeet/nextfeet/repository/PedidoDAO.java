package com.nextfeet.nextfeet.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.nextfeet.nextfeet.model.DetallePedido;
import com.nextfeet.nextfeet.model.Pedido;
import com.nextfeet.nextfeet.util.Pagina;

public interface PedidoDAO {

    int crear(Pedido pedido);

    void crearDetalle(int idPedido, DetallePedido detalle);

    void registrarHistorial(int idPedido, String estado);

    Pedido buscarPorId(int id);

    List<Pedido> listarPorCliente(int idUsuario);

    Pagina<Pedido> listarGlobal(String estado, String busqueda, int pagina, int tamano);

    void actualizarEstado(int id, String nuevoEstado);

    void anular(int id, String motivo);

    boolean existeCodigo(String codigo);

    List<Map<String, Object>> ventasRegistradas();

    BigDecimal totalVentas();

    int totalPedidosVendidos();

}
