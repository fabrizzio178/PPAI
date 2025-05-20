package com.ppai.Service;


import com.ppai.Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorResultado {
    // Arrays con todas las instancias de cada clase que el gestor conoce
    private ArrayList<Sismografo> todosSismografos;
    private ArrayList<OrdenDeInspeccion> todosOrdenInspeccion;
    private ArrayList<Empleado> todosEmpleados;
    private Sesion sesionActual;
    private ArrayList<Estado> todosEstados;
    private ArrayList<TipoMotivo> todosTipoMotivo;

    public void hardcodearSesion() {
        this.sesionActual = new Sesion(
                LocalDateTime.now(),
                null,
                new Usuario(
                        "1234",
                        "xX_Pipo_Xx",
                        "TETA",
                        true,
                        new Empleado(
                                "Llabot",
                                "Ignacio",
                                "sex@gmail.com",
                                "1234567890",
                                new Rol(
                                        "Programador de juegos porno 3D",
                                        "Programador"
                                )
                        )
                )
        );
    }

    //Atributos del gestor
    private Empleado usuarioLogueado;


    public void opCerrarOrdenInspeccion() {
        this.hardcodearSesion();

        this.buscarRILogueado();
    }

    public void buscarRILogueado() {
        this.usuarioLogueado = sesionActual.getUsuarioLogueado();
        System.out.println("PENIS");
    }
}
