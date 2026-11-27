package com.nextfeet.nextfeet.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.dto.RegistroDTO;
import com.nextfeet.nextfeet.model.Usuario;
import com.nextfeet.nextfeet.repository.UsuarioDAO;
import com.nextfeet.nextfeet.util.Pagina;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONO = Pattern.compile("^\\d{9}$");
    private static final int TAMANO_PAGINA = 10;

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario iniciarSesion(String correo, String password) {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            throw new IllegalStateException("Ingresa tu correo y contraseña.");
        }
        Usuario u = usuarioDAO.buscarPorCorreo(correo.trim());
        if (u == null || !passwordEncoder.matches(password, u.getPassword())) {
            throw new IllegalStateException("Correo o contraseña incorrectos.");
        }
        if (!u.isActivo()) {
            throw new IllegalStateException("Tu cuenta se encuentra cancelada.");
        }
        u.setPassword(null);
        return u;
    }

    @Override
    public void registrarCliente(RegistroDTO dto) {
        crearUsuario(dto, "CLIENTE");
    }

    @Override
    public Usuario obtenerPerfil(int idUsuario) {
        Usuario u = usuarioDAO.buscarPorId(idUsuario);
        if (u == null) {
            throw new IllegalStateException("El usuario no existe.");
        }
        u.setPassword(null);
        return u;
    }

    @Override
    public void actualizarPerfil(int idUsuario, String nombres, String apellidos, String telefono,
            String nuevaPassword) {
        Usuario u = obtenerPerfil(idUsuario);
        validarDatosPersonales(nombres, apellidos, telefono);
        u.setNombres(nombres.trim());
        u.setApellidos(apellidos.trim());
        u.setTelefono(telefono.trim());
        usuarioDAO.actualizarDatos(u);
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            validarPassword(nuevaPassword);
            usuarioDAO.actualizarPassword(idUsuario, passwordEncoder.encode(nuevaPassword));
        }
    }

    @Override
    public void desactivarCuentaPropia(int idUsuario, String passwordActual) {
        Usuario u = usuarioDAO.buscarPorId(idUsuario);
        if (u == null || passwordActual == null || !passwordEncoder.matches(passwordActual, u.getPassword())) {
            throw new IllegalStateException("La contraseña actual no es correcta.");
        }
        usuarioDAO.cambiarEstado(idUsuario, false);
    }

    @Override
    public List<Usuario> listarAdministradores() {
        List<Usuario> lista = usuarioDAO.listarPorRol("ADMIN");
        lista.forEach(a -> a.setPassword(null));
        return lista;
    }

    @Override
    public void registrarAdministrador(RegistroDTO dto) {
        crearUsuario(dto, "ADMIN");
    }

    @Override
    public void desactivarAdministrador(int idObjetivo, int idAdminEnSesion) {
        Usuario objetivo = usuarioDAO.buscarPorId(idObjetivo);
        if (objetivo == null || !objetivo.isAdmin()) {
            throw new IllegalStateException("El administrador no existe.");
        }
        if (idObjetivo == idAdminEnSesion) {
            throw new IllegalStateException("No puedes desactivar tu propia cuenta de administrador.");
        }
        if (usuarioDAO.contarActivosPorRol("ADMIN") <= 1) {
            throw new IllegalStateException("Debe existir al menos un administrador activo.");
        }
        usuarioDAO.cambiarEstado(idObjetivo, false);
    }

    @Override
    public Pagina<Usuario> listarClientes(String busqueda, Boolean activo, int pagina) {
        Pagina<Usuario> p = usuarioDAO.listarPorRolPaginado("CLIENTE", busqueda, activo, Math.max(pagina, 1),
                TAMANO_PAGINA);
        p.getContenido().forEach(c -> c.setPassword(null));
        return p;
    }

    @Override
    public void actualizarCliente(int idCliente, String nombres, String apellidos, String telefono) {
        Usuario c = obtenerPerfil(idCliente);
        if (c.isAdmin()) {
            throw new IllegalStateException("El usuario seleccionado no es un cliente.");
        }
        actualizarPerfil(idCliente, nombres, apellidos, telefono, null);
    }

    @Override
    public void desactivarCliente(int idCliente) {
        Usuario c = obtenerPerfil(idCliente);
        if (c.isAdmin()) {
            throw new IllegalStateException("El usuario seleccionado no es un cliente.");
        }
        usuarioDAO.cambiarEstado(idCliente, false);
    }

    private void crearUsuario(RegistroDTO dto, String rol) {
        validarDatosPersonales(dto.getNombres(), dto.getApellidos(), dto.getTelefono());
        String correo = dto.getCorreo() == null ? "" : dto.getCorreo().trim().toLowerCase();
        if (!EMAIL.matcher(correo).matches()) {
            throw new IllegalStateException("El correo electrónico no tiene un formato válido.");
        }
        validarPassword(dto.getPassword());
        if (!dto.getPassword().equals(dto.getConfirmarPassword())) {
            throw new IllegalStateException("Las contraseñas no coinciden.");
        }
        if (usuarioDAO.buscarPorCorreo(correo) != null) {
            throw new IllegalStateException("Ya existe una cuenta registrada con ese correo.");
        }
        Usuario u = new Usuario();
        u.setNombres(dto.getNombres().trim());
        u.setApellidos(dto.getApellidos().trim());
        u.setCorreo(correo);
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setTelefono(dto.getTelefono().trim());
        u.setRol(rol);
        u.setActivo(true);
        usuarioDAO.crear(u);
    }

    private void validarDatosPersonales(String nombres, String apellidos, String telefono) {
        if (nombres == null || nombres.isBlank() || apellidos == null || apellidos.isBlank()) {
            throw new IllegalStateException("Los nombres y apellidos son obligatorios.");
        }
        if (telefono == null || !TELEFONO.matcher(telefono.trim()).matches()) {
            throw new IllegalStateException("El teléfono debe tener 9 dígitos.");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalStateException("La contraseña debe tener al menos 8 caracteres.");
        }
    }

}
