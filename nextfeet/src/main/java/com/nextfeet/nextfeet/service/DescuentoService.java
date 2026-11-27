package com.nextfeet.nextfeet.service;

import java.math.BigDecimal;
import java.util.List;

import com.nextfeet.nextfeet.model.Descuento;
import com.nextfeet.nextfeet.model.Zapatilla;

public interface DescuentoService {

    List<Descuento> listar();

    Descuento obtener(int id);

    List<Zapatilla> listarZapatillas();

    void registrar(String nombre, BigDecimal porcentaje, String fechaInicio, String fechaFin,
            List<Integer> idsZapatillas);

    void actualizar(int id, String nombre, BigDecimal porcentaje, String fechaInicio, String fechaFin,
            boolean activo, List<Integer> idsZapatillas);

}
