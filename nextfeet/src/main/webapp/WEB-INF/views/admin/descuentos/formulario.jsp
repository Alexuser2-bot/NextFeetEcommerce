<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Descuento - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<%-- Interfaz 27 (nuevo descuento) e interfaz 28 (editar descuento): mismo formulario --%>
<c:set var="editando" value="${descuento.id != null}" />
<c:set var="accion" value="/admin/descuentos/nuevo" />
<c:if test="${editando}"><c:set var="accion" value="/admin/descuentos/${descuento.id}/editar" /></c:if>
<h2>${editando ? 'Editar descuento' : 'Nuevo descuento'}</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="${accion}" style="max-width:600px;">
    <label>Nombre</label>
    <input name="nombre" value="${descuento.nombre}" maxlength="100" required>
    <div class="fila">
        <div><label>Porcentaje (%)</label><input type="number" step="0.01" min="0.01" max="100" name="porcentaje" value="${descuento.porcentaje}" required></div>
        <div><label>Fecha de inicio</label><input type="date" name="fechaInicio" value="${descuento.fechaInicio}" required></div>
        <div><label>Fecha de fin</label><input type="date" name="fechaFin" value="${descuento.fechaFin}" required></div>
    </div>
    <c:if test="${editando}">
        <label>Estado</label>
        <select name="activo">
            <option value="true" ${descuento.activo ? 'selected' : ''}>Activo</option>
            <option value="false" ${!descuento.activo ? 'selected' : ''}>Inactivo</option>
        </select>
    </c:if>
    <label>Zapatillas con este descuento</label>
    <c:forEach items="${zapatillas}" var="z">
        <label style="font-weight:normal;">
            <input type="checkbox" name="idsZapatillas" value="${z.id}" ${descuento.idsZapatillas.contains(z.id) ? 'checked' : ''}>
            ${z.nombre} (${z.marca})
        </label>
    </c:forEach>
    <div class="fila">
        <button class="btn btn-acento" type="submit">${editando ? 'Actualizar descuento' : 'Guardar descuento'}</button>
        <a class="btn btn-secundario" href="/admin/descuentos">Cancelar</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
