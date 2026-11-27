<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Clientes - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Clientes</h2>
<form method="get" action="/admin/clientes" class="fila" style="margin-bottom:16px;">
    <input type="text" name="q" value="${q}" placeholder="Buscar por nombre o correo">
    <button class="btn btn-secundario" type="submit">Buscar</button>
</form>

<table>
    <thead><tr><th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${resultado.contenido}" var="cl">
        <tr>
            <td>${cl.id}</td>
            <td>${cl.nombreCompleto}</td>
            <td>${cl.correo}</td>
            <td>${cl.telefono}</td>
            <td>${cl.activo ? 'Activo' : 'Inactivo'}</td>
            <td class="fila">
                <a class="btn btn-pequeno btn-secundario" href="/admin/clientes/${cl.id}/editar">Editar</a>
                <c:if test="${cl.activo}">
                    <form method="post" action="/admin/clientes/${cl.id}/desactivar" onsubmit="return confirm('¿Cancelar la cuenta de este cliente?');">
                        <button class="btn btn-pequeno btn-peligro" type="submit">Cancelar cuenta</button>
                    </form>
                </c:if>
            </td>
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
