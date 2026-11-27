<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="titulo" value="Nuevo administrador - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Nuevo administrador</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form:form class="vertical" modelAttribute="registro" method="post" action="/admin/administradores/crear">
    <label>Nombres</label>
    <form:input path="nombres" required="true" />
    <label>Apellidos</label>
    <form:input path="apellidos" required="true" />
    <label>Correo electrónico</label>
    <form:input path="correo" type="email" required="true" />
    <label>Teléfono</label>
    <form:input path="telefono" required="true" />
    <label>Contraseña</label>
    <form:password path="password" required="true" />
    <label>Confirmar contraseña</label>
    <form:password path="confirmarPassword" required="true" />
    <button class="btn btn-acento" type="submit">Crear nuevo administrador</button>
</form:form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
