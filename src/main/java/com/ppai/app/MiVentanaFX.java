package com.ppai.app;

import com.ppai.controller.Monitor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MiVentanaFX extends Application {

    @Setter
    private static ArrayList<ArrayList<String>> datosOrdenes;
    private final HashMap<CheckBox, ArrayList<String>> mapaChecksOrdenes = new HashMap<>();
    @Getter
    private static ArrayList<String> seleccionados = new ArrayList<>();
    private static Monitor monitor;

    public static void inicializarVentana(Monitor monitorInstance) {
        monitor = monitorInstance;
        mostrar();
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label labelOrdenes = new Label("Órdenes de Inspección:");
        labelOrdenes.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        root.getChildren().add(labelOrdenes);

        VBox contenedorOrdenes = new VBox(5);
        if (datosOrdenes != null && !datosOrdenes.isEmpty()) {
            for (ArrayList<String> orden : datosOrdenes) {
                StringBuilder texto = new StringBuilder();
                if (!orden.isEmpty()) {
                    texto.append("Orden: ").append(orden.get(0));
                    for (int i = 1; i < orden.size(); i++) {
                        switch (i) {
                            case 1 -> texto.append(" | Estación: ").append(orden.get(i));
                            case 2 -> texto.append(" | Fecha: ").append(orden.get(i));
                            default -> texto.append(" | ").append(orden.get(i));
                        }
                    }
                }

                CheckBox checkBox = new CheckBox(texto.toString());
                checkBox.setWrapText(true);
                checkBox.setStyle("-fx-padding: 5px;");
                mapaChecksOrdenes.put(checkBox, orden);
                contenedorOrdenes.getChildren().add(checkBox);
            }
        } else {
            Label noDataLabel = new Label("No hay órdenes disponibles");
            contenedorOrdenes.getChildren().add(noDataLabel);
        }

        ScrollPane scrollPane = new ScrollPane(contenedorOrdenes);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(300);
        root.getChildren().add(scrollPane);





        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16;");
        btnConfirmar.setOnAction(e -> {
            seleccionados.clear();
            for (Map.Entry<CheckBox, ArrayList<String>> entry : mapaChecksOrdenes.entrySet()) {
                if (entry.getKey().isSelected() && !entry.getValue().isEmpty()) {
                    int numeroOrden = Integer.parseInt(entry.getValue().get(0));
                    if (monitor != null) {
                        monitor.seleccionaOrdenInsp(numeroOrden);
                    }
                    break;
                }
            }
            primaryStage.close();
        });

        buttonContainer.getChildren().add(btnConfirmar);
        root.getChildren().add(buttonContainer);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Seleccionar Órdenes de Inspección");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void mostrar() {
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                MiVentanaFX ventana = new MiVentanaFX();
                ventana.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}