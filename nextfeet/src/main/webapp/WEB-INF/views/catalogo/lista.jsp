<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Catálogo - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<h2>Catálogo de zapatillas</h2>
<div class="fila" style="align-items:flex-start;">
    <form class="tarjeta" method="get" action="/catalogo" style="max-width:240px;">
        <label>Buscar</label>
        <input type="text" name="q" value="${filtro.q}" placeholder="Nombre o marca">
        <label>Categoría</label>
        <select name="categoria">
            <option value="">Todas</option>
            <c:forEach items="${categorias}" var="c">
                <option value="${c.id}" ${filtro.categoriaId == c.id ? 'selected' : ''}>${c.nombre}</option>
            </c:forEach>
        </select>
        <label>Marca</label>
        <select name="marca">
            <option value="">Todas</option>
            <c:forEach items="${marcas}" var="m">
                <option value="${m}" ${filtro.marca == m ? 'selected' : ''}>${m}</option>
            </c:forEach>
        </select>
        <label>Talla</label>
        <input type="text" name="talla" value="${filtro.talla}" placeholder="Ej. 40">
        <label>Precio mín.</label>
        <input type="number" step="0.01" name="precioMin" value="${filtro.precioMin}">
        <label>Precio máx.</label>
        <input type="number" step="0.01" name="precioMax" value="${filtro.precioMax}">
        <button class="btn" type="submit" style="margin-top:10px;">Aplicar filtros</button>
    </form>

    <div style="flex:1;">
        <div class="grilla">
            <c:forEach items="${resultado.contenido}" var="z">
                <div class="producto-card">
                    <img src="${z.imagenUrl}" alt="${z.nombre}">
                    <div class="cuerpo">
                        <span class="marca">${z.marca}</span>
                        <span class="nombre">${z.nombre}</span>
                        <c:choose>
                            <c:when test="${z.porcentajeDescuento > 0}">
                                <span class="precio"><s style="color:#888;font-size:.85em;">S/ ${z.precio}</s> S/ ${z.precioFinal}</span>
                                <span class="badge-descuento">-${z.porcentajeDescuento}%</span>
                            </c:when>
                            <c:otherwise><span class="precio">S/ ${z.precio}</span></c:otherwise>
                        </c:choose>
                        <a class="btn btn-pequeno" href="/zapatilla/${z.id}">Ver producto</a>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty resultado.contenido}"><p>No se encontraron zapatillas con esos filtros.</p></c:if>
        </div>

        <div class="paginacion">
            <c:if test="${resultado.tienePrevia}">
                <a class="btn btn-secundario btn-pequeno" href="?pagina=${resultado.pagina - 1}&q=${filtro.q}">&laquo; Anterior</a>
            </c:if>
            <span>Página ${resultado.pagina} de ${resultado.totalPaginas}</span>
            <c:if test="${resultado.tieneSiguiente}">
                <a class="btn btn-secundario btn-pequeno" href="?pagina=${resultado.pagina + 1}&q=${filtro.q}">Siguiente &raquo;</a>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
