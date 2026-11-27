<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Iniciar sesión - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="tarjeta" style="max-width:420px;margin:30px auto;">
    <h2>Iniciar sesión</h2>
    <c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>
    <c:if test="${param.registrado != null}"><div class="alerta alerta-exito">Cuenta creada. Ahora inicia sesión.</div></c:if>
    <c:if test="${param.desactivado != null}"><div class="alerta alerta-exito">Tu cuenta fue cancelada.</div></c:if>

    <form class="vertical" method="post" action="/login">
        <label for="correo">Correo electrónico</label>
        <input type="email" id="correo" name="correo" required>
        <label for="password">Contraseña</label>
        <input type="password" id="password" name="password" required>
        <button class="btn" type="submit">Ingresar</button>
    </form>
    <p>¿Aún no tienes cuenta? <a href="/registro">Regístrate aquí</a></p>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
