package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class OrdenDeInspeccion {

    private static ArrayList<OrdenDeInspeccion> ordenesDeInspeccion = new ArrayList<>();

    private Integer numeroOrden;
    private LocalDateTime fechaHoraFinalizacion;
    private LocalDateTime fechaHoraCierre;
    private LocalDateTime fechaHoraInicio;
    private String observacionCierre;
    private EstacionSismologica estacionSismologica;
    private Estado estado;
    private Empleado empleado;

    public OrdenDeInspeccion(
            Integer numeroOrden,
            LocalDateTime fechaHoraFinalizacion,
            LocalDateTime fechaHoraCierre,
            LocalDateTime fechaHoraInicio,
            String observacionCierre,
            EstacionSismologica estacionSismologica,
            Estado estado,
            Empleado empleado
    ){
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.observacionCierre = observacionCierre;
        this.estacionSismologica = estacionSismologica;
        this.estado = estado;
        this.empleado = empleado;
        ordenesDeInspeccion.add(this);
    }

    public Boolean sosDeEmpleado(Empleado empleado) {
        return this.empleado.getNombre().equalsIgnoreCase(empleado.getNombre()) &&
                this.empleado.getApellido().equalsIgnoreCase(empleado.getApellido());
    }

    public Boolean sosCompletamenteRealizada() {
        return this.estado.sosAmbitoOrdenInspeccion() && this.estado.sosCompletamenteRealizada();
    }

    public ArrayList<Object> getDatosOI(ArrayList<Sismografo> todosSismografos) {
        ArrayList<Object> datos = new ArrayList<>();
        datos.add(this.numeroOrden);
        datos.add(this.fechaHoraFinalizacion);
        datos.add(this.estacionSismologica.getNombre());
        for (Sismografo sismografo : todosSismografos) {
            if (this.estacionSismologica.sosMiSismografo(sismografo)) {
                datos.add(sismografo.getIdentificadorSismografo());
                break;
            }
        }
        return datos;
    };


    public void cerrar(LocalDateTime fechaHoraCierre, Estado estadoCierre) {
        this.setFechaHoraCierre(fechaHoraCierre);
        this.setEstado(estadoCierre);
    }
}
