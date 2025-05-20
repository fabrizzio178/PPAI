package com.ppai;

import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Empleado;
import com.ppai.Service.GestorResultadosMUERTO;
import com.ppai.app.MiVentanaFX;
import com.ppai.controller.Monitor;
import javafx.application.Application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Application.launch(MiVentanaFX.class, args);
            Monitor hola = new Monitor();
            hola.cerrarOrdenInsp();

        }
    }


