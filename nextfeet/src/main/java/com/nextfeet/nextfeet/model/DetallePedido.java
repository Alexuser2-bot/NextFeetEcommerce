package com.nextfeet.nextfeet.model;

import java.math.BigDecimal;

public class DetallePedido {

    private Integer id;
    private Integer idPedido;
    private Integer idZapatilla;
    private String nombreZapatilla;
    private String talla;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetallePedido() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdZapatilla() {
        return idZapatilla;
    }

    public void setIdZapatilla(Integer idZapatilla) {
        this.idZapatilla = idZapatilla;
    }

    public String getNombreZapatilla() {
        return nombreZapatilla;
    }

    public void setNombreZapatilla(String nombreZapatilla) {
        this.nombreZapatilla = nombreZapatilla;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
