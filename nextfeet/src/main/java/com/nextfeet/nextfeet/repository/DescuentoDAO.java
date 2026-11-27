package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.model.Descuento;
import com.nextfeet.nextfeet.model.Zapatilla;

public interface DescuentoDAO {

    List<Descuento> listar();

    Descuento buscarPorId(int id);

    Descuento buscarPorNombre(String nombre);

    // Devuelve el id del descuento creado
    int crear(Descuento d);

    void actualizar(Descuento d);

    // Deja asignado el descuento solo a las zapatillas indicadas
    void asignarZapatillas(int idDescuento, List<Integer> idsZapatillas);

    List<Zapatilla> listarZapatillas();

}
