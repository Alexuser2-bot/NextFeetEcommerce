<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Pedido ${pedido.codigo} - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Pedido ${pedido.codigo}</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>
<p><span class="badge-estado estado-${pedido.estado}">${pedido.estadoTexto}</span>
   &nbsp; <a href="/mis-pedidos/${pedido.id}/seguimiento">Ver seguimiento de envío</a></p>

<div class="fila" style="align-items:flex-start;">
    <div class="tarjeta" style="flex:1;">
        <h4>Resumen</h4>
        <p><strong>Dirección de entrega:</strong> ${pedido.direccion}</p>
        <p><strong>Celular de contacto:</strong> ${pedido.telefono}</p>
        <p><strong>Subtotal:</strong> S/ ${pedido.subtotal}</p>
        <c:if test="${pedido.descuento > 0}"><p><strong>Descuento aplicado:</strong> - S/ ${pedido.descuento}</p></c:if>
        <p><strong>Envío:</strong> <c:choose><c:when test="${pedido.envio > 0}">S/ ${pedido.envio}</c:when><c:otherwise>GRATIS</c:otherwise></c:choose></p>
        <p><strong>Total pagado:</strong> S/ ${pedido.total}</p>
    </div>
    <div class="tarjeta" style="flex:2;">
        <h4>Productos</h4>
        <table>
            <thead><tr><th>Zapatilla</th><th>Talla</th><th>Cant.</th><th>Subtotal</th></tr></thead>
            <tbody>
            <c:forEach items="${pedido.detalles}" var="d">
                <tr><td>${d.nombreZapatilla}</td><td>${d.talla}</td><td>${d.cantidad}</td><td>S/ ${d.subtotal}</td></tr>
            </c:forEach>
            </tbody>
        </table>
        <p style="text-align:right;font-size:1.1rem;"><strong>Total: S/ ${pedido.total}</strong></p>
    </div>
</div>

<c:if test="${pedido.estado == 'PENDIENTE' || pedido.estado == 'EN_PREPARACION'}">
<div class="zona-peligro">
    <h4>Anular pedido</h4>
    <p>Puedes anular tu pedido mientras esté pendiente o en preparación.</p>
    <form class="vertical" method="post" action="/mis-pedidos/${pedido.id}/anular"
          onsubmit="return confirm('¿Confirmas la anulación de este pedido?');">
        <input type="text" name="motivo" placeholder="Motivo de la anulación" required>
        <button class="btn btn-peligro" type="submit" style="max-width:200px;">Anular pedido</button>
    </form>
</div>
</c:if>

<c:if test="${pedido.estado == 'ANULADO'}">
<div class="tarjeta" style="margin-top:16px;">
    <strong>Pedido anulado.</strong> Motivo: ${pedido.motivoAnulacion}
</div>
</c:if>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
