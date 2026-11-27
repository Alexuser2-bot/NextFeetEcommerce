package com.nextfeet.nextfeet.service;

import java.util.List;

import com.nextfeet.nextfeet.model.Marca;

public interface MarcaService {

    List<Marca> listar(String busqueda);

    Marca obtener(int id);

    void registrar(String nombre);

    void actualizar(int id, String nombre);

}
