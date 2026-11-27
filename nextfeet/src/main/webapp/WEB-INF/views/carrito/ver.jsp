<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Carrito - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Carrito de compras</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>
<c:if test="${param.agregado != null}"><div class="alerta alerta-exito">Producto añadido al carrito.</div></c:if>

<c:choose>
<c:when test="${carrito.vacio}">
    <p>Tu carrito está vacío. <a href="/catalogo">Ir al catálogo</a></p>
</c:when>
<c:otherwise>
<table>
    <thead><tr><th>Producto</th><th>Talla</th><th>Precio</th><th>Cantidad</th><th>Subtotal</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${carrito.items}" var="i">
        <tr>
            <td style="display:flex;gap:10px;align-items:center;">
                <img src="${i.imagenUrl}" style="width:50px;height:50px;object-fit:cover;border-radius:4px;">
                ${i.marca} ${i.nombre}
            </td>
            <td>${i.talla}</td>
            <td>S/ ${i.precioUnitario}</td>
            <td>
                <form method="post" action="/carrito/actualizar" style="display:flex;gap:6px;">
                    <input type="hidden" name="idZapatilla" value="${i.idZapatilla}">
                    <input type="hidden" name="talla" value="${i.talla}">
                    <input type="number" name="cantidad" value="${i.cantidad}" min="1" max="${i.stockDisponible}" style="width:70px;">
                    <button class="btn btn-pequeno btn-secundario" type="submit">Actualizar</button>
                </form>
            </td>
            <td>S/ ${i.subtotal}</td>
            <td>
                <form method="post" action="/carrito/eliminar">
                    <input type="hidden" name="idZapatilla" value="${i.idZapatilla}">
                    <input type="hidden" name="talla" value="${i.talla}">
                    <button class="btn btn-pequeno btn-peligro" type="submit">Eliminar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="fila" style="justify-content:space-between;margin-top:16px;align-items:center;">
    <form method="post" action="/carrito/vaciar" onsubmit="return confirm('¿Vaciar todo el carrito?');">
        <button class="btn btn-peligro" type="submit">Vaciar carrito</button>
    </form>
    <div style="text-align:right;">
        <p>Subtotal: S/ ${carrito.total}</p>
        <c:choose>
            <c:when test="${carrito.envio > 0}">
                <p>Envío: S/ ${carrito.envio}</p>
                <p style="color:#b26a00;">Agrega S/ ${carrito.faltaParaEnvioGratis} más para obtener envío gratis</p>
            </c:when>
            <c:otherwise>
                <p>Envío: <span class="badge-estado" style="background:#e9f7ef;color:#1b7a3d;">GRATIS</span></p>
            </c:otherwise>
        </c:choose>
        <p style="font-size:1.2rem;">Total a pagar: <strong>S/ ${carrito.totalPagar}</strong></p>
        <form class="vertical" method="post" action="/checkout/pagar" style="text-align:left;max-width:380px;margin-left:auto;">
            <c:choose>
                <%-- Caso A: el cliente ya tiene direcciones, se muestra la principal precargada --%>
                <c:when test="${not empty direcciones}">
                    <label>Dirección de entrega</label>
                    <select name="direccion">
                        <c:forEach items="${direcciones}" var="d">
                            <option value="${d.textoCompleto}" ${d.textoCompleto == direccion || (empty direccion && d.principal) ? 'selected' : ''}>
                                ${d.textoCompleto}${d.principal ? ' (Principal)' : ''}
                            </option>
                        </c:forEach>
                    </select>
                    <small>Puedes administrar tus direcciones en <a href="/direcciones">Mis direcciones</a>.</small>
                </c:when>
                <%-- Caso B: el cliente no tiene direcciones; debe registrar una antes de pagar --%>
                <c:otherwise>
                    <input type="hidden" name="direccion" value="">
                    <div class="alerta alerta-error">Aún no tienes una dirección de entrega. Registra una para poder pagar.</div>
                    <a class="btn btn-acento" href="/direcciones/nueva?volver=carrito">Agregar dirección de entrega</a>
                </c:otherwise>
            </c:choose>
            <label>Número de celular</label>
            <input type="text" name="telefono" maxlength="9" pattern="[0-9]{9}" value="${not empty telefono ? telefono : sessionScope.usuario.telefono}" placeholder="9 dígitos" required>
            <button class="btn btn-acento" type="submit" ${empty direcciones ? 'disabled' : ''}>Pagar</button>
        </form>
    </div>
</div>
</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
