package com.ppai.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class MiVentanaFX extends Application {

    @Setter
    private static ArrayList<ArrayList<String>> datosOrdenes;
    private final HashMap<CheckBox, ArrayList<String>> mapaChecksOrdenes = new HashMap<>();

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
                // Crear un formato más legible
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

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Seleccionar Órdenes de Inspección");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}