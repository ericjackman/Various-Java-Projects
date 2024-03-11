// Eric Jackman

import java.net.*;
import java.io.*;

public class Dispatcher {
    public static void main(String[] args) {
        ServerSocket server;
        Socket socket;
        try {
            server = new ServerSocket(8775);
            System.out.println("Waiting for client");
            while (true) {  // loop until a client connects
                socket = server.accept();
                ServerThread servThread = new ServerThread(socket);  // create a thread to handle client
                servThread.start();
                System.out.println("Connection successful");
            }
        } catch (IOException e) {
        }
    }
}
