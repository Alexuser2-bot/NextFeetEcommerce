<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${titulo != null ? titulo : 'NextFeet Admin'}" /></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="franja-admin">Panel de administración — NextFeet</div>
<header class="nf-header">
    <div class="nf-barra">
        <a class="nf-logo" href="/admin">Next<span>Feet</span></a>
        <nav class="nf-nav">
            <a href="/admin/categorias">Categorías</a>
            <a href="/admin/zapatillas">Zapatillas</a>
            <a href="/admin/marcas">Marcas</a>
            <a href="/admin/descuentos">Descuentos</a>
            <a href="/admin/pedidos">Pedidos</a>
            <a href="/admin/clientes">Clientes</a>
            <a href="/admin/administradores">Administradores</a>
        </nav>
        <div class="nf-usuario">
            <a href="/admin/perfil">${sessionScope.usuario.nombres}</a>
            <a href="/logout">Salir</a>
        </div>
    </div>
</header>
<main class="contenedor">
