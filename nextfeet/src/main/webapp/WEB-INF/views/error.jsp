<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ocurrió un error - NextFeet</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<main class="contenedor">
    <div class="tarjeta" style="max-width:520px;margin:60px auto;text-align:center;">
        <h2 style="color:#c62828;">Ocurrió un error</h2>
        <p>${not empty mensajeError ? mensajeError : message}</p>
        <a class="btn" href="/">Volver al inicio</a>
    </div>
</main>
</body>
</html>
