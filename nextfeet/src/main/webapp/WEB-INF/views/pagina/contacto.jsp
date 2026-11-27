<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="titulo" value="Contacto - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="tarjeta" style="max-width:520px;">
    <h2>Contáctanos</h2>
    <p>¿Tienes una consulta o quieres iniciar un proceso de devolución? Escríbenos.</p>
    <c:if test="${enviado}"><div class="alerta alerta-exito">¡Mensaje enviado! Te responderemos a la brevedad.</div></c:if>

    <form:form class="vertical" modelAttribute="contacto" method="post" action="/contacto">
        <label>Nombre</label>
        <form:input path="nombre" required="true" />
        <label>Correo</label>
        <form:input path="correo" type="email" required="true" />
        <label>Asunto</label>
        <form:input path="asunto" required="true" />
        <label>Mensaje</label>
        <form:textarea path="mensaje" rows="4" required="true" />
        <button class="btn" type="submit">Enviar mensaje</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
