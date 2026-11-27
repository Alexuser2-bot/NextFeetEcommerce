package com.nextfeet.nextfeet.repository;

import java.util.List;

import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.util.Pagina;

public interface UsuarioDAO {

    Usuario buscarPorId(int id);

    Usuario buscarPorCorreo(String correo);

    int crear(Usuario usuario);

    void actualizarDatos(Usuario usuario);

    void actualizarPassword(int id, String passwordCifrada);

    void cambiarEstado(int id, boolean activo);

    List<Usuario> listarPorRol(String rol);

    long contarActivosPorRol(String rol);

    Pagina<Usuario> listarPorRolPaginado(String rol, String busqueda, Boolean activo, int pagina, int tamano);

}
