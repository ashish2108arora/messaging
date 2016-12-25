package com.messaging.niki.client;

import com.messaging.niki.client.receiver.ClientReceiver;
import com.messaging.niki.client.request.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class Client {

    public static void main  (String [] args) {

        if (args.length != 2) {
            System.err.println("Usage: HostName and PortNumber are mandatory");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        System.out.println("trying to connect to" + hostName + ":" + portNumber);

        try{
            Socket echoSocket = new Socket(hostName, portNumber);
            System.out.println("connected to" + hostName + ":" + portNumber);

            //Writing to server socket
            ObjectOutputStream out = new ObjectOutputStream(echoSocket.getOutputStream());

            //Reading from standard inputstream to send messages
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            //Reading from server socket
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));


            System.out.println("Enter the senderID with which you want to register");
            String senderId = stdIn.readLine();
            System.out.println("trying to register :" + senderId + " to "+ hostName + ":" + portNumber);

            //First registration request
            Request registrationRequest = new Request();
            registrationRequest.setSenderID(senderId);
            out.writeObject(registrationRequest);
            System.out.println(senderId + " successfully registered to" + hostName + ":" + portNumber);

            System.out.println(senderId + " Creating a new thread to listen messages from " + hostName + ":" + portNumber);
            new Thread(new ClientReceiver(echoSocket)).start();

            System.out.println("Enter a space separated list of recipientIDs");
            List<String> recipientList = Arrays.asList(stdIn.readLine().split(" "));

            String userInput;
            System.out.println("Enter message");
            while ((userInput = stdIn.readLine()) != null) {
                Request messageRequest = new Request();
                messageRequest.setSenderID(senderId);
                messageRequest.setMessage(userInput);
                messageRequest.setRecipientIDs(recipientList);
                out.writeObject(messageRequest);
            }
        } catch (Exception ex) {
            System.out.println("Exception while creating client :" + ex.getMessage());
        }
    }
}
