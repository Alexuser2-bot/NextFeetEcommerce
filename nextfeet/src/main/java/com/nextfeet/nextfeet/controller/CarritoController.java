package com.nextfeet.nextfeet.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.cart.CarritoService;
import com.nextfeet.nextfeet.service.DireccionService;
import com.nextfeet.nextfeet.util.Sesion;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final DireccionService direccionService;

    public CarritoController(CarritoService carritoService, DireccionService direccionService) {
        this.carritoService = carritoService;
        this.direccionService = direccionService;
    }

    @GetMapping
    public String ver(HttpSession session, Model model) {
        model.addAttribute("carrito", carritoService.verCarrito(Sesion.idUsuario(session)));
        model.addAttribute("direcciones", direccionService.listar(Sesion.idUsuario(session)));
        return "carrito/ver";
    }

    @PostMapping("/agregar")
    public String agregar(HttpSession session, @RequestParam("idZapatilla") int idZapatilla,
            @RequestParam("talla") String talla, @RequestParam(value = "cantidad", defaultValue = "1") int cantidad,
            Model model) {
        try {
            carritoService.agregarItem(Sesion.idUsuario(session), idZapatilla, talla, cantidad);
            return "redirect:/carrito?agregado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/zapatilla/" + idZapatilla + "?error=" + ex.getMessage();
        }
    }

    @PostMapping("/actualizar")
    public String actualizar(HttpSession session, @RequestParam("idZapatilla") int idZapatilla,
            @RequestParam("talla") String talla, @RequestParam("cantidad") int cantidad) {
        carritoService.actualizarCantidad(Sesion.idUsuario(session), idZapatilla, talla, cantidad);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminar(HttpSession session, @RequestParam("idZapatilla") int idZapatilla,
            @RequestParam("talla") String talla) {
        carritoService.eliminarItem(Sesion.idUsuario(session), idZapatilla, talla);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciar(HttpSession session) {
        carritoService.vaciarCarrito(Sesion.idUsuario(session));
        return "redirect:/carrito";
    }

}
