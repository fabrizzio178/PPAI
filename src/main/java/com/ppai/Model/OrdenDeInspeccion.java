package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter

public class OrdenDeInspeccion {
    private Integer numeroOrden;
    private LocalDateTime fechaHoraFinalizacion;
    private LocalDateTime fechaHoraCierre;
    private LocalDateTime fechaHoraInicio;
    private String observacionCierre;
    private String tareaAsignada;
    private EstacionSismologica estacionSismologica;
    private Estado estado;
    private Empleado empleado;

    public OrdenDeInspeccion(
            Integer numeroOrden,
            LocalDateTime fechaHoraFinalizacion,
            LocalDateTime fechaHoraCierre,
            LocalDateTime fechaHoraInicio,
            String observacionCierre,
            String tareaAsignada,
            EstacionSismologica estacionSismologica,
            Estado estado,
            Empleado empleado
    ) {
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.observacionCierre = observacionCierre;
        this.tareaAsignada = tareaAsignada;
        this.estacionSismologica = estacionSismologica;
        this.estado = estado;
        this.empleado = empleado;
    }

    public Boolean sosDeEmpleado (Empleado empleado) {
        return Objects.equals(this.empleado.getNombre(), empleado.getNombre()) && Objects.equals(this.empleado.getApellido(), empleado.getApellido());
    }

    public Boolean sosCompletamenteRealizada() {
        return this.estado.sosAmbitoOrdenInspeccion() && this.estado.sosCompletamenteRealizada();

    }

    public ArrayList<Object> getDatosOI(ArrayList<Sismografo> todosSismografos) {           // ESTO SERIA MUCHO MAS FACIL SI ESTUVIERA DISTINTO EL DIAGRAMA DE SECUENCIA
        ArrayList<Object> datos = new ArrayList<>();
        datos.add(this.numeroOrden);
        datos.add(this.fechaHoraFinalizacion);
        ArrayList<String> nombreEstacionEIdentificador = this.estacionSismologica.getNombreEstacion(todosSismografos);
        datos.add(nombreEstacionEIdentificador.get(0)); //nombre estacion
        datos.add(nombreEstacionEIdentificador.get(1)); //identificador sismografo
        return datos;

    }
}
