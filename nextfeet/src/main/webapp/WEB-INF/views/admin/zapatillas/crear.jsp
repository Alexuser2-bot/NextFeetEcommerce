<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Nueva zapatilla - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<h2>Registrar nueva zapatilla</h2>
<c:if test="${not empty error}"><div class="alerta alerta-error">${error}</div></c:if>

<form class="vertical" method="post" action="/admin/zapatillas/nueva" style="max-width:600px;">
    <div class="fila">
        <div><label>Nombre</label><input name="nombre" required></div>
        <div><label>Marca</label><select name="marca" required>
            <c:forEach items="${marcas}" var="m"><option value="${m.nombre}">${m.nombre}</option></c:forEach>
        </select></div>
    </div>
    <div class="fila">
        <div>
            <label>Categoría</label>
            <select name="idCategoria" required>
                <c:forEach items="${categorias}" var="c"><option value="${c.id}">${c.nombre}</option></c:forEach>
            </select>
        </div>
        <div><label>Precio (S/)</label><input type="number" step="0.01" name="precio" required></div>
    </div>
    <label>Descripción</label>
    <textarea name="descripcion" rows="3"></textarea>
    <div class="fila">
        <div><label>Materiales</label><input name="materiales"></div>
        <div><label>Color</label><input name="color"></div>
    </div>
    <label>URL de la imagen (ej: /img/airrun-pro.webp)</label>
    <input name="imagenUrl" placeholder="/img/placeholder.svg">

    <label>Tallas y stock inicial</label>
    <div id="tallas-contenedor">
        <div class="fila talla-fila">
            <input type="text" name="tallas" placeholder="Talla (ej. 40)" required>
            <input type="number" name="stocks" placeholder="Stock" min="0" required>
        </div>
    </div>
    <button type="button" class="btn btn-secundario btn-pequeno" onclick="agregarTalla()">+ Añadir talla</button>

    <button class="btn btn-acento" type="submit" style="margin-top:14px;">Registrar zapatilla</button>
</form>

<script>
function agregarTalla() {
    var cont = document.getElementById('tallas-contenedor');
    var fila = document.createElement('div');
    fila.className = 'fila talla-fila';
    fila.innerHTML = '<input type="text" name="tallas" placeholder="Talla (ej. 40)" required>' +
                      '<input type="number" name="stocks" placeholder="Stock" min="0" required>';
    cont.appendChild(fila);
}
</script>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
