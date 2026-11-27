<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="Nosotros - NextFeet" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="tarjeta">
    <h2>Sobre NextFeet</h2>
    <p>NextFeet nace como respuesta a la alta tasa de fracaso de los emprendimientos de venta de
    zapatillas que dependen únicamente de un local físico. Nuestra misión es brindar a los
    pequeños negocios una plataforma de e-commerce accesible que les permita vender a nivel
    nacional, reducir sus costos operativos y competir en igualdad de condiciones.</p>
    <h3>Misión</h3>
    <p>Digitalizar la venta de zapatillas de emprendedores peruanos, ofreciendo una experiencia de
    compra ágil, segura y accesible para sus clientes.</p>
    <h3>Visión</h3>
    <p>Ser la plataforma de referencia para la digitalización de pequeños negocios de calzado en el Perú.</p>
    <h3>Valores</h3>
    <ul>
        <li>Accesibilidad tecnológica</li>
        <li>Transparencia con clientes y negocios aliados</li>
        <li>Mejora continua del servicio</li>
    </ul>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
