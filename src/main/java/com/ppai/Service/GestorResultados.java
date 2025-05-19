package com.ppai.Service;
import com.ppai.Model.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
        this.ordenesInsp.sort();
    }
}
