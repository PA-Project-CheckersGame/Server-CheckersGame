package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket = new Socket();

    public ClientThread(Socket socket){
        this.socket = socket;
    }

    public void run(){
        String request = "";
        String response = "";

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            StopServerThread stopServerThread = new StopServerThread(out);
            stopServerThread.start();

            do{
                request = in.readLine();
                System.out.println("Am primit de la client: " + request);
                if(request.contains("login")){
                    if(request.contains("Tavilian")){
                        out.println("login failed username_not_registered");
                        System.out.println("Am trimis: login failed username_not_registered");
                        out.flush();
                    }else if(request.contains("Tavi") && request.contains("12345")){
                        out.println("login failed wrong_password");
                        System.out.println("Am trimis: login failed wrong_password");
                        out.flush();
                    }else if(request.contains("Tavi") && request.contains("1234")){
                        out.println("login ok");
                        System.out.println("Am trimis: login ok");
                        out.flush();
                    }else  if(request.contains("Tavi") && request.contains("pula")){
                        out.println("login failed already_online");
                        out.flush();
                    }

                }
            }while(!request.equals("exit"));
            System.out.println("Client disconnected");

        } catch (IOException ex){
            System.out.println("An error occurred when closing the client socket. Error is: " + ex.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex){
                System.out.println("An error occurred when closing the server socket. Error is: " + ex.getMessage());
            }
        }
    }

}
