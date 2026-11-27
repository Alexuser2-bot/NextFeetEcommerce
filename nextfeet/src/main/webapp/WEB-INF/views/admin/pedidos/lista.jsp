<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Pedidos - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Pedidos</h2>
<form method="get" action="/admin/pedidos" class="fila" style="margin-bottom:16px;">
    <input type="text" name="q" value="${q}" placeholder="Buscar por código o cliente">
    <select name="estado">
        <option value="">Todos los estados</option>
        <option value="PENDIENTE" ${estado == 'PENDIENTE' ? 'selected' : ''}>Pendiente</option>
        <option value="EN_PREPARACION" ${estado == 'EN_PREPARACION' ? 'selected' : ''}>En preparación</option>
        <option value="EN_CAMINO" ${estado == 'EN_CAMINO' ? 'selected' : ''}>En camino</option>
        <option value="ENTREGADO" ${estado == 'ENTREGADO' ? 'selected' : ''}>Entregado</option>
        <option value="ANULADO" ${estado == 'ANULADO' ? 'selected' : ''}>Anulado</option>
        <option value="REEMBOLSADO" ${estado == 'REEMBOLSADO' ? 'selected' : ''}>Reembolsado</option>
    </select>
    <button class="btn btn-secundario" type="submit">Filtrar</button>
</form>

<table>
    <thead><tr><th>Código</th><th>Cliente</th><th>Fecha</th><th>Total</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${resultado.contenido}" var="p">
        <tr>
            <td>${p.codigo}</td>
            <td>${p.nombreCliente}</td>
            <td>${p.fechaTexto}</td>
            <td>S/ ${p.total}</td>
            <td><span class="badge-estado estado-${p.estado}">${p.estadoTexto}</span></td>
            <td><a class="btn btn-pequeno btn-secundario" href="/admin/pedidos/${p.id}">Ver detalle</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty resultado.contenido}"><tr><td colspan="6">No se encontraron pedidos.</td></tr></c:if>
    </tbody>
</table>
<div class="paginacion">
    <c:if test="${resultado.tienePrevia}"><a class="btn btn-pequeno btn-secundario" href="?pagina=${resultado.pagina - 1}&q=${q}&estado=${estado}">&laquo; Anterior</a></c:if>
    <span>Página ${resultado.pagina} de ${resultado.totalPaginas}</span>
    <c:if test="${resultado.tieneSiguiente}"><a class="btn btn-pequeno btn-secundario" href="?pagina=${resultado.pagina + 1}&q=${q}&estado=${estado}">Siguiente &raquo;</a></c:if>
</div>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
