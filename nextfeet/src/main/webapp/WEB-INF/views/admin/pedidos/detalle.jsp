<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Pedido ${pedido.codigo} - Admin" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Pedido ${pedido.codigo}</h2>
<p><span class="badge-estado estado-${pedido.estado}">${pedido.estadoTexto}</span></p>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<div class="fila" style="align-items:flex-start;">
    <div class="tarjeta" style="flex:1;">
        <h4>Cliente</h4>
        <p>${pedido.nombreCliente}<br>${pedido.correoCliente}</p>
        <p><strong>Dirección de entrega:</strong> ${pedido.direccion}<br><strong>Celular de contacto:</strong> ${pedido.telefono}</p>
        <h4>Comprobante</h4>
                <p>Subtotal: S/ ${pedido.subtotal}</p>
                <c:if test="${pedido.descuento > 0}"><p>Descuento aplicado: - S/ ${pedido.descuento}</p></c:if>
                <p>Envío: <c:choose><c:when test="${pedido.envio > 0}">S/ ${pedido.envio}</c:when><c:otherwise>GRATIS</c:otherwise></c:choose></p>
                <p><strong>Total: S/ ${pedido.total}</strong></p>
    </div>
    <div class="tarjeta" style="flex:2;">
        <h4>Zapatillas compradas</h4>
        <table>
            <thead><tr><th>Producto</th><th>Talla</th><th>Cant.</th><th>Subtotal</th></tr></thead>
            <tbody>
            <c:forEach items="${pedido.detalles}" var="d">
                <tr><td>${d.nombreZapatilla}</td><td>${d.talla}</td><td>${d.cantidad}</td><td>S/ ${d.subtotal}</td></tr>
            </c:forEach>
            </tbody>
        </table>

        <c:if test="${pedido.estado != 'ANULADO' && pedido.estado != 'REEMBOLSADO'}">
        <h4 style="margin-top:20px;">Actualizar estado logístico</h4>
        <form method="post" action="/admin/pedidos/${pedido.id}/estado" class="fila">
            <select name="estado">
                <option value="PENDIENTE" ${pedido.estado == 'PENDIENTE' ? 'selected' : ''}>Pendiente</option>
                <option value="EN_PREPARACION" ${pedido.estado == 'EN_PREPARACION' ? 'selected' : ''}>En preparación</option>
                <option value="EN_CAMINO" ${pedido.estado == 'EN_CAMINO' ? 'selected' : ''}>En camino</option>
                <option value="ENTREGADO" ${pedido.estado == 'ENTREGADO' ? 'selected' : ''}>Entregado</option>
            </select>
            <button class="btn" type="submit">Confirmar</button>
        </form>
        </c:if>
    </div>
</div>

<c:if test="${pedido.estado != 'ANULADO' && pedido.estado != 'ENTREGADO' && pedido.estado != 'REEMBOLSADO'}">
<div class="zona-peligro">
    <h4>Anular pedido</h4>
    <p>Se reintegrará el stock vendido al inventario.</p>
    <form class="vertical" method="post" action="/admin/pedidos/${pedido.id}/anular"
          onsubmit="return confirm('¿Confirmas la anulación de este pedido?');">
        <input type="text" name="motivo" placeholder="Motivo de la anulación" required>
        <button class="btn btn-peligro" type="submit" style="max-width:200px;">Anular pedido</button>
    </form>
</div>
</c:if>

<c:if test="${pedido.estado == 'ANULADO'}">
<div class="tarjeta" style="margin-top:16px;">
    <strong>Motivo de anulación:</strong> ${pedido.motivoAnulacion}
</div>
</c:if>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
