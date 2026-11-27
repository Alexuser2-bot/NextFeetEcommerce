package com.nextfeet.nextfeet.cart;

import java.math.BigDecimal;
import java.util.List;

public class Carrito {

    public static final BigDecimal ENVIO_GRATIS_DESDE = new BigDecimal("300");
    public static final BigDecimal COSTO_ENVIO = new BigDecimal("12.00");

    private Integer id;
    private Integer idUsuario;
    private List<CarritoItem> items;

    public Carrito() {
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

    public List<CarritoItem> getItems() {
        return items;
    }

    public void setItems(List<CarritoItem> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (items != null) {
            for (CarritoItem i : items) {
                total = total.add(i.getSubtotal());
            }
        }
        return total;
    }

    public int getCantidadTotal() {
        int total = 0;
        if (items != null) {
            for (CarritoItem item : items) {
                total += item.getCantidad();
            }
        }
        return total;
    }

    public boolean isVacio() {
        return items == null || items.isEmpty();
    }

    // Envío: gratis si el subtotal es S/ 300 o más; si no, cuesta S/ 12.00
    public BigDecimal getEnvio() {
        if (getTotal().compareTo(ENVIO_GRATIS_DESDE) >= 0) {
            return BigDecimal.ZERO;
        }
        return COSTO_ENVIO;
    }

    // Cuánto falta para que el envío sea gratis (solo se usa cuando se cobra envío)
    public BigDecimal getFaltaParaEnvioGratis() {
        return ENVIO_GRATIS_DESDE.subtract(getTotal());
    }

    public BigDecimal getTotalPagar() {
        return getTotal().add(getEnvio());
    }

}
