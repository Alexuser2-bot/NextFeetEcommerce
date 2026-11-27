package com.nextfeet.nextfeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.model.Marca;
import com.nextfeet.nextfeet.repository.MarcaDAO;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaDAO marcaDAO;

    public MarcaServiceImpl(MarcaDAO marcaDAO) {
        this.marcaDAO = marcaDAO;
    }

    @Override
    public List<Marca> listar(String busqueda) {
        return marcaDAO.listar(busqueda);
    }

    @Override
    public Marca obtener(int id) {
        Marca m = marcaDAO.buscarPorId(id);
        if (m == null) {
            throw new IllegalStateException("La marca no existe.");
        }
        return m;
    }

    @Override
    public void registrar(String nombre) {
        validarNombre(nombre, null);
        marcaDAO.crear(nombre.trim());
    }

    @Override
    public void actualizar(int id, String nombre) {
        Marca actual = obtener(id);
        validarNombre(nombre, id);
        marcaDAO.actualizar(id, nombre.trim());
        marcaDAO.renombrarEnZapatillas(actual.getNombre(), nombre.trim());
    }

    // El nombre es obligatorio y no puede repetirse (idActual evita chocar consigo misma al editar)
    private void validarNombre(String nombre, Integer idActual) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre de la marca es obligatorio.");
        }
        Marca existente = marcaDAO.buscarPorNombre(nombre.trim());
        if (existente != null && !existente.getId().equals(idActual)) {
            throw new IllegalStateException("Ya existe una marca con ese nombre.");
        }
    }

}
