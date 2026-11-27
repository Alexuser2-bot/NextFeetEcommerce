<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="titulo" value="Panel administrativo - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Panel de administración</h2>
<div class="panel-admin-grilla">
    <a class="panel-admin-tile" href="/admin/categorias">📁 Categorías</a>
    <a class="panel-admin-tile" href="/admin/zapatillas">👟 Zapatillas</a>
    <a class="panel-admin-tile" href="/admin/pedidos">📦 Pedidos</a>
    <a class="panel-admin-tile" href="/admin/clientes">👤 Clientes</a>
    <a class="panel-admin-tile" href="/admin/administradores">🛡️ Administradores</a>
</div>

<h3 style="margin-top:28px;">Métricas de ventas</h3>
<div class="metricas">
    <div class="metrica">
        <small>Ventas totales</small>
        <strong>S/ <fmt:formatNumber value="${totalVentas}" pattern="#,##0.00" /></strong>
    </div>
    <div class="metrica">
        <small>Pedidos vendidos</small>
        <strong>${totalPedidos}</strong>
    </div>
    <div class="metrica">
        <small>Ticket promedio</small>
        <strong>S/ <fmt:formatNumber value="${totalPedidos > 0 ? totalVentas / totalPedidos : 0}" pattern="#,##0.00" /></strong>
    </div>
</div>

<div class="tarjeta">
    <h4 style="margin-top:0;">
        Ventas por
        <c:choose>
            <c:when test="${periodo == 'dia'}">día</c:when>
            <c:when test="${periodo == 'semana'}">semana</c:when>
            <c:when test="${periodo == 'anio'}">año</c:when>
            <c:otherwise>mes</c:otherwise>
        </c:choose>
        (S/) y cantidad de ventas
    </h4>
    <div style="display:flex;gap:8px;flex-wrap:wrap;margin-bottom:12px;">
        <a class="btn btn-pequeno ${periodo == 'dia' ? 'btn-acento' : 'btn-secundario'}" href="/admin?periodo=dia">Días</a>
        <a class="btn btn-pequeno ${periodo == 'semana' ? 'btn-acento' : 'btn-secundario'}" href="/admin?periodo=semana">Semanas</a>
        <a class="btn btn-pequeno ${periodo == 'mes' ? 'btn-acento' : 'btn-secundario'}" href="/admin?periodo=mes">Meses</a>
        <a class="btn btn-pequeno ${periodo == 'anio' ? 'btn-acento' : 'btn-secundario'}" href="/admin?periodo=anio">Años</a>
    </div>
    <c:choose>
    <c:when test="${empty ventas}">
        <p>Aún no hay ventas registradas.</p>
    </c:when>
    <c:otherwise>
        <c:set var="max" value="1" />
        <c:forEach items="${ventas}" var="v">
            <c:if test="${v.total > max}"><c:set var="max" value="${v.total}" /></c:if>
        </c:forEach>
        <c:set var="n" value="${fn:length(ventas)}" />
        <c:set var="paso" value="${n > 1 ? 610 / (n - 1) : 0}" />
        <c:set var="puntos" value="" />
        <c:forEach items="${ventas}" var="v" varStatus="st">
            <c:set var="puntos" value="${puntos} ${70 + st.index * paso},${250 - (v.total * 230) / max}" />
        </c:forEach>

        <div style="overflow-x:auto;">
        <svg viewBox="0 0 720 312" style="width:100%;min-width:520px;height:auto;" role="img" aria-label="Gráfico de líneas de ventas">
            <c:forEach begin="0" end="4" var="k">
                <line x1="70" x2="680" y1="${250 - k * 57.5}" y2="${250 - k * 57.5}" stroke="#dfe3e8" stroke-width="1" />
                <text x="62" y="${254 - k * 57.5}" text-anchor="end" font-size="11" fill="#667">
                    <fmt:formatNumber value="${(max * k) / 4}" pattern="#,##0" />
                </text>
            </c:forEach>
            <line x1="70" x2="680" y1="250" y2="250" stroke="#99a" stroke-width="1.5" />

            <polyline points="${puntos}" fill="none" stroke="#ff6b35" stroke-width="3" stroke-linejoin="round" stroke-linecap="round" />

            <c:forEach items="${ventas}" var="v" varStatus="st">
                <circle cx="${70 + st.index * paso}" cy="${250 - (v.total * 230) / max}" r="5" fill="#1a2b4c" stroke="#fff" stroke-width="2">
                    <title>${v.etiqueta}: S/ <fmt:formatNumber value="${v.total}" pattern="#,##0.00" /> en ${v.pedidos} ${v.pedidos == 1 ? 'venta' : 'ventas'}</title>
                </circle>
                <c:if test="${st.index % salto == 0}">
                    <text x="${70 + st.index * paso}" y="${240 - (v.total * 230) / max}" text-anchor="middle" font-size="11" font-weight="700" fill="#1a2b4c">
                        <fmt:formatNumber value="${v.total}" pattern="#,##0" />
                    </text>
                    <text x="${70 + st.index * paso}" y="272" text-anchor="middle" font-size="12" fill="#445">${v.etiqueta}</text>
                    <text x="${70 + st.index * paso}" y="290" text-anchor="middle" font-size="11" fill="#1b7a3d" font-weight="700">${v.pedidos} ${v.pedidos == 1 ? 'venta' : 'ventas'}</text>
                </c:if>
            </c:forEach>
        </svg>
        </div>

        <table style="margin-top:14px;">
            <thead><tr><th>Periodo</th><th>Cantidad de ventas</th><th>Monto vendido (S/)</th></tr></thead>
            <tbody>
            <c:forEach items="${ventas}" var="v">
                <c:if test="${v.pedidos > 0}">
                <tr>
                    <td>${v.etiqueta}</td>
                    <td>${v.pedidos}</td>
                    <td><fmt:formatNumber value="${v.total}" pattern="#,##0.00" /></td>
                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
