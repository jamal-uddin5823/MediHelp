package com.example.medihelp;

public class Developer {
    private String Name;
    private String email;
    private Integer image;

    public Developer(String name, String email, Integer image) {
        Name = name;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}