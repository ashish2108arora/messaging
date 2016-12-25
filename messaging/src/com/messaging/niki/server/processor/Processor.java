package com.messaging.niki.server.processor;

import com.messaging.niki.client.request.Request;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class Processor extends Thread {

    private Socket clientSocket;

    HashMap<String,Socket> clientSocketMap;

    public Processor(Socket clientSocket, HashMap<String,Socket> clientSocketMap) {
        this.clientSocket = clientSocket;
        this.clientSocketMap = clientSocketMap;
    }

    @Override
    public void run() {
        Request request = null;
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while ((request = (Request)in.readObject()) != null) {
                if (request.getRecipientIDs() == null) {
                    clientSocketMap.put(request.getSenderID(), clientSocket);
                    System.out.println("Registration successful of client :" + request.getSenderID());
                }
                else {
                    System.out.println("Received the following request :" + request.toString());
                    List<String> recipientsIds = request.getRecipientIDs();
                    for(String recipientId : recipientsIds) {
                        Socket recipientSocket = clientSocketMap.get(recipientId);
                        PrintWriter out =
                                new PrintWriter(recipientSocket.getOutputStream(), true);
                        out.println(request.getMessage());
                        System.out.println("Sent to  :" + request.getRecipientIDs() + "from :" + request.getSenderID());
                    }
                }

                System.out.println("Waiting for more messages from :" + request.getSenderID());

            }

            System.out.println("Server Not Listening anymore from requests from :" + request.getSenderID());

        }catch (Exception ex) {
            System.out.println("Exception while processing the following request :" +
             request == null ? "request not initialized " : request.toString() + ex.getMessage());
        }
    }
}
