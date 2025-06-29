package com.lagunabreezelodge.db;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:lagunabreezelodge.db";


    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }
}