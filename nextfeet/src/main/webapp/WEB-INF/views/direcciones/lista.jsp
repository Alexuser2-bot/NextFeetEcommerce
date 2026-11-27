<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Mis direcciones - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Mis direcciones</h2>
    <a class="btn btn-acento" href="/direcciones/nueva">+ Agregar dirección</a>
</div>
<c:if test="${param.principalNueva != null}"><div class="alerta alerta-exito">Dirección registrada como principal.</div></c:if>
<c:if test="${param.guardada != null}"><div class="alerta alerta-exito">Dirección guardada.</div></c:if>
<c:if test="${param.actualizada != null}"><div class="alerta alerta-exito">Dirección actualizada.</div></c:if>
<c:if test="${param.principalCambiada != null}"><div class="alerta alerta-exito">Dirección principal actualizada.</div></c:if>

<table>
    <thead><tr><th>Departamento</th><th>Provincia</th><th>Distrito</th><th>Calle</th><th>Tipo</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${direcciones}" var="d">
        <tr>
            <td>${d.departamento}</td>
            <td>${d.provincia}</td>
            <td>${d.distrito}</td>
            <td>${d.calle}</td>
            <td><span class="badge-estado" style="${d.principal ? 'background:#dbeafe;color:#1d4ed8;' : ''}">${d.principal ? 'Principal' : 'Secundaria'}</span></td>
            <td class="fila">
                <a class="btn btn-pequeno btn-secundario" href="/direcciones/${d.id}/editar">Editar</a>
                <c:if test="${not d.principal}">
                    <form method="post" action="/direcciones/principal">
                        <input type="hidden" name="idDireccion" value="${d.id}">
                        <button class="btn btn-pequeno" type="submit">Hacer principal</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty direcciones}"><tr><td colspan="6">Aún no tienes direcciones registradas.</td></tr></c:if>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
