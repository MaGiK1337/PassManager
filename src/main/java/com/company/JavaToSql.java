package com.company;

import java.sql.*;
import java.util.Scanner;

public class JavaToSql {

    private static final String URL = "jdbc:mysql://localhost:3306/userData";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "kN2&nAb78QyT!";

    private static Scanner sc = new Scanner(System.in);
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    public void SearchDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void TestConnection() {
            try {
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                if (!connection.isClosed()) {
                    System.out.println("Connection successful");
                } else {
                    System.out.println("Connection failure");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void AddToSQL(String text, String inputLoginText, String inputPasswordText){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO passwords(resourceName, userLogin, userPassword) VALUES(?,?,?)");
            String newResourceName = text;
            String newLoginName = inputLoginText;
            String newPassword = inputPasswordText;
            preparedStatement.setString(1, newResourceName);
            preparedStatement.setString(2, newLoginName);
            preparedStatement.setString(3, newPassword);
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

    public void UpdatePasswordSQL(){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE passwords SET userPassword = ? WHERE Id = ?");
            int id = sc.nextInt();
            String newPassword = sc.next();
            preparedStatement.setInt(2,id);
            preparedStatement.setString(1,newPassword);
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

    public void deleteFromSQL(){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM passwords WHERE Id = ?");
            int id = sc.nextInt();
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
            System.out.println(check);
        }
        return check;
    }

    public void addMasterKey(){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO masterpassword(userMasterPassword) VALUES(?)");
            String newMasterPassword = sc.next();
            preparedStatement.setString(1, newMasterPassword);
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
    public void takeAllTable(){
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM passwords");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1) + resultSet.getString(2) + resultSet.getString(3) + resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}









