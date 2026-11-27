package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.dto.FiltroCatalogo;
import com.nextfeet.nextfeet.model.HistorialStock;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.util.Pagina;

public interface ZapatillaDAO {

    Zapatilla buscarPorId(int id);

    Pagina<Zapatilla> buscarCatalogo(FiltroCatalogo filtro, int tamano, boolean soloActivas);


    Pagina<Zapatilla> buscarInventario(String termino, int pagina, int tamano);

    List<String> listarMarcas();

    int crear(Zapatilla zapatilla);

    void actualizar(Zapatilla zapatilla);

    void cambiarEstado(int id, boolean activo);

    void eliminar(int id);

    boolean tienePedidosAsociados(int id);

    List<ZapatillaTalla> listarTallas(int idZapatilla);

    ZapatillaTalla buscarTalla(int idZapatilla, String talla);

    void guardarTallas(int idZapatilla, List<ZapatillaTalla> tallas);

    void actualizarStock(int idZapatilla, String talla, int nuevoStock);

    void registrarHistorialStock(int idZapatilla, String talla, int anterior, int nuevo, String motivo);

    List<HistorialStock> listarHistorialStock(int idZapatilla);

}
