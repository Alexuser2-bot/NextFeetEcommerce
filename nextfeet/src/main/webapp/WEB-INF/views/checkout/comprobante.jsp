<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Comprobante - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="tarjeta" style="max-width:600px;">
    <h2>¡Pago realizado!</h2>
    <p><strong>Código de pedido:</strong> ${pedido.codigo}</p>
    <p><strong>Fecha:</strong> ${pedido.fechaTexto}</p>
    <p><strong>Dirección de entrega:</strong> ${pedido.direccion}</p>
    <p><strong>Celular de contacto:</strong> ${pedido.telefono}</p>
    <table>
        <thead><tr><th>Producto</th><th>Talla</th><th>Cant.</th><th>Subtotal</th></tr></thead>
        <tbody>
        <c:forEach items="${pedido.detalles}" var="d">
            <tr><td>${d.nombreZapatilla}</td><td>${d.talla}</td><td>${d.cantidad}</td><td>S/ ${d.subtotal}</td></tr>
        </c:forEach>
        </tbody>
    </table>
    <p>Subtotal: S/ ${pedido.subtotal}</p>
    <c:if test="${pedido.descuento > 0}"><p style="color:#1b7a3d;">Descuento aplicado: - S/ ${pedido.descuento}</p></c:if>
    <p>Envío: <c:choose><c:when test="${pedido.envio > 0}">S/ ${pedido.envio}</c:when><c:otherwise>GRATIS</c:otherwise></c:choose></p>
    <p style="font-size:1.2rem;"><strong>Total pagado: S/ ${pedido.total}</strong></p>
    <div class="fila">
        <a class="btn" href="/mis-pedidos/${pedido.id}">Ver detalle del pedido</a>
        <button class="btn btn-secundario" onclick="window.print()">Descargar / Imprimir</button>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
