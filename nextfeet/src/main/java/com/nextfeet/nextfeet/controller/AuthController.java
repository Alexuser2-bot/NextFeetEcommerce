package com.nextfeet.nextfeet.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.dto.RegistroDTO;
import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.service.UsuarioService;
import com.nextfeet.nextfeet.util.Sesion;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        if (Sesion.usuario(session) != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam("correo") String correo,
            @RequestParam("password") String password, HttpSession session, Model model) {
        try {
            Usuario usuario = usuarioService.iniciarSesion(correo, password);
            session.setAttribute(Sesion.USUARIO, usuario);
            return usuario.isAdmin() ? "redirect:/admin" : "redirect:/";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registro", new RegistroDTO());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute("registro") RegistroDTO dto, Model model) {
        try {
            usuarioService.registrarCliente(dto);
            return "redirect:/login?registrado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "auth/registro";
        }
    }

}
