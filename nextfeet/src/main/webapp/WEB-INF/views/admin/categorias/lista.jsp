<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Categorías - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/admin-header.jsp" />

<div class="fila" style="justify-content:space-between;align-items:center;">
    <h2>Categorías</h2>
    <a class="btn btn-acento" href="/admin/categorias/crear">+ Nueva categoría</a>
</div>

<table>
    <thead><tr><th>Nombre</th><th>Descripción</th><th>Productos</th><th>Estado</th><th></th></tr></thead>
    <tbody>
    <c:forEach items="${categorias}" var="c">
        <tr>
            <td>${c.nombre}</td>
            <td>${c.descripcion}</td>
            <td>${c.totalZapatillas}</td>
            <td>${c.activo ? 'Activa' : 'Inactiva'}</td>
            <td class="fila">
                <a class="btn btn-pequeno btn-secundario" href="/admin/categorias/${c.id}/editar">Editar</a>
                <form method="post" action="/admin/categorias/${c.id}/eliminar"
                      onsubmit="return confirm('${c.totalZapatillas == 0 ? '¿Eliminar esta categoría?' : '¿Cambiar el estado de esta categoría?'}');">
                    <button class="btn btn-pequeno btn-peligro" type="submit">
                        ${c.totalZapatillas == 0 ? 'Eliminar' : (c.activo ? 'Desactivar' : 'Activar')}
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/layout/admin-footer.jsp" />
