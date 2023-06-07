package org.example;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/proiectjava";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "java";


    public List<Player> getDataBaseValues() {

        String query = "SELECT * FROM players;";

        List<Player> playersList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                playersList.add(new Player(resultSet.getString("PlayerName"), resultSet.getString("PlayerPassword"), resultSet.getBoolean("PlayerIsOnline"), resultSet.getString("PlayerStatus")));
            }

            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playersList;
    }

    public void insertNewPlayerIntoDB(String username, String password) {
        String query = "INSERT INTO players (PlayerName, PlayerPassword, PlayerIsOnline, PlayerStatus) VALUES (?, ?, ?, ?);";
        System.out.println(query);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setBoolean(3, true);
            statement.setString(4, "online");

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            // Get the generated keys
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int playerId = generatedKeys.getInt(1);
                System.out.println("Generated player ID: " + playerId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loginPlayer(String isOnline, String username) {
        String query = "UPDATE players SET PlayerIsOnline = ? WHERE PlayerName = ?;";
        System.out.println(query);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            int isOnlineValue = isOnline.equals("true") ? 1 : 0;

            statement.setInt(1, isOnlineValue);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(String status, String username) {
        String query = "UPDATE players SET PlayerStatus = ? WHERE PlayerName = ?;";
        System.out.println(query);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


