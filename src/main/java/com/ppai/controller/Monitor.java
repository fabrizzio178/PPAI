package com.ppai.controller;
import com.ppai.app.*;
import com.ppai.app.MiVentanaFX;


import com.ppai.Service.GestorResultados;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class Monitor {
    private GestorResultados gestor;

    public void cerrarOrdenInsp() {
        // Simula la interacción del actor con Monitor
        habilitarVentana();  // Se ajusta al orden del diagrama
    }

    public void habilitarVentana() {
        this.gestor = new GestorResultados();
        this.gestor.setMonitor(this);
        this.gestor.opCerrarOrdenInspeccion();  // Llamada reflejada en el diagrama
    }


    public void solicitarSeleccionOrdenesInspeccionRealizada() {
    ArrayList<ArrayList<Object>> arrayDatosOi = this.gestor.getDatosOrdenes();
    ArrayList<ArrayList<String>> datosConvertidos = new ArrayList<>();
    
    for (ArrayList<Object> fila : arrayDatosOi) {
        ArrayList<String> filaConvertida = new ArrayList<>();
        for (Object dato : fila) {
            filaConvertida.add(dato.toString());
        }
        datosConvertidos.add(filaConvertida);
    }

    // Mover estas llamadas fuera del bucle
    MiVentanaFX.setDatosOrdenes(datosConvertidos);
    MiVentanaFX.main(null);
}
}


    /*public void setDatosOrdenes(ArrayList<ArrayList<String>> datosOrdenes) {
        for (ArrayList<String> orden : datosOrdenes) {
            String texto = String.join(" | ", orden);
            CheckBox checkBox = new CheckBox(texto);
            contenedorOrdenes.getChildren().add(checkBox);
        }
    }




/*   // Pasar los datos a la ventana
        MiVentanaFX.setDatosOrdenes(datosConvertidos);

        // Lanzar la ventana JavaFX
        MiVentanaFX.main(null);

      */


    /* private void solicitarSeleccionOrdenesInspeccionRealizada() {

        ArrayList<ArrayList<String>> ordenes = gestor.mostrarDatosOrdenInspeccion(gestor.getOrdenesInsp());
    }
    */




    // Métodos para inicializar, obtener valores, manejar eventos, etc.




/*

cerrarOrdenInsp()
habilitarVentana()

solicitarSeleccionOrdenesInspecionRealizada()
seleccionaOrdenInsp()
pedirObservacion()
ingresarObservacion()
mostrarTiposMotivos()
solicitarSelTipoMotivo()
seleccionaTipoMotivo()
pedirComentario()
tomarComentario
solicitarConfirmacion()
confirmaCierre()

*/
/* @FXML
    private ComboBox<OrdenDeInspeccion> cmbOrdenesInspeccionesRealizadas;

    @FXML
    private TextArea txtObservaciones;

    @FXML
    private Label labelObservaciones;

    @FXML
    private TableView<String> grillaTipoMotivo;

    @FXML
    private ComboBox<String> cmbTipoMotivo;

    @FXML
    private TextArea txtComentario;

    @FXML
    private Label labelComentario;

    @FXML
    private Button btnConfirmacion;
*/