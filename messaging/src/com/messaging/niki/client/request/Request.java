package com.messaging.niki.client.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bluestone on 24-12-2016.
 */
public class Request implements Serializable{

    private static final long serialVersionUID = 1L;

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRecipientIDs() {
        return recipientIDs;
    }

    public void setRecipientIDs(List<String> recipientID) {
        this.recipientIDs = recipientID;
    }

    private String senderID;

    private String message;

    private List<String> recipientIDs;

    @Override
    public String toString() {
        return "Request{" +
                "senderID='" + senderID + '\'' +
                ", message='" + message + '\'' +
                ", recipientIDs=" + recipientIDs +
                '}';
    }

}
