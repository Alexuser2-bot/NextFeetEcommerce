<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mi perfil - Admin" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Mi perfil de administrador</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>
<c:if test="${param.actualizado != null}"><div class="alerta alerta-exito">Datos actualizados correctamente.</div></c:if>

<div class="tarjeta" style="max-width:480px;">
    <form class="vertical" method="post" action="/admin/perfil/actualizar">
        <label>Nombres</label>
        <input name="nombres" value="${usuario.nombres}" required>
        <label>Apellidos</label>
        <input name="apellidos" value="${usuario.apellidos}" required>
        <label>Correo (no editable)</label>
        <input value="${usuario.correo}" disabled>
        <label>Teléfono</label>
        <input name="telefono" value="${usuario.telefono}" required>
        <label>Nueva contraseña (opcional)</label>
        <input type="password" name="nuevaPassword">
        <button class="btn" type="submit">Confirmar cambios</button>
    </form>
</div>

<div class="zona-peligro" style="max-width:480px;">
    <h4>Desactivar mi cuenta de administrador</h4>
    <p>Perderás tus privilegios de administración de inmediato.</p>
    <form class="vertical" method="post" action="/admin/perfil/desactivar" onsubmit="return confirm('¿Confirmas la desactivación?');">
        <input type="password" name="passwordActual" placeholder="Contraseña actual" required>
        <button class="btn btn-peligro" type="submit">Desactivar cuenta</button>
    </form>
</div>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
