package com.ppai.Service;
import com.ppai.Model.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;


@Getter
@Setter

public class GestorResultados {
    private Usuario usuarioLogueado; // usado funcion1  ----> guarda el puntero al empleado
    private ArrayList<OrdenDeInspeccion> ordenesInsp; // usado fucion 1-2-3-5 ---> guarda las Ord de Insp del empleado y completamente realizadas
    private OrdenDeInspeccion ordenSeleccionada; // usado funcion 5-7-18-19 ----> guarda 1 OI seleccionada por el RI
    private ArrayList<ArrayList<Object>> datosOrdenes;
    private String observacion;
    private ArrayList<TipoMotivo> tiposMotivosFueraDeServicio; // usado funcion 10-14 ----> guarda 1 o mas motivos seleccionado por el usuario
    private Estado estadoOrdenInspeccionCerrada; // usado funcion  15-18 ----> guarda el nombre del estado de la OI (cerrada)
    private ArrayList<String> comentarios; // usado funcion 11 ----> guarda 1 comntario ingresado por el usuario para 1 tipo seleccionado
    private Boolean confirmacion;
    private LocalDateTime fechaHoraActual; // usado funcion 16-18 ----> guarda la fecha y la hora actual
    private Rol rolEmpleado; // Limpiar
    private Estado estadoSismografoFueraDeServicio; // usado funcion 17 ----> guarda el nombre del estado del Sismografo (fuera de servicio)
    private ArrayList<String> mailsEmpleados;
    private ArrayList<Usuario> usuarios; //TODOS LOS USUARIOS
    private ArrayList<Sismografo> sismografos; //TODOS LOS SISMOGRAFOS usado funcion 19
    private Sesion sesion;

    // 1era función que busca el RI logueado
    public Empleado buscarRIlogueado() {
        Usuario usuario = sesion.getUsuario();
        return usuario.getRILogueado(); //CAMBIAR DIAG SECUENCIA  GESTOR -> SESION -> USUARIO -> EMPLEADO
    }


    // 2
    public void buscarOrdenesInsp(ArrayList<OrdenDeInspeccion> ordenesInsp) { //es la mitad del loop grande, creo que el resto se puede implementar en el mostrar
        Empleado empleado = buscarRIlogueado();                             //si no hay que crear otro atributo para los datos (inneccesario) y el nombre de la estacion
        ArrayList<OrdenDeInspeccion> ordenesInspSel = new ArrayList<>();
        for (OrdenDeInspeccion orden : ordenesInsp) {
            Boolean esDeEmpleado = orden.sosDeEmpleado(empleado);
            Boolean esCompletamenteRealizada = orden.sosCompletamenteRealizada();

            if (esDeEmpleado && esCompletamenteRealizada) {
                ordenesInspSel.add(orden);
            }
        }
        this.ordenesInsp = ordenesInspSel;
    }
    // 3
    public void ordenarPorFechaFinalizacion() {
        this.ordenesInsp.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }
    // 4
    public void mostrarDatos(ArrayList<OrdenDeInspeccion> ordenesInsp, ArrayList<Sismografo> todosSismografos) {
        ArrayList<ArrayList<Object>> datosOrdenes = new ArrayList<>();  //PARAMETROS: lista de las ordenes filtradas arriba, lista de todos los sismografos
        for (OrdenDeInspeccion orden : ordenesInsp) {
            datosOrdenes.add(orden.getDatosOI(todosSismografos));
        }
        this.datosOrdenes = datosOrdenes;
    }
    // 5
    public void tomarSeleccionOrdenInsp(int numeroOrden) {
        for (OrdenDeInspeccion orden : ordenesInsp) {
            if (orden.getNumeroOrden() == numeroOrden) {
                this.ordenSeleccionada = orden;
                break;
            }
        }
    }
    // 6
    public void pedirObservacion(OrdenDeInspeccion orden, String observacion) {
        // disparador para pantalla, aca llama al metodo de pantalla
    }
    // 7
    public void tomarObservacion(String observacion) {
        this.ordenSeleccionada.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }
    // 8
    public void permitirActualizarSismografoComoFS() {
        System.out.println("Se ha permitido actualizar el sismografo como fuera de servicio.");
    }
    // 9
    public void buscarMotivos(ArrayList<TipoMotivo> tiposMotivo) {

        System.out.println("Motivos disponibles para marcar como fuera de servicio:");
        for (TipoMotivo tipo : tiposMotivo) {
            System.out.println(tipo.getNombreTipo() + " - " + tipo.getDescripcion());  // ESTO DEBE IR A PANTALLA NO A UN PRINT
        }
    }
    // 10
    public void tomarTiposMotivo(TipoMotivo selecTipoMotivo) {
        this.tiposMotivosFueraDeServicio.add(selecTipoMotivo);  //guarda la seleccion del usuario
    }
    // 11
    public void tomarComentarios(String comentario) {
        this.comentarios.add(comentario);  //guarda los comentarios del usuario
    }
    // 12
    public void tomarConfirmacion(OrdenDeInspeccion orden) {
        LocalDateTime fechaHoraActual = LocalDateTime.now(); //.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
    }
    // 13
    public boolean validarObservacionExistente(OrdenDeInspeccion orden) {
        String observacion = orden.getObservacionCierre(); // Consigue la observacion de la orden seleccionada
        return observacion != null && !observacion.trim().isEmpty(); // retorna TRUE si existe la observacion y no esta vacia
    }
    // 14
    public boolean validarMotivoExistente() {
        return !tiposMotivosFueraDeServicio.isEmpty(); //valida que se haya elegido por lo menos un motivo
    }
    // 15
    public void buscarEstadoCerradaOI(ArrayList<Estado> estados) {
        for(Estado estado : estados) {
            if(estado.sosAmbitoOrdenInspeccion() && estado.sosOICerrada()) {
                this.estadoOrdenInspeccionCerrada = estado;
            }
        }
    }
    // 16
    public void getFechaHoraActual() {
        this.fechaHoraActual = LocalDateTime.now();
    }
    // 17
    public void buscarFueraServicio(ArrayList<Estado> estados) {
        for (Estado estado : estados){
            if(estado.sosAmbitoSismografo() && estado.sosFueraDeServicio()){
                this.estadoSismografoFueraDeServicio = estado;
            }
        }
    }
    // 18
    public void cerrarOrdenInspeccion() {
        ordenSeleccionada.cerrar(fechaHoraActual, estadoOrdenInspeccionCerrada);
    }
    // 19
    public void ponerSismografoFueraDeServicio() {
        if (ordenSeleccionada != null) {
        for (Sismografo sismografo : sismografos) {
            if (ordenSeleccionada.getEstacionSismologica().sosMiSismografo(sismografo)) {
                sismografo.fueraDeServicio(this.tiposMotivosFueraDeServicio, this.comentarios, this.fechaHoraActual, this.estadoSismografoFueraDeServicio, buscarRIlogueado());
                break;
                }
            }
        }
    }

    public void enviarMail(){

    }

}
