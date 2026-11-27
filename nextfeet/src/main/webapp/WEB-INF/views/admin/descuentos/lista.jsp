<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Descuentos - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Descuentos</h2>
    <a class="btn btn-acento" href="/admin/descuentos/nuevo">+ Nuevo descuento</a>
</div>

<table>
    <thead><tr><th>Código</th><th>Nombre</th><th>Porcentaje</th><th>Inicio</th><th>Fin</th><th>Productos</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${descuentos}" var="d">
        <tr>
            <td>${d.id}</td>
            <td>${d.nombre}</td>
            <td>${d.porcentaje}%</td>
            <td>${d.fechaInicio}</td>
            <td>${d.fechaFin}</td>
            <td>${d.cantidadProductos}</td>
            <td>${d.activo ? 'Activo' : 'Inactivo'}</td>
            <td><a class="btn btn-pequeno btn-secundario" href="/admin/descuentos/${d.id}/editar">Editar</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty descuentos}"><tr><td colspan="8">Aún no hay descuentos registrados.</td></tr></c:if>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
