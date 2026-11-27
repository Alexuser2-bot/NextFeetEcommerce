<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="titulo" value="NextFeet - Zapatillas para cada paso" scope="request" />
<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<style>
    .flyer { position: relative; overflow: hidden; max-width: 640px; margin: 10px auto 30px; padding: 34px 30px 30px;
             color: #fff; text-align: center; border-radius: 10px;
             background: radial-gradient(circle at 20% 10%, #3b5a99 0%, #1a2b4c 45%, #0c1426 100%); box-shadow: 0 8px 28px rgba(0,0,0,.28); }
    .flyer::before { content: "SNEAKERS"; position: absolute; right: -90px; top: 120px; transform: rotate(90deg);
                     font-size: 5rem; font-weight: 900; letter-spacing: 8px; color: rgba(255,255,255,.06); pointer-events: none; }
    .flyer > * { position: relative; }
    .flyer-logo { font-size: 3rem; font-weight: 900; letter-spacing: 3px; margin: 0; line-height: 1; }
    .flyer-logo span { color: #ff6b35; }
    .flyer-lema { margin: 6px 0 22px; letter-spacing: 4px; font-size: .85rem; color: #cbd5e8; }
    .flyer h2 { margin: 0 0 6px; font-size: 1.35rem; color: #ff6b35; }
    .flyer p { margin: 0 auto 16px; max-width: 460px; line-height: 1.5; }
    .flyer-fotos { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; margin: 18px 0 24px; }
    .flyer-fotos div { background: #fff; border-radius: 10px; padding: 8px; box-shadow: 0 4px 14px rgba(0,0,0,.35); }
    .flyer-fotos img { width: 100%; height: 130px; object-fit: contain; display: block; }
</style>

<div class="flyer">
    <h1 class="flyer-logo">Next<span>Feet</span></h1>
    <div class="flyer-lema">ZAPATILLAS PARA CADA PASO</div>

    <h2>Running · Urbanas · Básquet · Casuales</h2>
    <p>Encuentra tu talla ideal en zapatillas de Nike, Adidas, Puma, Converse y Skechers, todo en un solo lugar.</p>

    <div class="flyer-fotos">
        <div><img src="/img/airrun-pro.webp" alt="Zapatilla de running"></div>
        <div><img src="/img/courtking-2.jpg" alt="Zapatilla de básquet"></div>
        <div><img src="/img/street-classic.webp" alt="Zapatilla urbana"></div>
        <div><img src="/img/comfort-walk.webp" alt="Zapatilla casual"></div>
    </div>

    <p>Nuestro objetivo es que encuentres tus zapatillas ideales de forma fácil y rápida, para que disfrutes cada nuevo paso.</p>

</div>

</main>
</body>
</html>
