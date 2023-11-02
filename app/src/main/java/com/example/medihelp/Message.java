package com.example.medihelp;

public class Message{
private String role="user";
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
        String age="25";
        String gender = "any";
        String weight = "56";
        if(MainActivity.currentUserData.getAge()!=null) {
            age = MainActivity.currentUserData.getAge();
            gender =MainActivity.currentUserData.getGender();
            weight = MainActivity.currentUserData.getWeight();
        }


        this.content = "You are an AI assistant that is an expert in medical health. You know about symptoms and signs of various types of illnesses. Recommend the medical history queries with which specialist they should consult. Format any lists on individual lines with a dash and a space in front of each line. I am a " + age + " year old " + gender + ". My weight is "+ weight+" kgs. My symptoms are " + messages + ". Just give one word answer. No additional word";
    }

}
