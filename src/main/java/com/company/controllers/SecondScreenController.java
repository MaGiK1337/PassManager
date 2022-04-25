package com.company.controllers;

import com.company.JavaToSql;
import com.company.controllers.exchanger.ExchangerData;
import com.company.generator.PasswordGenerator;
import com.company.userdata.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.company.HelloApplication.mainStage;
import static java.lang.Integer.parseInt;

public class SecondScreenController implements Initializable {

    @FXML
    private TableColumn<UserData, String> bt1Column;

    @FXML
    private TableColumn<UserData, String> bt2Column;

    @FXML
    private TableColumn<UserData, Integer> idColumn;

    @FXML
    private TableColumn<UserData, String> loginColumn;

    @FXML
    private TableColumn<UserData, String> passwordColumn;

    @FXML
    private TableColumn<UserData, String> sourceColumn;

    @FXML
    private TableView<UserData> tablePasswords;

    @FXML
    private Button btAddUserData;

    @FXML
    private Button btGeneratePassword;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputSizeGeneratedPassword;

    @FXML
    private TextField inputSource;

    @FXML
    private TextField txGeneratedPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindButtons();
        showListOfPasswords();
        bindColumns();
    }

    private final ObservableList<UserData> items = FXCollections.observableArrayList();

    private void addUserData(String text, String inputLoginText, String inputPasswordText){
        // TODO: 15.03.2022 Добавление ресурс/логин/пароль в бд
        JavaToSql javaToSql = new JavaToSql();
        javaToSql.SearchDriver();
        javaToSql.TestConnection();
        javaToSql.AddToSQL(text, inputLoginText, inputPasswordText);
        boolean isSuccess = false;
        if(isSuccess){
            alertMess("Данные успешно добавлены!");
        } else {
            alertMess("Ваш пароль присутствует в слитых базах данных!\nПридумайте другой пароль.");
        }
    }

    private void alertMess(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private void showListOfPasswords(){
        // TODO: 15.03.2022 Вывод на экран список паролей
        bindData();
        tablePasswords.setItems(items);
    }

    private void bindButtons() {
        btAddUserData.setOnAction(event -> {
            if(!inputLogin.getText().isEmpty() && !inputPassword.getText().isEmpty() && !inputSource.getText().isEmpty())
                addUserData(inputSource.getText(), inputLogin.getText(), inputPassword.getText());
        });
        btGeneratePassword.setOnAction(event -> {
            if(!inputSizeGeneratedPassword.getText().isEmpty())
                generatePassword( parseInt( inputSizeGeneratedPassword.getText() ) );
            else
                generatePassword(8);
        });
    }

    private void generatePassword(int sizePass) {

        if(sizePass > 7 && sizePass < 41){
            PasswordGenerator pg = new PasswordGenerator();
            String generatedPass = pg.generatePassword(sizePass);
            txGeneratedPassword.setText(generatedPass);
        }
    }

    private void bindColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        bindButtonsInColumns();

    }

    private void bindButtonsInColumns() {
        bt1Column.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        bt1Column.setCellFactory(cellFactory("Обновить"));

        bt2Column.setCellValueFactory(new PropertyValueFactory<>("DUMMY2"));
        bt2Column.setCellFactory(cellFactory("Удалить"));

    }

    private Callback<TableColumn<UserData, String>, TableCell<UserData, String>> cellFactory(String nameBt){
        return new Callback<>() {
            @Override
            public TableCell call(final TableColumn<UserData, String> param) {
                return new TableCell<UserData, String>() {

                    final Button btn = new Button(nameBt);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            btn.setOnAction(event -> {
                                int id = this.getIndex();

                                if(Objects.equals(nameBt, "Обновить")){
                                    // TODO: 17.04.2022 Создать окно для обновления данных
                                    try {
                                        ExchangerData.item = items.get(id);
                                        changeWindow();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if(Objects.equals(nameBt, "Удалить")){
                                    deleteDataById(id);
                                    alertMess(nameBt + " " + id);
                                }
                            });
                            setGraphic(btn);
                        }
                        setText(null);
                    }
                };
            }
        };
    }

    private void deleteDataById(int id) {
        // TODO: 17.04.2022 Удаление данных по ID
    }

    private void bindData() {
        for (int i = 0; i < 5; i++){
            items.add(new UserData(i, "kek_" + i, "lol_" + i, "azaz_" + i));
        }
    }

    private void changeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/company/update.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        mainStage.setScene(scene);
        mainStage.show();
    }

}

