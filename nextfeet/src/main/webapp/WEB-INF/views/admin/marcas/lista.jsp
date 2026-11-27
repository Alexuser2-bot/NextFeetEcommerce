<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Marcas - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Marcas</h2>
    <a class="btn btn-acento" href="/admin/marcas/nueva">+ Nueva marca</a>
</div>

<form class="fila" method="get" action="/admin/marcas" style="margin-bottom:12px;">
    <input type="text" name="q" value="${q}" placeholder="Buscar marca">
    <button class="btn btn-secundario" type="submit">Buscar</button>
</form>

<table>
    <thead><tr><th>Código</th><th>Nombre</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${marcas}" var="m">
        <tr>
            <td>${m.id}</td>
            <td>${m.nombre}</td>
            <td><a class="btn btn-pequeno btn-secundario" href="/admin/marcas/${m.id}/editar">Editar</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty marcas}"><tr><td colspan="3">No se encontraron marcas.</td></tr></c:if>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
