package com.ppai.Service;
import com.ppai.Model.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Getter
@Setter

public class GestorResultados {
    private Empleado usuarioLogueado;
    private ArrayList<OrdenDeInspeccion> ordenesInsp;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacion;
    private ArrayList<TipoMotivo> tiposMotivosFueraDeServicio;
    private Estado estadoOrdenInspeccionCerrada;
    private ArrayList<String> comentarios;
    private Boolean confirmacion;
    private Date fechaHoraActual;
    private Rol rolEmpleado;
    private Estado estadoSismografoFueraDeServicio;
    private ArrayList<String> mailsEmpleados;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Sismografo> sismografos;

    // 1era función que busca el RI logueado
    public void buscarRIlogueado(Sesion sesion) {
        this.usuarioLogueado = sesion.getUsuario().getRILogueado();; //CAMBIAR DIAG SECUENCIA  GESTOR -> SESION -> USUARIO -> EMPLEADO
    }

    // 2da función que busca datos de las ordenes de inspeccion del R.I logueado
//    public void buscarOrdenesInsp(ArrayList<OrdenDeInspeccion> ordenesInsp) {
//        Empleado logueado = this.usuarioLogueado;
//        String nombreEstacion = "";
//        String numeroSismografo = "";
//        String identificadorSismografo = "";
//        for (OrdenDeInspeccion orden : ordenesInsp) {
//            if (orden.sosDeEmpleado(logueado) && orden.sosCompletamenteRealizada()) {
//               orden.getDatosIO();
//               nombreEstacion = orden.buscarNombreEstacion();
//
//               for (Sismografo sismografo : sismografos) {
//                   if (sismografo.sosDeEstacion(orden.getEstacionSismologica())){
//                       identificadorSismografo = sismografo.getIdentificadorSismografo();
//                   }
//               }
//            }
//        }
//    }

    public void buscarOrdenesInsp(ArrayList<OrdenDeInspeccion> ordenesInsp) { //es la mitad del loop grande, creo que el resto se puede implementar en el mostrar
        Empleado empleado = this.usuarioLogueado;                             //si no hay que crear otro atributo para los datos (inneccesario) y el nombre de la estacion
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

    public void ordenarPorFechaFinalizacion() {
        this.ordenesInsp.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }

    public ArrayList<ArrayList<Object>> mostrarDatosOrdenInspeccion(ArrayList<OrdenDeInspeccion> ordenesInsp, ArrayList<Sismografo> todosSismografos) {
        ArrayList<ArrayList<Object>> datosOrdenes = new ArrayList<>();  //PARAMETROS: lista de las ordenes filtradas arriba, lista de todos los sismografos
        for (OrdenDeInspeccion orden : ordenesInsp) {
            datosOrdenes.add(orden.getDatosOI(todosSismografos));
        }
        return datosOrdenes; //enviar a pantalla
    }

    public void tomarSeleccionOrdenInsp(int numeroOrden) {
        for (OrdenDeInspeccion orden : ordenesInsp) {
            if (orden.getNumeroOrden() == numeroOrden) {
                this.ordenSeleccionada = orden;
                break;
            };
        }
    }

    public void pedirObservacion(OrdenDeInspeccion orden, String observacion) {
        // disparador para pantalla, aca llama al metodo de pantalla
    }

    public void tomarObservacion(String observacion) {
        this.ordenSeleccionada.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }

    public void permitirActualizarSismografoComoFS() {
        System.out.println("Se ha permitido actualizar el sismografo como fuera de servicio.");
    }

    public void buscarMotivos(ArrayList<TipoMotivo> tiposMotivo) {

        System.out.println("Motivos disponibles para marcar como fuera de servicio:");
        for (TipoMotivo tipo : tiposMotivo) {
            System.out.println(tipo.getNombreTipo() + " - " + tipo.getDescripcion());  // ESTO DEBE IR A PANTALLA NO A UN PRINT
        }
    }

    public void tomarTiposMotivo(ArrayList<TipoMotivo> selecTiposMotivo) {
        this.tiposMotivosFueraDeServicio = selecTiposMotivo;
    }

        //System.out.println("Seleccione los motivos (numeros separados por espacio, por ejemplo: 1 2 3):");
        String[] entradas = scanner.nextLine().split(" ");

        for (String entrada: entradas){
            try{
                int index = Integer.parseInt(entrada) - 1;
                if (index >= 0 && index < motivos.size()){
                    MotivoFueraServicio motivo = motivos.get(index);
                    System.out.println("Ingrese comentario para '" + motivo.getMotivoTipo() +"':");
                    String comentario = scanner.nextLine();
                    motivo.setComentario(comentario);
                    motivosSeleccionados.add(motivo);
                }
            } catch (NumberFormatException ignored){
            }
        }

        System.out.println("Motivos seleccionados: ");
        for (MotivoFueraServicio motivoSeleccionado: motivosSeleccionados){
            System.out.println("- " + motivoSeleccionado.getMotivoTipo());
        }
    }

    public void tomarConfirmacion(OrdenDeInspeccion orden) {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
        System.out.println("Orden cerrada con éxito. Fecha de cierre: " + fechaHoraActual);
    }

    public void validarObservacionExistente(OrdenDeInspeccion orden){
        String observacion = orden.getObservacionCierre();
        if (observacion == null || observacion.trim().isEmpty()) {
            System.out.println("No se ingreso ninguna observación");
        } else{
            System.out.println("Observacion registrada: " + observacion);
        }
    }

    public void validarMotivoExistente(){
        if (motivosSeleccionados.isEmpty()){
            System.out.println("No se seleccionaron motivos.");
        } else{
            System.out.println("Hay motivos seleccionados");
        }
    }

    public void buscarEstadoCerradaOI(Estado estado){
        String nombre = estado.getNombreEstado();
        String ambito = estado.getAmbito();

        if ("OrdenInspeccion".equalsIgnoreCase(ambito) && "OICerrada".equalsIgnoreCase(nombre)){
            System.out.println("Estado válido: OICerrada en ámbito OrdenInspeccion");
        }else{
            System.out.println("Estado no coincide con OICerrada/OrdenInspeccion.");
        }
    }
}
