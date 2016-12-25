package com.messaging.niki.server;

import com.messaging.niki.server.processor.Processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class Server {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: Port Number is mandatory");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket =
                new ServerSocket(portNumber);
        HashMap<String,Socket> clientSocketMap = new HashMap<String,Socket>();
        while (true) {
            try {
                System.out.println("Server listening on port " + portNumber );
                Socket clientSocket = serverSocket.accept();

                new Processor(clientSocket,clientSocketMap).start();

            } catch (Exception e) {
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}
