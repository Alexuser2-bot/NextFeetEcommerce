package com.nextfeet.nextfeet.service;

import java.util.List;

import com.nextfeet.nextfeet.model.Direccion;

public interface DireccionService {

    List<Direccion> listar(int idUsuario);

    Direccion obtener(int idUsuario, int idDireccion);

    void agregar(int idUsuario, Direccion direccion);

    void actualizar(int idUsuario, Direccion direccion);

    void hacerPrincipal(int idUsuario, int idDireccion);

}
