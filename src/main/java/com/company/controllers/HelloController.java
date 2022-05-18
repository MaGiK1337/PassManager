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

/**
 * Класс, который является первым окном и отвечает за создание Мастер пароля или вход по нему
 */
public class HelloController implements Initializable {
    String MasterKey;

    /**
     * Функция, которая создает мастер пароль, при создании возвращает параметр true
     */
    private boolean createMasterKey(String newMasterKey){
        new JavaToSql().addMasterKey(newMasterKey);
        MasterKey = newMasterKey;
        return true;
    }
    /** Функция, которая проверяет корректность введенного мастер-пароля
     * и возвращает булевую переменную "Корректен ли мастер пароль"
     */
    private boolean checkMasterKeyCorrect(String masterKey){
        if (Objects.equals(MasterKey, "")){
            return true;
        }else{
            return Objects.equals(MasterKey, masterKey);
        }
    }
    /** Функция проверяет, существует ли мастер пароль и возвращает булевую переменную
     * Существует - true, не существует - false
    */
    private boolean checkExistenceMasterKey(){
        if (Objects.equals(new JavaToSql().checkMasterPassword(), "")){
            MasterKey = "";
            return false;
        }else{
            MasterKey = new JavaToSql().checkMasterPassword();
            return true;
        }
    }

    /**
     * Функция, которая меняет 1-ое окно на 2-ое
     * @throws IOException
     */
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

    /** Функция инциализации данных
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkMasterKeyFunc();
        bindButtons();
    }

    /**
     * Функция, которая задает текст UI элементов
     */
    private void checkMasterKeyFunc() {
        if(checkExistenceMasterKey()){
            txEnterRegMasterKey.setText("Enter MasterKey");
            btEnterRegMasterKey.setText("Enter");
        } else {
            txEnterRegMasterKey.setText("Create MasterKey");
            btEnterRegMasterKey.setText("Create");
        }
    }

    /**
     * Функция, которая:
     * 1) Создает мастер пароль, если он не существует
     * 2) Открывает 2-ое окно, если мастер пароль корректный
     * 3) Вызывает alert о неверном мастер пароле, если он некорректный
     */
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

    /**
     * Функция, которая выводит сообщение о неверном мастер пароле
     */
    private void alertWrongPass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Неверный MasterKey!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
}