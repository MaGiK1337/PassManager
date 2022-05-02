package com.company.controllers;

import com.company.DB.JavaToSql;
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
import java.util.Objects;
import java.util.ResourceBundle;

import static com.company.HelloApplication.mainStage;

public class HelloController implements Initializable {
    String MasterKey;

    private boolean createMasterKey(String newMasterKey){
        new JavaToSql().addMasterKey(newMasterKey);
        MasterKey = newMasterKey;
        return true;
    }

    private boolean checkMasterKeyCorrect(String masterKey){
        if (Objects.equals(MasterKey, "")){
            return true;
        }else{
            return Objects.equals(MasterKey, masterKey);
        }
    }

    private boolean checkExistenceMasterKey(){
        if (Objects.equals(new JavaToSql().checkMasterPassword(), "")){
            MasterKey = "";
            return false;
        }else{
            MasterKey = new JavaToSql().checkMasterPassword();
            return true;
        }
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
            if(!inputMasterKey.getText().isEmpty()){
                try {
                    if (!Objects.equals(MasterKey, "")) {
                        if (checkMasterKeyCorrect(inputMasterKey.getText()))
                            changeWindow();
                        else
                            alertWrongPass();
                    } else {
                        if(createMasterKey(inputMasterKey.getText()))
                            changeWindow();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                alertWrongPass();
            }
        });
    }

    private void alertWrongPass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Неверный MasterKey!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
}