package com.nextfeet.nextfeet.util;

import java.util.List;

public class Pagina<T> {

    private final List<T> contenido;
    private final int pagina;
    private final int tamano;
    private final long total;

    public Pagina(List<T> contenido, int pagina, int tamano, long total) {
        this.contenido = contenido;
        this.pagina = pagina;
        this.tamano = tamano;
        this.total = total;
    }

    public List<T> getContenido() {
        return contenido;
    }

    public int getPagina() {
        return pagina;
    }

    public int getTamano() {
        return tamano;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPaginas() {
        return (int) Math.max(1, Math.ceil((double) total / tamano));
    }

    public boolean isTienePrevia() {
        return pagina > 1;
    }

    public boolean isTieneSiguiente() {
        return pagina < getTotalPaginas();
    }

}
