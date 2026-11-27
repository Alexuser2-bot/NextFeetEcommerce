package com.nextfeet.nextfeet.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.dto.FiltroCatalogo;
import com.nextfeet.nextfeet.model.HistorialStock;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.repository.ZapatillaDAO;
import com.nextfeet.nextfeet.util.Pagina;

@Service
public class ZapatillaServiceImpl implements ZapatillaService {

    private static final int TAMANO_PAGINA = 12;
    private static final int TAMANO_PAGINA_ADMIN = 10;

    private final ZapatillaDAO zapatillaDAO;

    public ZapatillaServiceImpl(ZapatillaDAO zapatillaDAO) {
        this.zapatillaDAO = zapatillaDAO;
    }

    @Override
    public Pagina<Zapatilla> verCatalogo(FiltroCatalogo filtro) {
        return zapatillaDAO.buscarCatalogo(filtro, TAMANO_PAGINA, true);
    }

    @Override
    public Zapatilla verFichaTecnica(int id) {
        Zapatilla z = zapatillaDAO.buscarPorId(id);
        if (z == null || !z.isActivo()) {
            throw new IllegalStateException("La zapatilla no está disponible.");
        }
        return z;
    }

    @Override
    public List<String> listarMarcas() {
        return zapatillaDAO.listarMarcas();
    }

    @Override
    public Pagina<Zapatilla> listarInventario(String termino, int pagina) {
        return zapatillaDAO.buscarInventario(termino, pagina, TAMANO_PAGINA_ADMIN);
    }

    @Override
    public void registrar(Zapatilla z) {
        validarDatosBasicos(z);
        if (z.getTallas() == null || z.getTallas().isEmpty()) {
            throw new IllegalStateException("Debes registrar al menos una talla con su stock inicial.");
        }
        z.setSku(generarSku(z.getMarca()));
        zapatillaDAO.crear(z);
    }

    @Override
    public void actualizar(Zapatilla z) {
        Zapatilla existente = obtenerParaAdmin(z.getId());
        validarDatosBasicos(z);
        z.setSku(existente.getSku());
        zapatillaDAO.actualizar(z);
        if (z.getTallas() != null && !z.getTallas().isEmpty()) {
            zapatillaDAO.guardarTallas(z.getId(), z.getTallas());
        }
    }

    @Override
    public void actualizarStock(int idZapatilla, String talla, int nuevoStock, String motivo) {
        if (nuevoStock < 0) {
            throw new IllegalStateException("El stock no puede ser negativo.");
        }
        ZapatillaTalla actual = zapatillaDAO.buscarTalla(idZapatilla, talla);
        int anterior = actual == null ? 0 : actual.getStock();
        zapatillaDAO.actualizarStock(idZapatilla, talla, nuevoStock);
        zapatillaDAO.registrarHistorialStock(idZapatilla, talla, anterior, nuevoStock,
                motivo == null || motivo.isBlank() ? "Ajuste manual" : motivo);
    }

    @Override
    public void cambiarVisibilidad(int id, boolean activo) {
        obtenerParaAdmin(id);
        zapatillaDAO.cambiarEstado(id, activo);
    }

    @Override
    public void eliminar(int id) {
        Zapatilla z = obtenerParaAdmin(id);
        if (z.getStockTotal() > 0) {
            throw new IllegalStateException("No se puede eliminar: la zapatilla aún tiene stock disponible. "
                    + "Puedes desactivarla del catálogo en su lugar.");
        }
        if (zapatillaDAO.tienePedidosAsociados(id)) {
            throw new IllegalStateException("No se puede eliminar: la zapatilla tiene pedidos asociados. " +
                    "Puedes desactivarla en su lugar.");
        }
        zapatillaDAO.eliminar(id);
    }

    @Override
    public List<HistorialStock> historialStock(int id) {
        return zapatillaDAO.listarHistorialStock(id);
    }

    @Override
    public ZapatillaTalla verificarDisponibilidad(int idZapatilla, String talla, int cantidadSolicitada) {
        ZapatillaTalla t = zapatillaDAO.buscarTalla(idZapatilla, talla);
        if (t == null || t.getStock() < cantidadSolicitada) {
            throw new IllegalStateException("No hay stock suficiente para la talla " + talla + ".");
        }
        return t;
    }

    @Override
    public ZapatillaTalla verificarDisponibilidadSinLanzar(int idZapatilla, String talla) {
        return zapatillaDAO.buscarTalla(idZapatilla, talla);
    }

    private Zapatilla obtenerParaAdmin(int id) {
        Zapatilla z = zapatillaDAO.buscarPorId(id);
        if (z == null) {
            throw new IllegalStateException("La zapatilla no existe.");
        }
        return z;
    }

    private void validarDatosBasicos(Zapatilla z) {
        if (z.getNombre() == null || z.getNombre().isBlank() || z.getMarca() == null || z.getMarca().isBlank()) {
            throw new IllegalStateException("El nombre y la marca son obligatorios.");
        }
        if (z.getPrecio() == null || z.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("El precio debe ser mayor a cero.");
        }
        if (z.getIdCategoria() == null) {
            throw new IllegalStateException("Debes seleccionar una categoría.");
        }
    }

    private String generarSku(String marca) {
        String prefijo = marca == null || marca.isBlank() ? "NF" : marca.substring(0, Math.min(3, marca.length())).toUpperCase();
        return prefijo + "-" + System.currentTimeMillis() % 100000;
    }

}
