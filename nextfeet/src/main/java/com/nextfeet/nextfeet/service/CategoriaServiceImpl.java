package com.nextfeet.nextfeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.model.Categoria;
import com.nextfeet.nextfeet.repository.CategoriaDAO;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaServiceImpl(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaDAO.listarTodas();
    }

    @Override
    public Categoria obtener(int id) {
        Categoria c = categoriaDAO.buscarPorId(id);
        if (c == null) {
            throw new IllegalStateException("La categoría no existe.");
        }
        return c;
    }

    @Override
    public void crear(String nombre, String descripcion) {
        validarNombre(nombre);
        if (categoriaDAO.buscarPorNombre(nombre) != null) {
            throw new IllegalStateException("Ya existe una categoría con ese nombre.");
        }
        Categoria c = new Categoria();
        c.setNombre(nombre.trim());
        c.setDescripcion(descripcion);
        categoriaDAO.crear(c);
    }

    @Override
    public void actualizar(int id, String nombre, String descripcion) {
        Categoria c = obtener(id);
        validarNombre(nombre);
        Categoria existente = categoriaDAO.buscarPorNombre(nombre);
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalStateException("Ya existe una categoría con ese nombre.");
        }
        c.setNombre(nombre.trim());
        c.setDescripcion(descripcion);
        categoriaDAO.actualizar(c);
    }

    @Override
    public void desactivarOEliminar(int id) {
        Categoria c = obtener(id);
        long total = categoriaDAO.contarZapatillas(id);
        if (total == 0) {
            categoriaDAO.eliminar(id);
        } else {
            categoriaDAO.cambiarEstado(id, !c.isActivo());
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre de la categoría es obligatorio.");
        }
    }

}
