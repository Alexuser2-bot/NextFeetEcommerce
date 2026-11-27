package com.nextfeet.nextfeet.service;

import java.util.List;

import com.nextfeet.nextfeet.model.Categoria;

public interface CategoriaService {

    List<Categoria> listarTodas();

    Categoria obtener(int id);

    void crear(String nombre, String descripcion);

    void actualizar(int id, String nombre, String descripcion);

    void desactivarOEliminar(int id);

}
