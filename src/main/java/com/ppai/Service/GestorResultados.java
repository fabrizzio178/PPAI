package com.ppai.Service;

import com.ppai.Model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorResultados {
    private List<OrdenDeInspeccion> ordenes;
    private List<MotivoFueraServicio> motivosSeleccionados = new ArrayList<>();
    private ArrayList<Sismografo> sismografos = new ArrayList<>();
    private OrdenDeInspeccion ordenSeleccionada;

    public GestorResultados() {
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    public List<OrdenDeInspeccion> getListaOrdenes() {
        return ordenes;
    }

    public List<MotivoFueraServicio> getListaMotivosDisponibles() {
        return List.of(
                new MotivoFueraServicio("Sin energía", "NO ENERGY"),
                new MotivoFueraServicio("Mantenimiento", "MAINTENANCE"),
                new MotivoFueraServicio("Problema técnico", "TECNICAL PROBLEM")
        );
    }

    private void cargarOrdenesHardCodeadas() {
        EstacionSismologica estacion1 = new EstacionSismologica(
                "SISMO01", "DOC123", "2024-03-15", -31, -64, "Estación Norte", 1001
        );

        EstacionSismologica estacion2 = new EstacionSismologica(
                "SISMO02", "DOC456", "2024-04-10", -32, -63, "Estación Sur", 1002
        );

        Empleado empleadoDummy = new Empleado("12345678", "Pipo", "Pipo123@gmail.com", "351392212");
        Estado estadoInicial = new Estado("OrdenInspeccion", "OICerrada");

        ordenes.add(new OrdenDeInspeccion(101, "2025-05-01 10:00", null, "2025-04-30 09:00", null, estacion1, estadoInicial, empleadoDummy));
        ordenes.add(new OrdenDeInspeccion(102, "2025-05-01 12:00", null, "2025-04-30 11:00", null, estacion2, estadoInicial, empleadoDummy));
    }

    public List<String> mostrarOrdenesDisponiblesComoTexto() {
        List<String> resultado = new ArrayList<>();
        for (OrdenDeInspeccion orden : ordenes) {
            resultado.add("Número: " + orden.getNumeroOrden()
                    + " | Estación: " + orden.getEstacionSismologica().getNombre()
                    + " | Finalización: " + orden.getFechaHoraFinalizacion());
        }
        return resultado;
    }

    public OrdenDeInspeccion solicitarSeleccionOrdenesInspeccionRealizadas(int numero) {
        for (OrdenDeInspeccion orden : ordenes) {
            if (orden.getNumeroOrden() == numero) {
                ordenSeleccionada = orden;
                return orden;
            }
        }
        return null;
    }

    public void pedirObservacion(OrdenDeInspeccion orden, String observacion) {
        orden.setObservacionCierre(observacion);
    }

    public List<String> buscarMotivosComoTexto() {
        List<String> texto = new ArrayList<>();
        List<MotivoFueraServicio> motivos = getListaMotivosDisponibles();
        for (int i = 0; i < motivos.size(); i++) {
            MotivoFueraServicio motivo = motivos.get(i);
            texto.add((i + 1) + ". " + motivo.getMotivoTipo() + " - " + motivo.getComentario());
        }
        return texto;
    }

    public void tomarTipoMotivo(List<Integer> indicesSeleccionados) {
        List<MotivoFueraServicio> motivos = getListaMotivosDisponibles();
        for (Integer index : indicesSeleccionados) {
            if (index >= 0 && index < motivos.size()) {
                motivosSeleccionados.add(motivos.get(index));
            }
        }
    }

    public void tomarComentario(List<String> comentarios) {
        for (int i = 0; i < motivosSeleccionados.size() && i < comentarios.size(); i++) {
            motivosSeleccionados.get(i).setComentario(comentarios.get(i));
        }
    }

    public void tomarConfirmacion(OrdenDeInspeccion orden) {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
    }

    public boolean validarObservacionExistente(OrdenDeInspeccion orden) {
        String observacion = orden.getObservacionCierre();
        return observacion != null && !observacion.trim().isEmpty();
    }

    public boolean validarMotivoExistente() {
        return !motivosSeleccionados.isEmpty();
    }

    public boolean buscarEstadoCerradaOI(Estado estado) {
        String nombre = estado.getNombreEstado();
        String ambito = estado.getAmbito();
        return "OrdenInspeccion".equalsIgnoreCase(ambito) && "OICerrada".equalsIgnoreCase(nombre);
    }

    public String getFechaHoraActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        return fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public List<String> buscarFueraServicio() {
        List<String> resultado = new ArrayList<>();
        for (Sismografo s : sismografos) {
            Estado estado = s.getEstadoActual();
            if (estado != null && "FueraDeServicio".equalsIgnoreCase(estado.getNombreEstado()) && "Sismografo".equalsIgnoreCase(estado.getAmbito())) {
                resultado.add("- ID: " + s.getIdentificadorSismografo()
                        + " | Modelo: " + s.getModelo()
                        + " | Estación: " + s.getEstacionSismologica().getNombre());
            }
        }
        return resultado;
    }

    public void cerrarOrdenInspeccion() {
        if (ordenSeleccionada != null) {
            String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ordenSeleccionada.setFechaHoraCierre(fechaHoraActual);
            ordenSeleccionada.setEstado(new Estado("OrdenInspeccion", "OICerrada"));
        }
    }

    public void ponerSismografoFueraDeServicio() {
        if (ordenSeleccionada != null) {
            for (Sismografo sismografo : sismografos) {
                if (sismografo.getEstacionSismologica().equals(ordenSeleccionada.getEstacionSismologica())) {
                    sismografo.fueraDeServicio(motivosSeleccionados, ordenSeleccionada.getEmpleado());
                    break;
                }
            }
        }
    }
}

// ESTE CODIGO YA QUEDA LISTO PARA SER CONSUMIDO EN JAVA FX
