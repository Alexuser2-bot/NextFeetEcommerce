package com.nextfeet.nextfeet.cart;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.service.ZapatillaService;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoDAO carritoDAO;
    private final ZapatillaService zapatillaService;

    public CarritoServiceImpl(CarritoDAO carritoDAO, ZapatillaService zapatillaService) {
        this.carritoDAO = carritoDAO;
        this.zapatillaService = zapatillaService;
    }

    @Override
    public Carrito verCarrito(int idUsuario) {
        return carritoDAO.obtenerCarrito(idUsuario);
    }

    @Override
    public void agregarItem(int idUsuario, int idZapatilla, String talla, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalStateException("La cantidad debe ser mayor a cero.");
        }
        int idCarrito = carritoDAO.obtenerOCrearCarrito(idUsuario);
        int yaEnCarrito = carritoDAO.cantidadEnItem(idCarrito, idZapatilla, talla);
        zapatillaService.verificarDisponibilidad(idZapatilla, talla, yaEnCarrito + cantidad);
        carritoDAO.agregarOIncrementarItem(idCarrito, idZapatilla, talla, cantidad);
    }

    @Override
    public void actualizarCantidad(int idUsuario, int idZapatilla, String talla, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalStateException("La cantidad debe ser mayor a cero.");
        }
        int idCarrito = carritoDAO.obtenerOCrearCarrito(idUsuario);
        zapatillaService.verificarDisponibilidad(idZapatilla, talla, cantidad);
        carritoDAO.actualizarCantidad(idCarrito, idZapatilla, talla, cantidad);
    }

    @Override
    public void eliminarItem(int idUsuario, int idZapatilla, String talla) {
        int idCarrito = carritoDAO.obtenerOCrearCarrito(idUsuario);
        carritoDAO.eliminarItem(idCarrito, idZapatilla, talla);
    }

    @Override
    public void vaciarCarrito(int idUsuario) {
        int idCarrito = carritoDAO.obtenerOCrearCarrito(idUsuario);
        carritoDAO.vaciar(idCarrito);
    }

}
