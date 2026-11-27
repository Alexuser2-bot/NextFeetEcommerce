package com.nextfeet.nextfeet.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Agrupa las ventas registradas por día, semana, mes o año para el gráfico del panel.
public class VentasPorPeriodo {

    private static final String[] MESES = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    public static List<Map<String, Object>> agrupar(List<Map<String, Object>> filas, String periodo) {
        Map<String, BigDecimal> totales = new HashMap<>();
        Map<String, Integer> pedidos = new HashMap<>();
        LocalDate primera = null;
        LocalDate ultima = null;

        for (Map<String, Object> fila : filas) {
            LocalDate fecha = aFecha(fila.get("fecha"));
            BigDecimal total = new BigDecimal(String.valueOf(fila.get("total")));
            String clave = clave(fecha, periodo);
            if (!totales.containsKey(clave)) {
                totales.put(clave, BigDecimal.ZERO);
                pedidos.put(clave, 0);
            }
            totales.put(clave, totales.get(clave).add(total));
            pedidos.put(clave, pedidos.get(clave) + 1);
            if (primera == null || fecha.isBefore(primera)) {
                primera = fecha;
            }
            if (ultima == null || fecha.isAfter(ultima)) {
                ultima = fecha;
            }
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        if (ultima == null) {
            return resultado;
        }

        // Se recorren los últimos periodos hasta la venta más reciente, incluyendo los que no tuvieron ventas
        LocalDate inicioPrimera = inicioPeriodo(primera, periodo);
        LocalDate actual = inicioPeriodo(ultima, periodo);
        for (int i = 0; i < limite(periodo) && !actual.isBefore(inicioPrimera); i++) {
            String clave = clave(actual, periodo);
            Map<String, Object> punto = new HashMap<>();
            punto.put("etiqueta", etiqueta(actual, periodo));
            punto.put("total", totales.containsKey(clave) ? totales.get(clave) : BigDecimal.ZERO);
            punto.put("pedidos", pedidos.containsKey(clave) ? pedidos.get(clave) : 0);
            resultado.add(0, punto);
            actual = anterior(actual, periodo);
        }
        return resultado;
    }

    private static LocalDate inicioPeriodo(LocalDate fecha, String periodo) {
        if ("dia".equals(periodo)) {
            return fecha;
        }
        if ("semana".equals(periodo)) {
            return fecha.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        }
        if ("anio".equals(periodo)) {
            return fecha.withDayOfYear(1);
        }
        return fecha.withDayOfMonth(1);
    }

    private static LocalDate anterior(LocalDate inicio, String periodo) {
        if ("dia".equals(periodo)) {
            return inicio.minusDays(1);
        }
        if ("semana".equals(periodo)) {
            return inicio.minusWeeks(1);
        }
        if ("anio".equals(periodo)) {
            return inicio.minusYears(1);
        }
        return inicio.minusMonths(1);
    }

    private static String clave(LocalDate fecha, String periodo) {
        if ("dia".equals(periodo)) {
            return fecha.toString();
        }
        if ("semana".equals(periodo)) {
            int semana = fecha.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            return fecha.get(IsoFields.WEEK_BASED_YEAR) + "-" + (semana < 10 ? "0" : "") + semana;
        }
        if ("anio".equals(periodo)) {
            return String.valueOf(fecha.getYear());
        }
        return fecha.getYear() + "-" + (fecha.getMonthValue() < 10 ? "0" : "") + fecha.getMonthValue();
    }

    private static String etiqueta(LocalDate fecha, String periodo) {
        if ("dia".equals(periodo)) {
            return (fecha.getDayOfMonth() < 10 ? "0" : "") + fecha.getDayOfMonth() + " " + MESES[fecha.getMonthValue() - 1];
        }
        if ("semana".equals(periodo)) {
            return "Sem " + fecha.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + " - " + fecha.get(IsoFields.WEEK_BASED_YEAR);
        }
        if ("anio".equals(periodo)) {
            return String.valueOf(fecha.getYear());
        }
        return MESES[fecha.getMonthValue() - 1] + " " + fecha.getYear();
    }

    private static int limite(String periodo) {
        if ("dia".equals(periodo)) {
            return 30;
        }
        if ("semana".equals(periodo)) {
            return 12;
        }
        if ("anio".equals(periodo)) {
            return 5;
        }
        return 12;
    }

    private static LocalDate aFecha(Object valor) {
        if (valor instanceof Timestamp) {
            return ((Timestamp) valor).toLocalDateTime().toLocalDate();
        }
        if (valor instanceof LocalDateTime) {
            return ((LocalDateTime) valor).toLocalDate();
        }
        if (valor instanceof java.sql.Date) {
            return ((java.sql.Date) valor).toLocalDate();
        }
        if (valor instanceof LocalDate) {
            return (LocalDate) valor;
        }
        return LocalDate.parse(String.valueOf(valor).substring(0, 10));
    }

}
