<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Zapatillas - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Zapatillas</h2>
    <a class="btn btn-acento" href="/admin/zapatillas/nueva">+ Agregar zapatilla</a>
</div>
<form method="get" action="/admin/zapatillas" class="fila" style="margin-bottom:16px;">
    <input type="text" name="q" value="${q}" placeholder="Buscar por SKU o nombre">
    <button class="btn btn-secundario" type="submit">Buscar</button>
</form>

<table>
    <thead><tr><th>Img</th><th>Nombre</th><th>Marca</th><th>Precio</th><th>Stock</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${resultado.contenido}" var="z">
        <tr>
            <td><img src="${z.imagenUrl}" style="width:42px;height:42px;object-fit:cover;border-radius:4px;"></td>
            <td>${z.nombre}</td>
            <td>${z.marca}</td>
            <td>S/ ${z.precio}</td>
            <td>${z.stockTotal}</td>
            <td>${z.activo ? 'Activa' : 'Inactiva'}</td>
            <td><a class="btn btn-pequeno btn-secundario" href="/admin/zapatillas/${z.id}">Gestionar</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="paginacion">
    <c:if test="${resultado.tienePrevia}"><a class="btn btn-pequeno btn-secundario" href="?pagina=${resultado.pagina - 1}&q=${q}">&laquo; Anterior</a></c:if>
    <span>Página ${resultado.pagina} de ${resultado.totalPaginas}</span>
    <c:if test="${resultado.tieneSiguiente}"><a class="btn btn-pequeno btn-secundario" href="?pagina=${resultado.pagina + 1}&q=${q}">Siguiente &raquo;</a></c:if>
</div>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
