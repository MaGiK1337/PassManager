package com.company.userdata;

/**
 * Класс, который хранит данные пользователя
 */
public class UserData{
    private int id;
    private String source, login, password;

    /**
     *  Конструктор данного класса
     * @param id
     * @param source
     * @param login
     * @param password
     */
    public UserData(int id, String source, String login, String password) {
        this.id = id;
        this.source = source;
        this.login = login;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
