package com.nextfeet.nextfeet.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.util.Sesion;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        Usuario usuario = session == null ? null : Sesion.usuario(session);
        String uri = request.getRequestURI();

        if (usuario == null) {
            response.sendRedirect("/login");
            return false;
        }
        boolean zonaAdmin = uri.startsWith("/admin");
        if (zonaAdmin && !usuario.isAdmin()) {
            response.sendRedirect("/");
            return false;
        }
        if (!zonaAdmin && usuario.isAdmin()) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }

}
