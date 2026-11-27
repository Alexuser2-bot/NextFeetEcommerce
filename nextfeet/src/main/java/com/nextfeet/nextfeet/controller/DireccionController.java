package com.nextfeet.nextfeet.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.model.Direccion;
import com.nextfeet.nextfeet.service.DireccionService;
import com.nextfeet.nextfeet.util.Sesion;

// Interfaces 29, 30 y 31: listado, registro y edición de direcciones del cliente
@Controller
@RequestMapping("/direcciones")
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        model.addAttribute("direcciones", direccionService.listar(Sesion.idUsuario(session)));
        return "direcciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("direccion", new Direccion());
        return "direcciones/formulario";
    }

    // "volver" = carrito cuando la dirección se registra desde el carrito (RN-05)
    @PostMapping("/nueva")
    public String guardar(HttpSession session, @ModelAttribute("direccion") Direccion direccion,
            @RequestParam(value = "volver", required = false) String volver, Model model) {
        try {
            boolean esLaPrimera = direccionService.listar(Sesion.idUsuario(session)).isEmpty();
            direccionService.agregar(Sesion.idUsuario(session), direccion);
            if ("carrito".equals(volver)) {
                return "redirect:/carrito";
            }
            return esLaPrimera ? "redirect:/direcciones?principalNueva" : "redirect:/direcciones?guardada";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "direcciones/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(HttpSession session, @PathVariable int id, Model model) {
        try {
            model.addAttribute("direccion", direccionService.obtener(Sesion.idUsuario(session), id));
            return "direcciones/formulario";
        } catch (IllegalStateException ex) {
            return "redirect:/direcciones";
        }
    }

    @PostMapping("/{id}/editar")
    public String actualizar(HttpSession session, @PathVariable int id,
            @ModelAttribute("direccion") Direccion direccion, Model model) {
        try {
            direccion.setId(id);
            direccionService.actualizar(Sesion.idUsuario(session), direccion);
            return "redirect:/direcciones?actualizada";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "direcciones/formulario";
        }
    }

    @PostMapping("/principal")
    public String hacerPrincipal(HttpSession session, @RequestParam("idDireccion") int idDireccion) {
        try {
            direccionService.hacerPrincipal(Sesion.idUsuario(session), idDireccion);
        } catch (IllegalStateException ex) {
            // Si la dirección no es suya, simplemente se vuelve al listado
        }
        return "redirect:/direcciones?principalCambiada";
    }

}
