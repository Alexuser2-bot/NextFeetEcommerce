package com.nextfeet.nextfeet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Descuento {

    private Integer id;
    private String nombre;
    private BigDecimal porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activo;
    private int cantidadProductos;
    private List<Integer> idsZapatillas = new ArrayList<>();

    public Descuento() {
    }

    // Precio con el porcentaje de descuento aplicado (por ejemplo, 100 con 15% queda en 85.00)
    public static BigDecimal aplicar(BigDecimal precio, BigDecimal porcentaje) {
        return precio.multiply(BigDecimal.valueOf(100).subtract(porcentaje))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public List<Integer> getIdsZapatillas() {
        return idsZapatillas;
    }

    public void setIdsZapatillas(List<Integer> idsZapatillas) {
        this.idsZapatillas = idsZapatillas;
    }

}
