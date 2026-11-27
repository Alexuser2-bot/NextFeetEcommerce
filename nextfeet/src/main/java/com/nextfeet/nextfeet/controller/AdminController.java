package com.nextfeet.nextfeet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nextfeet.nextfeet.dto.RegistroDTO;
import com.nextfeet.nextfeet.model.Categoria;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.model.ZapatillaTalla;
import com.nextfeet.nextfeet.service.CategoriaService;
import com.nextfeet.nextfeet.service.MarcaService;
import com.nextfeet.nextfeet.service.PedidoService;
import com.nextfeet.nextfeet.service.UsuarioService;
import com.nextfeet.nextfeet.service.ZapatillaService;
import com.nextfeet.nextfeet.util.Sesion;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;
    private final ZapatillaService zapatillaService;
    private final PedidoService pedidoService;
    private final MarcaService marcaService;

    public AdminController(UsuarioService usuarioService,
            CategoriaService categoriaService,
            ZapatillaService zapatillaService,
            PedidoService pedidoService,
            MarcaService marcaService) {
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.zapatillaService = zapatillaService;
        this.pedidoService = pedidoService;
        this.marcaService = marcaService;
    }

    // ==================== Panel, administradores y clientes ====================

    @GetMapping
    public String panel(@RequestParam(value = "periodo", required = false) String periodo, Model model) {
        if (periodo == null || !(periodo.equals("dia") || periodo.equals("semana")
                || periodo.equals("mes") || periodo.equals("anio"))) {
            periodo = "mes";
        }
        List<Map<String, Object>> ventas = pedidoService.ventasPorPeriodo(periodo);
        int salto = Math.max(1, (int) Math.ceil(ventas.size() / 10.0));
        model.addAttribute("ventas", ventas);
        model.addAttribute("periodo", periodo);
        model.addAttribute("salto", salto);
        model.addAttribute("totalVentas", pedidoService.totalVentas());
        model.addAttribute("totalPedidos", pedidoService.totalPedidosVendidos());
        return "admin/panel";
    }

    @GetMapping("/administradores")
    public String listarAdmins(Model model) {
        model.addAttribute("administradores", usuarioService.listarAdministradores());
        return "admin/administradores/lista";
    }

    @GetMapping("/administradores/crear")
    public String formularioCrearAdmin(Model model) {
        model.addAttribute("registro", new RegistroDTO());
        return "admin/administradores/crear";
    }

    @PostMapping("/administradores/crear")
    public String crearAdmin(@ModelAttribute("registro") RegistroDTO dto, Model model) {
        try {
            usuarioService.registrarAdministrador(dto);
            return "redirect:/admin/administradores";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "admin/administradores/crear";
        }
    }

    @PostMapping("/administradores/{id}/desactivar")
    public String desactivarAdmin(@PathVariable int id, HttpSession session, Model model) {
        try {
            usuarioService.desactivarAdministrador(id, Sesion.idUsuario(session));
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/administradores";
    }

    @GetMapping("/clientes")
    public String listarClientes(@RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "activo", required = false) Boolean activo,
            @RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        model.addAttribute("resultado", usuarioService.listarClientes(q, activo, pagina));
        model.addAttribute("q", q);
        model.addAttribute("activo", activo);
        return "admin/clientes/lista";
    }

    @GetMapping("/clientes/{id}/editar")
    public String formularioEditarCliente(@PathVariable int id, Model model) {
        model.addAttribute("cliente", usuarioService.obtenerPerfil(id));
        return "admin/clientes/editar";
    }

    @PostMapping("/clientes/{id}/editar")
    public String editarCliente(@PathVariable int id, @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos, @RequestParam("telefono") String telefono, Model model) {
        try {
            usuarioService.actualizarCliente(id, nombres, apellidos, telefono);
            return "redirect:/admin/clientes";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("cliente", usuarioService.obtenerPerfil(id));
            return "admin/clientes/editar";
        }
    }

    @PostMapping("/clientes/{id}/desactivar")
    public String desactivarCliente(@PathVariable int id) {
        usuarioService.desactivarCliente(id);
        return "redirect:/admin/clientes";
    }

    // ==================== Perfil del administrador ====================

    @GetMapping("/perfil")
    public String verPerfilAdmin(HttpSession session, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPerfil(Sesion.idUsuario(session)));
        return "admin/perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfilAdmin(HttpSession session, @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos, @RequestParam("telefono") String telefono,
            @RequestParam(value = "nuevaPassword", required = false) String nuevaPassword, Model model) {
        try {
            usuarioService.actualizarPerfil(Sesion.idUsuario(session), nombres, apellidos, telefono, nuevaPassword);
            return "redirect:/admin/perfil?actualizado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("usuario", usuarioService.obtenerPerfil(Sesion.idUsuario(session)));
            return "admin/perfil";
        }
    }

    @PostMapping("/perfil/desactivar")
    public String desactivarPerfilAdmin(HttpSession session, @RequestParam("passwordActual") String passwordActual,
            Model model) {
        try {
            usuarioService.desactivarCuentaPropia(Sesion.idUsuario(session), passwordActual);
            session.invalidate();
            return "redirect:/login?desactivado";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("usuario", usuarioService.obtenerPerfil(Sesion.idUsuario(session)));
            return "admin/perfil";
        }
    }

    // ==================== Categorías ====================

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "admin/categorias/lista";
    }

    @GetMapping("/categorias/crear")
    public String formularioCrearCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/crear";
    }

    @PostMapping("/categorias/crear")
    public String crearCategoria(@RequestParam("nombre") String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion, Model model) {
        try {
            categoriaService.crear(nombre, descripcion);
            return "redirect:/admin/categorias";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "admin/categorias/crear";
        }
    }

    @GetMapping("/categorias/{id}/editar")
    public String formularioEditarCategoria(@PathVariable int id, Model model) {
        model.addAttribute("categoria", categoriaService.obtener(id));
        return "admin/categorias/editar";
    }

    @PostMapping("/categorias/{id}/editar")
    public String editarCategoria(@PathVariable int id, @RequestParam("nombre") String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion, Model model) {
        try {
            categoriaService.actualizar(id, nombre, descripcion);
            return "redirect:/admin/categorias";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categoria", categoriaService.obtener(id));
            return "admin/categorias/editar";
        }
    }

    @PostMapping("/categorias/{id}/eliminar")
    public String desactivarOEliminarCategoria(@PathVariable int id) {
        categoriaService.desactivarOEliminar(id);
        return "redirect:/admin/categorias";
    }

    // ==================== Zapatillas ====================

    @GetMapping("/zapatillas")
    public String listarZapatillas(@RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        model.addAttribute("resultado", zapatillaService.listarInventario(q, pagina));
        model.addAttribute("q", q);
        return "admin/zapatillas/lista";
    }

    @GetMapping("/zapatillas/nueva")
    public String formularioCrearZapatilla(Model model) {
        model.addAttribute("zapatilla", new Zapatilla());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listar(null));
        return "admin/zapatillas/crear";
    }

    @PostMapping("/zapatillas/nueva")
    public String crearZapatilla(@RequestParam("nombre") String nombre, @RequestParam("marca") String marca,
            @RequestParam("idCategoria") int idCategoria, @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "materiales", required = false) String materiales,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam("tallas") List<String> tallas, @RequestParam("stocks") List<Integer> stocks,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl, Model model) {
        try {
            Zapatilla z = new Zapatilla();
            z.setNombre(nombre);
            z.setMarca(marca);
            z.setIdCategoria(idCategoria);
            z.setPrecio(precio);
            z.setDescripcion(descripcion);
            z.setMateriales(materiales);
            z.setColor(color);
            z.setImagenUrl(limpiarUrl(imagenUrl));
            z.setTallas(construirTallas(tallas, stocks));
            zapatillaService.registrar(z);
            return "redirect:/admin/zapatillas";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("marcas", marcaService.listar(null));
            return "admin/zapatillas/crear";
        }
    }

    @GetMapping("/zapatillas/{id}")
    public String gestionarZapatilla(@PathVariable int id, Model model) {
        model.addAttribute("zapatilla", zapatillaService.verFichaTecnica(id));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listar(null));
        model.addAttribute("historial", zapatillaService.historialStock(id));
        return "admin/zapatillas/detalle";
    }

    @PostMapping("/zapatillas/{id}/actualizar")
    public String actualizarZapatilla(@PathVariable int id, @RequestParam("nombre") String nombre,
            @RequestParam("marca") String marca, @RequestParam("idCategoria") int idCategoria,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "materiales", required = false) String materiales,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam("tallas") List<String> tallas, @RequestParam("stocks") List<Integer> stocks,
            @RequestParam(value = "imagenUrl", required = false) String imagenUrl, Model model) {
        try {
            Zapatilla actual = zapatillaService.verFichaTecnica(id);
            Zapatilla z = new Zapatilla();
            z.setId(id);
            z.setNombre(nombre);
            z.setMarca(marca);
            z.setIdCategoria(idCategoria);
            z.setPrecio(precio);
            z.setDescripcion(descripcion);
            z.setMateriales(materiales);
            z.setColor(color);
            String nuevaImagen = limpiarUrl(imagenUrl);
            z.setImagenUrl(nuevaImagen != null ? nuevaImagen : actual.getImagenUrl());
            z.setTallas(construirTallas(tallas, stocks));
            zapatillaService.actualizar(z);
            return "redirect:/admin/zapatillas/" + id;
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("zapatilla", zapatillaService.verFichaTecnica(id));
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("marcas", marcaService.listar(null));
            model.addAttribute("historial", zapatillaService.historialStock(id));
            return "admin/zapatillas/detalle";
        }
    }

    @PostMapping("/zapatillas/{id}/estado")
    public String cambiarEstadoZapatilla(@PathVariable int id, @RequestParam("activo") boolean activo) {
        zapatillaService.cambiarVisibilidad(id, activo);
        return "redirect:/admin/zapatillas/" + id;
    }

    @PostMapping("/zapatillas/{id}/eliminar")
    public String eliminarZapatilla(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            zapatillaService.eliminar(id);
            return "redirect:/admin/zapatillas";
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/admin/zapatillas/" + id;
        }
    }

    private List<ZapatillaTalla> construirTallas(List<String> tallas, List<Integer> stocks) {
        List<ZapatillaTalla> lista = new ArrayList<>();
        for (int i = 0; i < tallas.size(); i++) {
            if (tallas.get(i) == null || tallas.get(i).isBlank()) {
                continue;
            }
            ZapatillaTalla t = new ZapatillaTalla();
            t.setTalla(tallas.get(i).trim());
            t.setStock(i < stocks.size() && stocks.get(i) != null ? stocks.get(i) : 0);
            lista.add(t);
        }
        return lista;
    }

    private String limpiarUrl(String imagenUrl) {
        if (imagenUrl == null || imagenUrl.isBlank()) {
            return null;
        }
        return imagenUrl.trim();
    }

    // ==================== Pedidos ====================

    @GetMapping("/pedidos")
    public String listarPedidos(@RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        model.addAttribute("resultado", pedidoService.listarGlobal(estado, q, pagina));
        model.addAttribute("estado", estado);
        model.addAttribute("q", q);
        return "admin/pedidos/lista";
    }

    @GetMapping("/pedidos/{id}")
    public String detallePedido(@PathVariable int id, Model model) {
        model.addAttribute("pedido", pedidoService.detalleAdmin(id));
        return "admin/pedidos/detalle";
    }

    @PostMapping("/pedidos/{id}/estado")
    public String actualizarEstadoPedido(@PathVariable int id, @RequestParam("estado") String estado, Model model) {
        try {
            pedidoService.actualizarEstado(id, estado);
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/pedidos/" + id;
    }

    @PostMapping("/pedidos/{id}/anular")
    public String anularPedido(@PathVariable int id, @RequestParam("motivo") String motivo, Model model) {
        try {
            pedidoService.anular(id, motivo);
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/pedidos/" + id;
    }

}
