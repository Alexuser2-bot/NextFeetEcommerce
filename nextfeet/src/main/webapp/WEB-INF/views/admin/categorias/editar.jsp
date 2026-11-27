<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Editar categoría - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Editar categoría</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="/admin/categorias/${categoria.id}/editar">
    <label>Nombre</label>
    <input name="nombre" value="${categoria.nombre}" required>
    <label>Descripción</label>
    <textarea name="descripcion" rows="3">${categoria.descripcion}</textarea>
    <button class="btn" type="submit">Guardar cambios</button>
</form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
