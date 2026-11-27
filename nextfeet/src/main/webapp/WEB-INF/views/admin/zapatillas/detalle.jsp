<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="${zapatilla.nombre} - Admin" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>${zapatilla.nombre} <span class="badge-estado">${zapatilla.activo ? 'Activa' : 'Inactiva'}</span></h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="/admin/zapatillas/${zapatilla.id}/actualizar"
      style="max-width:600px;">
    <img src="${zapatilla.imagenUrl}" style="width:160px;height:160px;object-fit:cover;border-radius:8px;">
    <div class="fila">
        <div><label>Nombre</label><input name="nombre" value="${zapatilla.nombre}" required></div>
        <div><label>Marca</label><select name="marca" required>
            <c:forEach items="${marcas}" var="m"><option value="${m.nombre}" ${m.nombre == zapatilla.marca ? 'selected' : ''}>${m.nombre}</option></c:forEach>
        </select></div>
    </div>
    <div class="fila">
        <div>
            <label>Categoría</label>
            <select name="idCategoria" required>
                <c:forEach items="${categorias}" var="c">
                    <option value="${c.id}" ${c.id == zapatilla.idCategoria ? 'selected' : ''}>${c.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div><label>Precio (S/)</label><input type="number" step="0.01" name="precio" value="${zapatilla.precio}" required></div>
    </div>
    <label>Descripción</label>
    <textarea name="descripcion" rows="3">${zapatilla.descripcion}</textarea>
    <div class="fila">
        <div><label>Materiales</label><input name="materiales" value="${zapatilla.materiales}"></div>
        <div><label>Color</label><input name="color" value="${zapatilla.color}"></div>
    </div>
    <label>URL de la imagen (opcional, ej: /img/airrun-pro.webp)</label>
    <input name="imagenUrl" value="${zapatilla.imagenUrl}">

    <label>Tallas y stock</label>
    <div id="tallas-contenedor">
        <c:forEach items="${zapatilla.tallas}" var="t">
        <div class="fila talla-fila">
            <input type="text" name="tallas" value="${t.talla}" required>
            <input type="number" name="stocks" value="${t.stock}" min="0" required>
        </div>
        </c:forEach>
    </div>
    <button type="button" class="btn btn-secundario btn-pequeno" onclick="agregarTalla()">+ Añadir talla</button>

    <button class="btn" type="submit" style="margin-top:14px;">Guardar cambios</button>
</form>

<div class="fila" style="margin-top:16px;">
    <form method="post" action="/admin/zapatillas/${zapatilla.id}/estado">
        <input type="hidden" name="activo" value="${!zapatilla.activo}">
        <button class="btn btn-secundario" type="submit">${zapatilla.activo ? 'Desactivar del catálogo' : 'Activar en catálogo'}</button>
    </form>
</div>

<h3>Historial de stock</h3>
<table>
    <thead><tr><th>Fecha</th><th>Talla</th><th>Anterior</th><th>Nuevo</th><th>Motivo</th></tr></thead>
    <tbody>
    <c:forEach items="${historial}" var="h">
        <tr><td>${h.fechaTexto}</td><td>${h.talla}</td><td>${h.cantidadAnterior}</td><td>${h.cantidadNueva}</td><td>${h.motivo}</td></tr>
    </c:forEach>
    <c:if test="${empty historial}"><tr><td colspan="5">Sin movimientos registrados.</td></tr></c:if>
    </tbody>
</table>

<div class="zona-peligro">
    <h4>Eliminar zapatilla</h4>
    <p>Solo es posible si la zapatilla no tiene stock disponible ni pedidos asociados. Si no cumple, usa «Desactivar del catálogo».</p>
    <form method="post" action="/admin/zapatillas/${zapatilla.id}/eliminar" onsubmit="return confirm('¿Eliminar definitivamente esta zapatilla?');">
        <button class="btn btn-peligro" type="submit">Eliminar zapatilla</button>
    </form>
</div>

<script>
function agregarTalla() {
    var cont = document.getElementById('tallas-contenedor');
    var fila = document.createElement('div');
    fila.className = 'fila talla-fila';
    fila.innerHTML = '<input type="text" name="tallas" placeholder="Talla" required>' +
                      '<input type="number" name="stocks" placeholder="Stock" min="0" required>';
    cont.appendChild(fila);
}
</script>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
