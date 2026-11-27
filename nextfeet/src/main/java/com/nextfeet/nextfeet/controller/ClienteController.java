package com.nextfeet.nextfeet.controller;

import java.math.BigDecimal;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nextfeet.nextfeet.dto.ContactoDTO;
import com.nextfeet.nextfeet.dto.FiltroCatalogo;
import com.nextfeet.nextfeet.service.CategoriaService;
import com.nextfeet.nextfeet.service.PedidoService;
import com.nextfeet.nextfeet.service.UsuarioService;
import com.nextfeet.nextfeet.service.ZapatillaService;
import com.nextfeet.nextfeet.util.Sesion;

@Controller
public class ClienteController {

    private final ZapatillaService zapatillaService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    public ClienteController(ZapatillaService zapatillaService,
            CategoriaService categoriaService,
            UsuarioService usuarioService,
            PedidoService pedidoService) {
        this.zapatillaService = zapatillaService;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    // ==================== Páginas públicas (inicio, nosotros, contacto) ====================

    @GetMapping("/")
    public String home(Model model) {
        return "pagina/home";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "pagina/nosotros";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("contacto", new ContactoDTO());
        return "pagina/contacto";
    }

    @PostMapping("/contacto")
    public String enviarContacto(@ModelAttribute("contacto") ContactoDTO dto, Model model) {
        model.addAttribute("enviado", true);
        model.addAttribute("contacto", new ContactoDTO());
        return "pagina/contacto";
    }

    // ==================== Catálogo y ficha técnica ====================

    @GetMapping("/catalogo")
    public String catalogo(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "categoria", required = false) Integer categoriaId,
            @RequestParam(value = "marca", required = false) String marca,
            @RequestParam(value = "talla", required = false) String talla,
            @RequestParam(value = "precioMin", required = false) BigDecimal precioMin,
            @RequestParam(value = "precioMax", required = false) BigDecimal precioMax,
            @RequestParam(value = "pagina", defaultValue = "1") int pagina,
            Model model) {

        FiltroCatalogo filtro = new FiltroCatalogo();
        filtro.setQ(q);
        filtro.setCategoriaId(categoriaId);
        filtro.setMarca(marca);
        filtro.setTalla(talla);
        filtro.setPrecioMin(precioMin);
        filtro.setPrecioMax(precioMax);
        filtro.setPagina(pagina);

        model.addAttribute("resultado", zapatillaService.verCatalogo(filtro));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", zapatillaService.listarMarcas());
        model.addAttribute("filtro", filtro);
        return "catalogo/lista";
    }

    @GetMapping("/zapatilla/{id}")
    public String ficha(@PathVariable int id, Model model) {
        model.addAttribute("zapatilla", zapatillaService.verFichaTecnica(id));
        return "catalogo/detalle";
    }

    // ==================== Perfil del cliente ====================

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        return perfil(session, model);
    }

    // Carga los datos que necesita la vista del perfil (usuario y sus direcciones)
    private String perfil(HttpSession session, Model model) {
        int idUsuario = Sesion.idUsuario(session);
        model.addAttribute("usuario", usuarioService.obtenerPerfil(idUsuario));
        return "perfil/ver";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(HttpSession session,
            @RequestParam("nombres") String nombres, @RequestParam("apellidos") String apellidos,
            @RequestParam("telefono") String telefono,
            @RequestParam(value = "nuevaPassword", required = false) String nuevaPassword,
            Model model) {
        try {
            usuarioService.actualizarPerfil(Sesion.idUsuario(session), nombres, apellidos, telefono, nuevaPassword);
            return "redirect:/perfil?actualizado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return perfil(session, model);
        }
    }

    @PostMapping("/perfil/desactivar")
    public String desactivarCuenta(HttpSession session, @RequestParam("passwordActual") String passwordActual,
            Model model) {
        try {
            usuarioService.desactivarCuentaPropia(Sesion.idUsuario(session), passwordActual);
            session.invalidate();
            return "redirect:/login?desactivado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return perfil(session, model);
        }
    }

    // ==================== Pedidos del cliente ====================

    @GetMapping("/mis-pedidos")
    public String historialPedidos(HttpSession session, Model model) {
        model.addAttribute("pedidos", pedidoService.historialCliente(Sesion.idUsuario(session)));
        return "pedido/historial";
    }

    @GetMapping("/mis-pedidos/{id}")
    public String detallePedido(HttpSession session, @PathVariable int id, Model model) {
        model.addAttribute("pedido", pedidoService.detalleCliente(Sesion.idUsuario(session), id));
        return "pedido/detalle";
    }

    @PostMapping("/mis-pedidos/{id}/anular")
    public String anularPedido(HttpSession session, @PathVariable int id,
            @RequestParam("motivo") String motivo, Model model) {
        try {
            pedidoService.anularPorCliente(Sesion.idUsuario(session), id, motivo);
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("pedido", pedidoService.detalleCliente(Sesion.idUsuario(session), id));
            return "pedido/detalle";
        }
        return "redirect:/mis-pedidos/" + id;
    }

    @GetMapping("/mis-pedidos/{id}/seguimiento")
    public String seguimientoPedido(HttpSession session, @PathVariable int id, Model model) {
        model.addAttribute("pedido", pedidoService.seguimiento(Sesion.idUsuario(session), id));
        return "pedido/seguimiento";
    }

}
