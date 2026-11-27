package com.nextfeet.nextfeet.model;

public class Direccion {

    private Integer id;
    private Integer idUsuario;
    private String departamento;
    private String provincia;
    private String distrito;
    private String calle;
    private boolean principal;

    public Direccion() {
    }

    // Dirección en una sola línea; es el texto que se guarda en el pedido
    public String getTextoCompleto() {
        return calle + ", " + distrito + ", " + provincia + ", " + departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

}
