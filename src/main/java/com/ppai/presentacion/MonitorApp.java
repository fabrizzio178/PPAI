package com.ppai.presentacion;

import com.ppai.PpaiApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MonitorApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(PpaiApplication.class).run();
    }


    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(PpaiApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene escena = new Scene(loader.load());


        stage.setScene(escena);
        stage.show();
    }
    @Override // se utiliza para indicar que un métod en una subclase está sobrescribiendo un métod de su superclase
    // Paramos el programa cuando se cierre la ventana
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
