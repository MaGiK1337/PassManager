package com.company.DB;

import com.company.userdata.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;


public class JavaToSql {

    private static final String URL = "jdbc:mysql://localhost:3306/userData";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "kN2&nAb78QyT!";

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    public void SearchDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void TestConnection() {
            try {
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public boolean AddToSQL(String text, String inputLoginText, String inputPasswordText){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO passwords(resourceName, userLogin, userPassword) VALUES(?,?,?)");
            new Saver().checkInFile(inputPasswordText);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, inputLoginText);
            preparedStatement.setString(3, inputPasswordText);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try{
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void UpdatePasswordSQL(int id, String source, String login, String password){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE passwords SET userPassword = ?, resourceName = ?, userLogin = ? WHERE Id = ? ");
            preparedStatement.setInt(4,id);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, source);
            preparedStatement.setString(3, login);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFromSQL(int id){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM passwords WHERE Id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String checkMasterPassword() {
        String check = null;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM masterPassword");
            resultSet.next();
            check = resultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (check == null){
            check = "";
        }
        return check;
    }

    public void addMasterKey(String masterKey){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO masterpassword(userMasterPassword) VALUES(?)");
            preparedStatement.setString(1, masterKey);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public ObservableList<UserData> takeAllTable(){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM passwords");

            final ObservableList<UserData> items = FXCollections.observableArrayList();


            while (resultSet.next()){
                items.add(new UserData(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }

    }
}









