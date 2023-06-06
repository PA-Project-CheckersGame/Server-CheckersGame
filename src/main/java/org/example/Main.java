package org.example;

import java.sql.*;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/proiectjava";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "java";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

        } catch (SQLException e) {
            e.printStackTrace();
        }

        GameServer server = new GameServer();
    }
}
