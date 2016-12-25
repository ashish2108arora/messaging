package com.messaging.niki.client.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class ClientReceiver implements Runnable {

    private Socket clientSocket;

    public ClientReceiver(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        BufferedReader in =
                null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
