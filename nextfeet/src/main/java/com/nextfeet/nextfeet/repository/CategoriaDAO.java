package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.model.Categoria;

public interface CategoriaDAO {

    List<Categoria> listarTodas();

    Categoria buscarPorId(int id);

    Categoria buscarPorNombre(String nombre);

    int crear(Categoria categoria);

    void actualizar(Categoria categoria);

    void cambiarEstado(int id, boolean activo);

    void eliminar(int id);

    long contarZapatillas(int idCategoria);

}
