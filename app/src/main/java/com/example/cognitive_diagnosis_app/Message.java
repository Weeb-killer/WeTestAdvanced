package com.example.cognitive_diagnosis_app;

public class Message {
    public static String SEND_BY_ME="me";
    public static String SEND_BY_BOT="teacher";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendby() {
        return sendby;
    }

    public void setSendby(String sendby) {
        this.sendby = sendby;
    }

    public Message(String message, String sendby) {
        this.message = message;
        this.sendby = sendby;
    }

    String message;
    String sendby;
}
