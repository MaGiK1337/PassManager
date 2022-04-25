package com.company;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JavaToSql javaToSql = new JavaToSql();
        Saver saver = new Saver();
        javaToSql.SearchDriver();
        javaToSql.TestConnection();
        //javaToSql.AddToSQL();
        //javaToSql.getDeleteFromSQL();
        //javaToSql.UpdatePassSQL();
        //saver.saveOurFile();
        //saver.checkInFile();
        //javaToSql.checkMasterPassword();
        //javaToSql.addMasterKey();
        //javaToSql.takeAllTable();

    }

}