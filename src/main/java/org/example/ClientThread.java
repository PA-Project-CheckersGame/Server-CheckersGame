package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.List;

import static java.lang.System.out;

public class ClientThread extends Thread {
    private Socket socket = new Socket();

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/proiectjava";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "java";

    private DataBase dataBase = new DataBase();

    public void run() {
        String request = "";
        String response = "";

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            StopServerThread stopServerThread = new StopServerThread(out);
            stopServerThread.start();

            do {
                request = in.readLine();
                System.out.println("Am primit de la client: " + request);
                if (request.contains("login")) {

                    String[] words = request.split(" "); // Separatorul este spațiul

                    String username = words[1]; // "username"
                    String password = words[2]; // "password"

                    String loginResponse = handleLoginRequest(username, password);
                    out.println(loginResponse);
                    out.flush();

                }
                if(request.contains("register")) {

                    String[] words = request.split(" "); // Separatorul este spațiul

                    String username = words[1]; // "username"
                    String password = words[2]; // "password"

                    String registerResponse = handleRegisterRequest(username, password);
                    out.println(registerResponse);
                    out.flush();

                }
                if(request.contains("logout")) {

                    String[] words = request.split(" "); // Separatorul este spațiul

                    String username = words[1]; // "username"

                    String logoutResponse = handleLogoutRequest(username);
                    out.println(logoutResponse);
                    out.flush();

                }

            } while (!request.equals("exit"));
            System.out.println("Client disconnected");

        } catch (IOException ex) {
            out.println("An error occurred when closing the client socket. Error is: " + ex.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                out.println("An error occurred when closing the server socket. Error is: " + ex.getMessage());
            }
        }
    }

    public String handleLoginRequest(String username, String password) {
        List<Player> playersList = dataBase.getDataBaseValues();


        int index = -1;
        for (Player player : playersList) {
            if (player.getPlayerName().equals(username)) {
                index = playersList.indexOf(player);
            }
        }
        if(index == -1) {
            System.out.println("Am trimis: login failed username_not_registered");
            return "login failed username_not_registered";
        }
         else {
            if (!playersList.get(index).getPlayerPassword().equals(password)) {
                System.out.println("Am trimis: login failed wrong_password");
                return "login failed wrong_password";
            } else if (playersList.get(index).getPlayerName().equals(username) && playersList.get(index).getPlayerPassword().equals(password) && playersList.get(index).isPlayerIsOnline()) {
                System.out.println("Am trimis: login failed already_online");
                return "login failed already_online";
            } else if (playersList.get(index).getPlayerName().equals(username) && playersList.get(index).getPlayerPassword().equals(password) && !playersList.get(index).isPlayerIsOnline()) {
                dataBase.loginPlayer("true", username);
                dataBase.changeStatus("online", username);
                System.out.println("Am trimis: login ok");
                return"login ok";
            }
         }
         return "login ok";
    }

    public String handleRegisterRequest(String username, String password) {
        List<Player> playersList = dataBase.getDataBaseValues();

        int index = -1;
        for (Player player : playersList) {
            if (player.getPlayerName().equals(username)) {
                index = playersList.indexOf(player);
            }
        }
        if(index != -1) {
            System.out.println("Am trimis: register failed username_already_exists");
            return "register failed username_already_exists";

        } else {
            dataBase.insertNewPlayerIntoDB(username, password);
            dataBase.changeStatus("online", username);
            System.out.println("Am trimis: register ok");
            return "register ok";
        }
    }

    public String handleLogoutRequest(String username) {

            dataBase.loginPlayer("false", username);
            dataBase.changeStatus("offline", username);
            System.out.println("Am trimis: logout ok");
            return "logout ok";

    }
}
