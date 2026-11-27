package com.nextfeet.nextfeet.service;

import java.util.List;

import com.nextfeet.nextfeet.dto.FiltroCatalogo;
import com.nextfeet.nextfeet.model.HistorialStock;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.util.Pagina;

public interface ZapatillaService {

    Pagina<Zapatilla> verCatalogo(FiltroCatalogo filtro);


    Zapatilla verFichaTecnica(int id);

    List<String> listarMarcas();

    Pagina<Zapatilla> listarInventario(String termino, int pagina);

    void registrar(Zapatilla zapatilla);

    void actualizar(Zapatilla zapatilla);

    void actualizarStock(int idZapatilla, String talla, int nuevoStock, String motivo);

    void cambiarVisibilidad(int id, boolean activo);

    void eliminar(int id);

    List<HistorialStock> historialStock(int id);

    ZapatillaTalla verificarDisponibilidad(int idZapatilla, String talla, int cantidadSolicitada);

    ZapatillaTalla verificarDisponibilidadSinLanzar(int idZapatilla, String talla);

}
