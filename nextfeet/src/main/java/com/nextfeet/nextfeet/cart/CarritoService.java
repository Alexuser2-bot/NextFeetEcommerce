package com.nextfeet.nextfeet.cart;

public interface CarritoService {

    Carrito verCarrito(int idUsuario);

    void agregarItem(int idUsuario, int idZapatilla, String talla, int cantidad);

    void actualizarCantidad(int idUsuario, int idZapatilla, String talla, int cantidad);

    void eliminarItem(int idUsuario, int idZapatilla, String talla);

    void vaciarCarrito(int idUsuario);

}
