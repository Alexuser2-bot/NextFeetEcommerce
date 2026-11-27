<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Seguimiento del pedido - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Seguimiento del pedido ${pedido.codigo}</h2>
<p>Estado actual: <span class="badge-estado estado-${pedido.estado}">${pedido.estadoTexto}</span></p>

<ul style="list-style:none;padding:0;">
    <c:forEach items="${pedido.historial}" var="h">
        <li class="tarjeta" style="margin-bottom:8px;">
            <span class="badge-estado estado-${h.estado}">${h.estado}</span>
            &nbsp; ${h.fechaTexto}
        </li>
    </c:forEach>
</ul>
<a href="/mis-pedidos/${pedido.id}">Volver al detalle del pedido</a>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
