package com.company.controllers;

import com.company.DB.JavaToSql;
import com.company.controllers.exchanger.ExchangerData;
import com.company.userdata.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.company.HelloApplication.mainStage;

public class UpdateScreenController implements Initializable {

    @FXML
    private Button btUpdate;

    @FXML
    private TextField inputNewLogin;

    @FXML
    private TextField inputNewPassword;

    @FXML
    private TextField inputNewSource;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserData dataToUpdate = ExchangerData.item;
        inputNewSource.setText(dataToUpdate.getSource());
        inputNewLogin.setText(dataToUpdate.getLogin());
        inputNewPassword.setText(dataToUpdate.getPassword());

        btUpdate.setOnAction(event -> {
            updateData(dataToUpdate);
            try {
                changeWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateData(UserData dataToUpdate) {
        new JavaToSql().UpdatePasswordSQL(dataToUpdate.getId(),inputNewSource.getText(),inputNewLogin.getText(),inputNewPassword.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные обновлены", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private void changeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/company/second.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 925, 800);
        mainStage.setScene(scene);
        mainStage.show();
    }
}