<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Marca - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<%-- Interfaz 24 (nueva marca) e interfaz 25 (editar marca): mismo formulario --%>
<c:set var="editando" value="${marca.id != null}" />
<c:set var="accion" value="/admin/marcas/nueva" />
<c:if test="${editando}"><c:set var="accion" value="/admin/marcas/${marca.id}/editar" /></c:if>
<h2>${editando ? 'Editar marca' : 'Nueva marca'}</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="${accion}" style="max-width:400px;">
    <label>Nombre de la marca</label>
    <input name="nombre" value="${marca.nombre}" maxlength="80" required>
    <div class="fila">
        <button class="btn btn-acento" type="submit">${editando ? 'Actualizar marca' : 'Guardar marca'}</button>
        <a class="btn btn-secundario" href="/admin/marcas">Cancelar</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
