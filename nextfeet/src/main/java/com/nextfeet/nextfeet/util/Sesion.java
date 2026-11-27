package com.nextfeet.nextfeet.util;

import jakarta.servlet.http.HttpSession;

import com.nextfeet.nextfeet.model.Usuario;

public final class Sesion {

    public static final String USUARIO = "usuario";

    private Sesion() {
    }

    public static Usuario usuario(HttpSession session) {
        return (Usuario) session.getAttribute(USUARIO);
    }

    public static int idUsuario(HttpSession session) {
        return usuario(session).getId();
    }

}
