<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="titulo" value="Crear cuenta - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="tarjeta" style="max-width:480px;margin:30px auto;">
    <h2>Crear cuenta de cliente</h2>
    <c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

    <form:form class="vertical" modelAttribute="registro" method="post" action="/registro">
        <label>Nombres</label>
        <form:input path="nombres" required="true" />
        <label>Apellidos</label>
        <form:input path="apellidos" required="true" />
        <label>Correo electrónico</label>
        <form:input path="correo" type="email" required="true" />
        <label>Teléfono (9 dígitos)</label>
        <form:input path="telefono" required="true" />
        <label>Contraseña</label>
        <form:password path="password" required="true" />
        <label>Confirmar contraseña</label>
        <form:password path="confirmarPassword" required="true" />
        <button class="btn" type="submit">Registrarse</button>
    </form:form>
    <p>¿Ya tienes cuenta? <a href="/login">Inicia sesión</a></p>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
