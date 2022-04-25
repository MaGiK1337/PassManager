package com.company.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.company.HelloApplication.mainStage;

public class HelloController implements Initializable {

    private final boolean isExist = true;

    private boolean createMasterKey(String newMasterKey){
        // TODO: 15.03.2022 Создание MasterKey
        return newMasterKey.length() >= 8;
    }

    private boolean checkMasterKeyCorrect(String masterKey){
        // TODO: 15.03.2022 Проверка на корректность MasterKey
        return masterKey.equals("key");
    }

    private boolean checkExistenceMasterKey(){
        // TODO: 15.03.2022 Проверка существования MasterKey
        return isExist;
    }

    private void changeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/company/second.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 925, 800);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    private Button btEnterRegMasterKey;

    @FXML
    private TextField inputMasterKey;

    @FXML
    private Text txEnterRegMasterKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        checkMasterKeyFunc();
        bindButtons();

    }

    private void checkMasterKeyFunc() {
        if(checkExistenceMasterKey()){
            txEnterRegMasterKey.setText("Enter MasterKey");
            btEnterRegMasterKey.setText("Enter");

        } else {
            txEnterRegMasterKey.setText("Create MasterKey");
            btEnterRegMasterKey.setText("Create");

        }
    }

    private void bindButtons() {
        btEnterRegMasterKey.setOnAction(event -> {
            try {
                changeWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if(!inputMasterKey.getText().isEmpty()){
//                try {
//                    if (isExist) {
//                        if (checkMasterKeyCorrect(inputMasterKey.getText()))
//                            changeWindow();
//                        else
//                            alertWrongPass();
//                    } else {
//                        if(createMasterKey(inputMasterKey.getText()))
//                            changeWindow();
//                    }
//
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                alertWrongPass();
//            }

        });
    }

    private void alertWrongPass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Неверный MasterKey!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
}