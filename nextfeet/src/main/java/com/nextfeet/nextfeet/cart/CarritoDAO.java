package com.nextfeet.nextfeet.cart;

public interface CarritoDAO {

    int obtenerOCrearCarrito(int idUsuario);

    Carrito obtenerCarrito(int idUsuario);

    void agregarOIncrementarItem(int idCarrito, int idZapatilla, String talla, int cantidad);

    void actualizarCantidad(int idCarrito, int idZapatilla, String talla, int cantidad);

    void eliminarItem(int idCarrito, int idZapatilla, String talla);

    void vaciar(int idCarrito);

    int contarItems(int idCarrito, int idZapatilla, String talla);

    int cantidadEnItem(int idCarrito, int idZapatilla, String talla);

}
