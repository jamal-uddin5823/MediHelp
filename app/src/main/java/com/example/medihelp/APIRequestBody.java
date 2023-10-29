package com.example.medihelp;

import java.util.List;

public class APIRequestBody {
    private String model="mistralai/mistral-7b-instruct";
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}


