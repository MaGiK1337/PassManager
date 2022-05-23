package com.company.controllers;

import com.company.DB.JavaToSql;
import com.company.DB.Saver;
import com.company.controllers.exchanger.ExchangerData;
import com.company.generator.PasswordGenerator;
import com.company.userdata.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.company.HelloApplication.mainStage;
import static java.lang.Integer.parseInt;

/**
 * Класс, который отвечает за 2-ое окно, которое сохраняет и выводит данные, и генерирует пароль
 */
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

    /**
     * Функция, которая инциализирует данные
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Saver().saveOurFile();
        bindButtons();
        showListOfPasswords();
        bindColumns();
    }

    private ObservableList<UserData> items = FXCollections.observableArrayList();

    /**
     * Функция, которая добавляет пользовательские данные в БД и проверяет компрометацию пароля
     * @param text
     * @param inputLoginText
     * @param inputPasswordText
     */
    private void addUserData(String text, String inputLoginText, String inputPasswordText){
        try {
            JavaToSql javaToSql = new JavaToSql();
            javaToSql.SearchDriver();
            javaToSql.TestConnection();
            if(javaToSql.AddToSQL(text, inputLoginText, inputPasswordText)){
                alertMess("Данные успешно добавлены!");
                showListOfPasswords();
            }else {
                alertMess("Ваш пароль присутствует в слитых базах данных!\nПридумайте другой пароль.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alertMess("Ваш пароль присутствует в слитых базах данных!\nПридумайте другой пароль.");
        }
    }

    /**
     *  Функция, которая вызывает alert с определенным сообщением
     * @param mess
     */
    private void alertMess(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     *  Функция, которая выводит таблицу пользовательских данных
     */
    private void showListOfPasswords(){
        bindData();
        tablePasswords.setItems(items);
    }

    /**
     * Функция, которая устанавливает слушатели нажатий
     */
    private void bindButtons() {
        btAddUserData.setOnAction(event -> {
            if(!inputLogin.getText().isEmpty() && !inputPassword.getText().isEmpty() && !inputSource.getText().isEmpty())
                addUserData(inputSource.getText(), inputLogin.getText(), inputPassword.getText());
        });
        btGeneratePassword.setOnAction(event -> {
            if(!inputSizeGeneratedPassword.getText().isEmpty())
                generatePassword(parseInt( inputSizeGeneratedPassword.getText()));
            else
                generatePassword(8);
        });
    }

    /**
     *  Функция, которая генерирует пароль определенной длины и на вход получает длину пароля
     * @param sizePass
     */
    private void generatePassword(int sizePass) {
        if(sizePass > 7 && sizePass < 41){
            PasswordGenerator pg = new PasswordGenerator();
            String generatedPass = pg.generatePassword(sizePass);
            txGeneratedPassword.setText(generatedPass);
        }
    }

    /**
     *  Функция, которая инциализирует данные таблицы
     */
    private void bindColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        bindButtonsInColumns();
    }

    /**
     * Функция, которая инициализирует кнопки в таблице
     */
    private void bindButtonsInColumns() {
        bt1Column.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        bt1Column.setCellFactory(cellFactory("Обновить"));
        bt2Column.setCellValueFactory(new PropertyValueFactory<>("DUMMY2"));
        bt2Column.setCellFactory(cellFactory("Удалить"));
    }

    /**
     *  Сущность, которая создает custom button для таблицы
     * @param nameBt
     * @return
     */
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
                                    try {
                                        ExchangerData.item = items.get(id);
                                        changeWindow();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if(Objects.equals(nameBt, "Удалить")){
                                    alertMess("Данные удалены");
                                    deleteDataById(items.get(id).getId());
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

    /**
     *  Функция, которая удаляет строку в БД по определенному id
     * @param id
     */
    private void deleteDataById(int id) {
        new JavaToSql().deleteFromSQL(id);
        showListOfPasswords();
    }

    /**
     * Функция, которая обновляет таблицу
     */
    private void bindData() {
        items = new JavaToSql().takeAllTable();
        tablePasswords.getSelectionModel().setCellSelectionEnabled(true);
        tablePasswords.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MenuItem item = new MenuItem("Копировать");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<TablePosition> posList = tablePasswords.getSelectionModel().getSelectedCells();
                StringBuilder clipboardString = new StringBuilder();
                for (TablePosition p : posList) {
                    int r = p.getRow();
                    int c = p.getColumn();
                    if (c == 1){
                        clipboardString.append(items.get(r).getSource());
                    }
                    else if(c == 2){
                        clipboardString.append(items.get(r).getLogin());
                    }else if(c == 3){
                        clipboardString.append(items.get(r).getPassword());
                    }
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        tablePasswords.setContextMenu(menu);
    }

    /**
     * Функция, которая меняет 2-ое окно на 3-е
     * @throws IOException
     */
    private void changeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/company/update.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        mainStage.setScene(scene);
        mainStage.show();
    }
}

