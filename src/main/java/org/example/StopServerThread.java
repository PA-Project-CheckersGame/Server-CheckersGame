package org.example;

import java.io.PrintWriter;
import java.util.Scanner;

public class StopServerThread extends Thread {
    PrintWriter out = null;

    public StopServerThread(PrintWriter out) {
        this.out = out;
    }
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if(command.equals("stop")) {
            System.out.println("Stopping the server...");
            out.println("Server stopped...");
            out.flush();
            System.exit(0);
        }
    }
}
