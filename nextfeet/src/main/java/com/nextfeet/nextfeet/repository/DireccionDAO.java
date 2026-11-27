package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.model.Direccion;

public interface DireccionDAO {

    List<Direccion> listarPorUsuario(int idUsuario);

    Direccion buscarPorId(int id);

    void crear(Direccion d);

    void actualizar(Direccion d);

    void quitarPrincipal(int idUsuario);

    void marcarPrincipal(int id);

}
