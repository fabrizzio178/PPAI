package com.ppai;

import com.ppai.presentacion.MonitorApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpaiApplication {

    public static void main(String[] args) {
        Application.launch(MonitorApp.class, args);
    }

}
