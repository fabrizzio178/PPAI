package com.ppai.view;

import com.ppai.Service.GestorResultados;
import javafx.scene.control.*;

public class Monitor {
    private GestorResultados gestor;


    @FXML
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

    // Métodos para inicializar, obtener valores, manejar eventos, etc.


}
