package com.nextfeet.nextfeet.cart;

import java.math.BigDecimal;

public class CarritoItem {

    private Integer id;
    private Integer idCarrito;
    private Integer idZapatilla;
    private String talla;
    private int cantidad;
    private String nombre;
    private String marca;
    private String imagenUrl;
    private BigDecimal precioUnitario;
    private int stockDisponible;

    public CarritoItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getIdZapatilla() {
        return idZapatilla;
    }

    public void setIdZapatilla(Integer idZapatilla) {
        this.idZapatilla = idZapatilla;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario == null ? BigDecimal.ZERO : precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

}
