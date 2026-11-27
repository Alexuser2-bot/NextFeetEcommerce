<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mis pedidos - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Mis pedidos</h2>
<table>
    <thead><tr><th>Código</th><th>Fecha</th><th>Total</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${pedidos}" var="p">
        <tr>
            <td>${p.codigo}</td>
            <td>${p.fechaTexto}</td>
            <td>S/ ${p.total}</td>
            <td><span class="badge-estado estado-${p.estado}">${p.estadoTexto}</span></td>
            <td><a class="btn btn-pequeno" href="/mis-pedidos/${p.id}">Ver detalle</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty pedidos}"><tr><td colspan="5">Aún no tienes pedidos.</td></tr></c:if>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
