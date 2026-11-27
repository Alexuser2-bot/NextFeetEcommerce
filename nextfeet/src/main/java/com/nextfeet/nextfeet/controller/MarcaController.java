package com.nextfeet.nextfeet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.model.Marca;
import com.nextfeet.nextfeet.service.MarcaService;

// Interfaces 23, 24 y 25: listado, registro y edición de marcas (solo administrador)
@Controller
@RequestMapping("/admin/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public String listar(@RequestParam(value = "q", required = false) String q, Model model) {
        model.addAttribute("marcas", marcaService.listar(q));
        model.addAttribute("q", q);
        return "admin/marcas/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("marca", new Marca());
        return "admin/marcas/formulario";
    }

    @PostMapping("/nueva")
    public String registrar(@RequestParam("nombre") String nombre, Model model) {
        try {
            marcaService.registrar(nombre);
            return "redirect:/admin/marcas";
        } catch (IllegalStateException ex) {
            Marca m = new Marca();
            m.setNombre(nombre);
            model.addAttribute("marca", m);
            model.addAttribute("error", ex.getMessage());
            return "admin/marcas/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("marca", marcaService.obtener(id));
        return "admin/marcas/formulario";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre, Model model) {
        try {
            marcaService.actualizar(id, nombre);
            return "redirect:/admin/marcas";
        } catch (IllegalStateException ex) {
            Marca m = new Marca();
            m.setId(id);
            m.setNombre(nombre);
            model.addAttribute("marca", m);
            model.addAttribute("error", ex.getMessage());
            return "admin/marcas/formulario";
        }
    }

}
