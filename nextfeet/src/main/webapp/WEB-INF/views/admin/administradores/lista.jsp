<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Administradores - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Administradores</h2>
    <a class="btn btn-acento" href="/admin/administradores/crear">+ Nuevo administrador</a>
</div>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<table>
    <thead><tr><th>Nombre</th><th>Correo</th><th>Teléfono</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${administradores}" var="a">
        <tr>
            <td>${a.nombreCompleto}</td>
            <td>${a.correo}</td>
            <td>${a.telefono}</td>
            <td>${a.activo ? 'Activo' : 'Inactivo'}</td>
            <td>
                <c:if test="${a.activo}">
                    <form method="post" action="/admin/administradores/${a.id}/desactivar" onsubmit="return confirm('¿Desactivar a este administrador?');">
                        <button class="btn btn-pequeno btn-peligro" type="submit">Desactivar</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
