package com.nextfeet.nextfeet.service;

import java.util.List;

import com.nextfeet.nextfeet.dto.RegistroDTO;
import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.util.Pagina;

public interface UsuarioService {

    Usuario iniciarSesion(String correo, String password);

    void registrarCliente(RegistroDTO dto);

    Usuario obtenerPerfil(int idUsuario);

    void actualizarPerfil(int idUsuario, String nombres, String apellidos, String telefono,
            String nuevaPassword);

    void desactivarCuentaPropia(int idUsuario, String passwordActual);

    List<Usuario> listarAdministradores();

    void registrarAdministrador(RegistroDTO dto);

    void desactivarAdministrador(int idObjetivo, int idAdminEnSesion);

    Pagina<Usuario> listarClientes(String busqueda, Boolean activo, int pagina);

    void actualizarCliente(int idCliente, String nombres, String apellidos, String telefono);

    void desactivarCliente(int idCliente);

}
