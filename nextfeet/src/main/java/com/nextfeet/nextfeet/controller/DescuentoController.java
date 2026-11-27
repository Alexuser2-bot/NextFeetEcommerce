package com.nextfeet.nextfeet.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.model.Descuento;
import com.nextfeet.nextfeet.service.DescuentoService;

// Interfaces 26, 27 y 28: listado, registro y edición de descuentos (solo administrador)
@Controller
@RequestMapping("/admin/descuentos")
public class DescuentoController {

    private final DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("descuentos", descuentoService.listar());
        return "admin/descuentos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("descuento", new Descuento());
        model.addAttribute("zapatillas", descuentoService.listarZapatillas());
        return "admin/descuentos/formulario";
    }

    @PostMapping("/nuevo")
    public String registrar(@RequestParam("nombre") String nombre, @RequestParam("porcentaje") BigDecimal porcentaje,
            @RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin,
            @RequestParam(value = "idsZapatillas", required = false) List<Integer> idsZapatillas, Model model) {
        try {
            descuentoService.registrar(nombre, porcentaje, fechaInicio, fechaFin, idsZapatillas);
            return "redirect:/admin/descuentos";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("descuento", new Descuento());
            model.addAttribute("zapatillas", descuentoService.listarZapatillas());
            return "admin/descuentos/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("descuento", descuentoService.obtener(id));
        model.addAttribute("zapatillas", descuentoService.listarZapatillas());
        return "admin/descuentos/formulario";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre,
            @RequestParam("porcentaje") BigDecimal porcentaje, @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin, @RequestParam("activo") boolean activo,
            @RequestParam(value = "idsZapatillas", required = false) List<Integer> idsZapatillas, Model model) {
        try {
            descuentoService.actualizar(id, nombre, porcentaje, fechaInicio, fechaFin, activo, idsZapatillas);
            return "redirect:/admin/descuentos";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("descuento", descuentoService.obtener(id));
            model.addAttribute("zapatillas", descuentoService.listarZapatillas());
            return "admin/descuentos/formulario";
        }
    }

}
