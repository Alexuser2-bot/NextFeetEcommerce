<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${titulo != null ? titulo : 'NextFeet'}" /></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<header class="nf-header">
    <div class="nf-barra">
        <a class="nf-logo" href="/">Next<span>Feet</span></a>
        <nav class="nf-nav">
            <a href="/catalogo">Catálogo</a>
            <a href="/nosotros">Nosotros</a>
            <a href="/contacto">Contacto</a>
        </nav>
        <div class="nf-usuario">
            <c:choose>
                <c:when test="${sessionScope.usuario != null}">
                    <a href="/carrito">🛒 Carrito</a>
                    <a href="/mis-pedidos">Mis pedidos</a>
                    <a href="/direcciones">Mis direcciones</a>
                    <a href="/perfil">${sessionScope.usuario.nombres}</a>
                    <a href="/logout">Salir</a>
                </c:when>
                <c:otherwise>
                    <a href="/login">Iniciar sesión</a>
                    <a href="/registro">Registrarme</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
<main class="contenedor">
