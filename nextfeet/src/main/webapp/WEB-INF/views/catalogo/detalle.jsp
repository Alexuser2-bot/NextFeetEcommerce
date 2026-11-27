<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="${zapatilla.nombre} - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<c:if test="${not empty param.error}"><div class="alerta alerta-error">${param.error}</div></c:if>
<div class="fila" style="align-items:flex-start;">
    <img src="${zapatilla.imagenUrl}" alt="${zapatilla.nombre}" style="max-width:420px;width:100%;border-radius:8px;">
    <div style="flex:1;min-width:280px;">
        <span class="marca">${zapatilla.marca}</span>
        <h2 style="margin-top:4px;">${zapatilla.nombre}</h2>
        <c:choose>
            <c:when test="${zapatilla.porcentajeDescuento > 0}">
                <p class="precio" style="font-size:1.4rem;">
                    <s style="color:#888;font-size:.8em;">S/ ${zapatilla.precio}</s> S/ ${zapatilla.precioFinal}
                    <span class="badge-descuento">-${zapatilla.porcentajeDescuento}%</span>
                </p>
            </c:when>
            <c:otherwise><p class="precio" style="font-size:1.4rem;">S/ ${zapatilla.precio}</p></c:otherwise>
        </c:choose>
        <p>${zapatilla.descripcion}</p>
        <p><strong>Materiales:</strong> ${zapatilla.materiales}</p>
        <p><strong>Color:</strong> ${zapatilla.color}</p>

        <c:if test="${sessionScope.usuario != null}">
        <form class="vertical" method="post" action="/carrito/agregar" style="max-width:300px;">
            <input type="hidden" name="idZapatilla" value="${zapatilla.id}">
            <label>Talla</label>
            <select name="talla" required>
                <c:forEach items="${zapatilla.tallas}" var="t">
                    <option value="${t.talla}" ${t.stock == 0 ? 'disabled' : ''}>
                        ${t.talla} <c:choose><c:when test="${t.stock == 0}">(agotada)</c:when><c:otherwise>(${t.stock} disp.)</c:otherwise></c:choose>
                    </option>
                </c:forEach>
            </select>
            <label>Cantidad</label>
            <input type="number" name="cantidad" value="1" min="1" required>
            <button class="btn btn-acento" type="submit">Añadir al carrito</button>
        </form>
        </c:if>
        <c:if test="${sessionScope.usuario == null}">
            <p><a href="/login">Inicia sesión</a> para comprar esta zapatilla.</p>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
