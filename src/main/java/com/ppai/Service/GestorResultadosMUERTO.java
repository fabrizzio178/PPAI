package com.ppai.Service;

import com.ppai.Model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorResultadosMUERTO {
    private List<OrdenDeInspeccion> ordenes;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public GestorResultadosMUERTO() {
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    private void cargarOrdenesHardCodeadas() {
        // Parseo de fechas como LocalDateTime para coincidir con el constructor de EstacionSismologica
        LocalDateTime fechaEstacion1 = LocalDateTime.parse("2024-03-15 00:00", formatter);
        LocalDateTime fechaEstacion2 = LocalDateTime.parse("2024-04-10 00:00", formatter);

        EstacionSismologica estacion1 = new EstacionSismologica(
                "SISMO01",
                "DOC123",
                fechaEstacion1,
                -31,
                -64,
                "Estación Norte",
                1001
        );

        EstacionSismologica estacion2 = new EstacionSismologica(
                "SISMO02",
                "DOC456",
                fechaEstacion2,
                -32,
                -63,
                "Estación Sur",
                1002
        );

        Rol rol = new Rol("Acceso completo", "Administrador");
        Empleado empleadoDummy = new Empleado("12345678", "Pipo", "Pipo123@gmail.com", "351392212", rol);
        Estado estadoInicial = new Estado("OrdenInspeccion", "OICerrada");

        ordenes.add(new OrdenDeInspeccion(
                101,
                LocalDateTime.parse("2025-05-01 10:00", formatter),
                null,
                LocalDateTime.parse("2025-04-30 09:00", formatter),
                null,
                estacion1,
                estadoInicial,
                empleadoDummy
        ));

        ordenes.add(new OrdenDeInspeccion(
                102,
                LocalDateTime.parse("2025-05-01 12:00", formatter),
                null,
                LocalDateTime.parse("2025-04-30 11:00", formatter),
                null,
                estacion2,
                estadoInicial,
                empleadoDummy
        ));
    }

    public void mostrarOrdenesDisponibles() {
        for (OrdenDeInspeccion orden : ordenes) {
            System.out.println(
                    "Número: " + orden.getNumeroOrden()
                            + " | Estación: " + orden.getEstacionSismologica().getNombre()
                            + " | Finalización: " + orden.getFechaHoraFinalizacion()
            );
        }
    }

    public OrdenDeInspeccion seleccionarOrdenPorNumero(int numero) {
        for (OrdenDeInspeccion orden : ordenes) {
            if (orden.getNumeroOrden() == numero) return orden;
        }
        return null;
    }

    public void ingresarObservacion(OrdenDeInspeccion orden, String observacion) {
        orden.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }

    public void confirmarCierre(OrdenDeInspeccion orden) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        orden.setFechaHoraCierre(fechaHoraActual);
        System.out.println("Orden cerrada con éxito. Fecha de cierre: " + fechaHoraActual.format(formatter));
    }
}
