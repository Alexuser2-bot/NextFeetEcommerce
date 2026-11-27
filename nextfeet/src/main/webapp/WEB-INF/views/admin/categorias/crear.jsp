<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Nueva categoría - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Nueva categoría</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="/admin/categorias/crear">
    <label>Nombre de la categoría</label>
    <input name="nombre" required>
    <label>Descripción</label>
    <textarea name="descripcion" rows="3"></textarea>
    <button class="btn btn-acento" type="submit">Registrar categoría</button>
</form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
