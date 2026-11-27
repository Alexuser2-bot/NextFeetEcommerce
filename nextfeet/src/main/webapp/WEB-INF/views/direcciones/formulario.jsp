<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Dirección - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<%-- Interfaz 30 (agregar dirección) e interfaz 31 (editar dirección): mismo formulario --%>
<c:set var="editando" value="${direccion.id != null}" />
<c:set var="accion" value="/direcciones/nueva" />
<c:if test="${editando}"><c:set var="accion" value="/direcciones/${direccion.id}/editar" /></c:if>
<h2>${editando ? 'Editar dirección' : 'Agregar dirección'}</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="${accion}" style="max-width:480px;">
    <input type="hidden" name="volver" value="${param.volver}">
    <label>Departamento</label>
    <input name="departamento" value="${direccion.departamento}" maxlength="60" required>
    <label>Provincia</label>
    <input name="provincia" value="${direccion.provincia}" maxlength="60" required>
    <label>Distrito</label>
    <input name="distrito" value="${direccion.distrito}" maxlength="60" required>
    <label>Calle</label>
    <input name="calle" value="${direccion.calle}" maxlength="120" required>
    <div class="fila">
        <button class="btn btn-acento" type="submit">${editando ? 'Actualizar dirección' : 'Guardar dirección'}</button>
        <a class="btn btn-secundario" href="/direcciones">Cancelar</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
