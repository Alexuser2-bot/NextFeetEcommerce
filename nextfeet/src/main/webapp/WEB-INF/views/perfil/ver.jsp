<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mi perfil - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Mi perfil</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>
<c:if test="${param.actualizado != null}"><div class="alerta alerta-exito">Datos actualizados correctamente.</div></c:if>

<div class="tarjeta" style="max-width:480px;">
    <form class="vertical" method="post" action="/perfil/actualizar">
        <label>Nombres</label>
        <input name="nombres" value="${usuario.nombres}" required>
        <label>Apellidos</label>
        <input name="apellidos" value="${usuario.apellidos}" required>
        <label>Correo (no editable)</label>
        <input value="${usuario.correo}" disabled>
        <label>Teléfono</label>
        <input name="telefono" value="${usuario.telefono}" required>
        <label>Nueva contraseña (opcional)</label>
        <input type="password" name="nuevaPassword" placeholder="Dejar en blanco para no cambiarla">
        <button class="btn" type="submit">Guardar cambios</button>
    </form>
</div>

<p><a href="/direcciones">Administrar mis direcciones de envío</a></p>

<div class="zona-peligro" style="max-width:480px;">
    <h4>Cancelar mi cuenta</h4>
    <p>Esta acción cancelará tu usuario y no podrás volver a iniciar sesión. Confirma tu contraseña actual.</p>
    <form class="vertical" method="post" action="/perfil/desactivar" onsubmit="return confirm('¿Seguro que deseas cancelar tu cuenta?');">
        <input type="password" name="passwordActual" placeholder="Contraseña actual" required>
        <button class="btn btn-peligro" type="submit">Cancelar cuenta</button>
    </form>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
