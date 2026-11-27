package com.nextfeet.nextfeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.model.Direccion;
import com.nextfeet.nextfeet.repository.DireccionDAO;

@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionDAO direccionDAO;

    public DireccionServiceImpl(DireccionDAO direccionDAO) {
        this.direccionDAO = direccionDAO;
    }

    @Override
    public List<Direccion> listar(int idUsuario) {
        return direccionDAO.listarPorUsuario(idUsuario);
    }

    // La dirección debe existir y pertenecer al cliente que la pide
    @Override
    public Direccion obtener(int idUsuario, int idDireccion) {
        Direccion d = direccionDAO.buscarPorId(idDireccion);
        if (d == null || d.getIdUsuario() != idUsuario) {
            throw new IllegalStateException("La dirección no pertenece a tu cuenta.");
        }
        return d;
    }

    // RN-04: la primera dirección del cliente queda como principal; las demás, como secundarias
    @Override
    public void agregar(int idUsuario, Direccion d) {
        validar(d);
        d.setIdUsuario(idUsuario);
        d.setPrincipal(direccionDAO.listarPorUsuario(idUsuario).isEmpty());
        direccionDAO.crear(d);
    }

    @Override
    public void actualizar(int idUsuario, Direccion d) {
        obtener(idUsuario, d.getId());
        validar(d);
        direccionDAO.actualizar(d);
    }

    private void validar(Direccion d) {
        if (vacio(d.getDepartamento()) || vacio(d.getProvincia()) || vacio(d.getDistrito()) || vacio(d.getCalle())) {
            throw new IllegalStateException("Completa departamento, provincia, distrito y calle.");
        }
        if (d.getDepartamento().length() > 60 || d.getProvincia().length() > 60 || d.getDistrito().length() > 60
                || d.getCalle().length() > 120) {
            throw new IllegalStateException("Alguno de los datos de la dirección es demasiado largo.");
        }
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }

    @Override
    public void hacerPrincipal(int idUsuario, int idDireccion) {
        obtener(idUsuario, idDireccion);
        direccionDAO.quitarPrincipal(idUsuario);
        direccionDAO.marcarPrincipal(idDireccion);
    }

}
