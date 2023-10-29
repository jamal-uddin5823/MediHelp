package org.example;

import com.google.gson.Gson;

import java.util.List;

class Message{
    private String role;
    private String content;
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String messages) {
        this.content = "You are an AI assistant that is an expert in medical health. You know about symptoms and signs of various types of illnesses. Recommend the medical history queries with which specialist they should consult.  Format any lists on individual lines with a dash and a space in front of each line.\\nMy symptoms are "+messages+"Just give one word answer.";
    }

}

class APIRequestBody {
    private String model;
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

public class Main{
    public static void main(String[] args) {
        String symptoms="asthma";
        Message mes = new Message();
        mes.setRole("user");
        mes.setContent(symptoms);
        List<Message> list = new List<Message>;
        list.add(mes);

        APIRequestBody req = new APIRequestBody();
        req.setModel("mistralai/mistral-7b-instruct");
        req.setMessages(list);

        Gson gson=new Gson();
        String jsonRequest = gson.toJson(req);
    }
}