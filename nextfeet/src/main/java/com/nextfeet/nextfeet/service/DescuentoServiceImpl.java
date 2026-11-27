package com.nextfeet.nextfeet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nextfeet.nextfeet.model.Descuento;
import com.nextfeet.nextfeet.model.Zapatilla;
import com.nextfeet.nextfeet.repository.DescuentoDAO;

@Service
public class DescuentoServiceImpl implements DescuentoService {

    private final DescuentoDAO descuentoDAO;

    public DescuentoServiceImpl(DescuentoDAO descuentoDAO) {
        this.descuentoDAO = descuentoDAO;
    }

    @Override
    public List<Descuento> listar() {
        return descuentoDAO.listar();
    }

    @Override
    public Descuento obtener(int id) {
        Descuento d = descuentoDAO.buscarPorId(id);
        if (d == null) {
            throw new IllegalStateException("El descuento no existe.");
        }
        return d;
    }

    @Override
    public List<Zapatilla> listarZapatillas() {
        return descuentoDAO.listarZapatillas();
    }

    @Override
    public void registrar(String nombre, BigDecimal porcentaje, String fechaInicio, String fechaFin,
            List<Integer> idsZapatillas) {
        Descuento d = validar(null, nombre, porcentaje, fechaInicio, fechaFin);
        int id = descuentoDAO.crear(d);
        descuentoDAO.asignarZapatillas(id, idsZapatillas == null ? List.of() : idsZapatillas);
    }

    @Override
    public void actualizar(int id, String nombre, BigDecimal porcentaje, String fechaInicio, String fechaFin,
            boolean activo, List<Integer> idsZapatillas) {
        obtener(id);
        Descuento d = validar(id, nombre, porcentaje, fechaInicio, fechaFin);
        d.setActivo(activo);
        descuentoDAO.actualizar(d);
        descuentoDAO.asignarZapatillas(id, idsZapatillas == null ? List.of() : idsZapatillas);
    }

    // Valida los datos del formulario y devuelve el descuento listo para guardar
    private Descuento validar(Integer idActual, String nombre, BigDecimal porcentaje, String fechaInicio,
            String fechaFin) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre del descuento es obligatorio.");
        }
        Descuento existente = descuentoDAO.buscarPorNombre(nombre.trim());
        if (existente != null && !existente.getId().equals(idActual)) {
            throw new IllegalStateException("Ya existe un descuento con ese nombre.");
        }
        if (porcentaje == null || porcentaje.compareTo(BigDecimal.ZERO) <= 0
                || porcentaje.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalStateException("El porcentaje debe ser mayor a 0 y como máximo 100.");
        }
        LocalDate inicio;
        LocalDate fin;
        try {
            inicio = LocalDate.parse(fechaInicio);
            fin = LocalDate.parse(fechaFin);
        } catch (DateTimeParseException | NullPointerException ex) {
            throw new IllegalStateException("Ingresa la fecha de inicio y la fecha de fin.");
        }
        if (fin.isBefore(inicio)) {
            throw new IllegalStateException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        Descuento d = new Descuento();
        d.setId(idActual);
        d.setNombre(nombre.trim());
        d.setPorcentaje(porcentaje);
        d.setFechaInicio(inicio);
        d.setFechaFin(fin);
        return d;
    }

}
