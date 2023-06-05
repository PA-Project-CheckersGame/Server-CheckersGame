package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8069;
    private static ServerSocket serverSocket = null;

    public GameServer() {
        // Creating an endpoint for communication
        try {
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server socket created...");

            System.out.println("Waiting for clients...");
            // accepting the clients in an infinite loop until STOP command
            while (true) {
                // Client's socket
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Execute the client's request in a new thread
                // start -> triggers the method run() from the ClientThread
                new ClientThread(clientSocket).start();
            }

        } catch (IOException e) {
            System.out.println("An error occurred when creating the server socket. Error is: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    System.out.println("An error occurred when closing the server socket. Error is: " + ex.getMessage());
                }
            }
        }
    }

}
