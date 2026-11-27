package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.model.Marca;

public interface MarcaDAO {

    List<Marca> listar(String busqueda);

    Marca buscarPorId(int id);

    Marca buscarPorNombre(String nombre);

    void crear(String nombre);

    void actualizar(int id, String nombre);

    void renombrarEnZapatillas(String nombreAnterior, String nombreNuevo);

}
