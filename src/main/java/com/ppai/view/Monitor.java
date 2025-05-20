package com.ppai.view;

import com.ppai.Service.GestorResultados;

import java.util.ArrayList;

public class Monitor {
    private GestorResultados gestor;

    public void cerrarOrdenInsp() {
        // Simula la interacción del actor con Monitor
        habilitarVentana();  // Se ajusta al orden del diagrama
    }

    public void habilitarVentana() {
        this.gestor = new GestorResultados();
        this.gestor.cerrarOrdenInsp();  // Llamada reflejada en el diagrama
    }


    public void solicitarSeleccionOrdenesInspeccionRealizada() {
        ArrayList<ArrayList<Object>> arrayDatosOi = this.gestor.getDatosOrdenes();


    }


}




    /* private void solicitarSeleccionOrdenesInspeccionRealizada() {

        ArrayList<ArrayList<String>> ordenes = gestor.mostrarDatosOrdenInspeccion(gestor.getOrdenesInsp());
    }
    */




    // Métodos para inicializar, obtener valores, manejar eventos, etc.


}

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
    private Button btnConfirmacion; */