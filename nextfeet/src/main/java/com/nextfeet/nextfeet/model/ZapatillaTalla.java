package com.nextfeet.nextfeet.model;

public class ZapatillaTalla {

    private Integer id;
    private Integer idZapatilla;
    private String talla;
    private int stock;

    public ZapatillaTalla() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
