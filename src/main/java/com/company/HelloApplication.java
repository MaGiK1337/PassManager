package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

     public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("first.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Master of passwords");
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}