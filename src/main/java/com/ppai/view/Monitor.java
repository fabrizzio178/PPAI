package com.ppai.view;

import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Service.GestorResultados;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class Monitor {

    private final GestorResultados gestor = new GestorResultados();

    @FXML
    private ComboBox<String> comboOrdenes;

    @FXML
    private ListView<String> listMotivos;

    @FXML
    private TextArea textComentario;

    @FXML
    private Label labelResultado;

    @FXML
    private Button btnSeleccionar, btnConfirmar;

    public void initialize() {
        comboOrdenes.setItems(FXCollections.observableArrayList(gestor.mostrarOrdenesDisponiblesComoTexto()));
        listMotivos.setItems(FXCollections.observableArrayList(gestor.buscarMotivosComoTexto()));
    }

    @FXML
    public void seleccionarOrden() {
        int index = comboOrdenes.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            // Como los números en texto inician en 101, 102... asumimos índice + 101
            gestor.solicitarSeleccionOrdenesInspeccionRealizadas(101 + index);
            labelResultado.setText("Orden seleccionada.");
        }
    }

    @FXML
    public void tomarTipoMotivo() {
        List<Integer> indices = new ArrayList<>(listMotivos.getSelectionModel().getSelectedIndices());
        gestor.tomarTipoMotivo(indices);
        labelResultado.setText("Motivos seleccionados.");
    }

    @FXML
    public void tomarComentario() {
        String comentario = textComentario.getText();
        List<String> comentarios = new ArrayList<>();
        for (int i = 0; i < listMotivos.getSelectionModel().getSelectedIndices().size(); i++) {
            comentarios.add(comentario); // mismo comentario para todos los motivos
        }
        gestor.tomarComentario(comentarios);
        labelResultado.setText("Comentario registrado.");
    }

    @FXML
    public void confirmarCierre() {
        OrdenDeInspeccion orden = gestor.solicitarSeleccionOrdenesInspeccionRealizadas(101 + comboOrdenes.getSelectionModel().getSelectedIndex());
        if (gestor.validarObservacionExistente(orden) && gestor.validarMotivoExistente()) {
            gestor.cerrarOrdenInspeccion();
            gestor.ponerSismografoFueraDeServicio();
            labelResultado.setText("Orden cerrada y sismógrafo marcado fuera de servicio.");
        } else {
            labelResultado.setText("Falta observación o motivo.");
        }
    }
}
