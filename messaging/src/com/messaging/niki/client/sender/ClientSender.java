package com.messaging.niki.client.sender;

import com.messaging.niki.client.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class ClientSender implements Runnable {

    private Socket clientSocket;

    private String senderId;

    private String recipientId;

    public ClientSender(Socket clientSocket, String senderId, String recipientId) {
        this.clientSocket = clientSocket;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    @Override
    public void run() {
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        try {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                Request messageRequest = new Request();
                messageRequest.setSenderID(senderId);
                messageRequest.setMessage(userInput);
                messageRequest.setRecipientIDs(null);
                System.out.println(messageRequest.toString());
                out.writeObject(messageRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
