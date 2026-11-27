package com.nextfeet.nextfeet.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.cart.CarritoService;
import com.nextfeet.nextfeet.model.Pedido;
import com.nextfeet.nextfeet.service.DireccionService;
import com.nextfeet.nextfeet.service.PedidoService;
import com.nextfeet.nextfeet.util.Sesion;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final PedidoService pedidoService;
    private final CarritoService carritoService;
    private final DireccionService direccionService;

    public CheckoutController(PedidoService pedidoService, CarritoService carritoService,
            DireccionService direccionService) {
        this.pedidoService = pedidoService;
        this.carritoService = carritoService;
        this.direccionService = direccionService;
    }

    @PostMapping("/pagar")
    public String pagar(HttpSession session, @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono, Model model) {
        try {
            Pedido pedido = pedidoService.confirmarCompra(Sesion.idUsuario(session), direccion, telefono);
            return "redirect:/checkout/comprobante/" + pedido.getId();
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("direccion", direccion);
            model.addAttribute("telefono", telefono);
            model.addAttribute("carrito", carritoService.verCarrito(Sesion.idUsuario(session)));
            model.addAttribute("direcciones", direccionService.listar(Sesion.idUsuario(session)));
            return "carrito/ver";
        }
    }

    @GetMapping("/comprobante/{idPedido}")
    public String comprobante(HttpSession session, @PathVariable int idPedido, Model model) {
        model.addAttribute("pedido", pedidoService.detalleCliente(Sesion.idUsuario(session), idPedido));
        return "checkout/comprobante";
    }

}
