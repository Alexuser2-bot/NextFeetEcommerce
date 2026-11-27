<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Editar cliente - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Editar cliente</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="/admin/clientes/${cliente.id}/editar">
    <label>Nombres</label>
    <input name="nombres" value="${cliente.nombres}" required>
    <label>Apellidos</label>
    <input name="apellidos" value="${cliente.apellidos}" required>
    <label>Correo (no editable)</label>
    <input value="${cliente.correo}" disabled>
    <label>Teléfono</label>
    <input name="telefono" value="${cliente.telefono}" required>
    <button class="btn" type="submit">Guardar cambios</button>
</form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
