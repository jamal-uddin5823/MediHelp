package com.example.medihelp;

public class User {

    private String name, email, password, age, weight, BloodGroup,gender, picture;

    private boolean isDoctor;


    public User() {
        this.isDoctor=false;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isDoctor=false;
    }

    public User(String name, String email, String password, String age, String weight, String bloodGroup, String gender, String picture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        BloodGroup = bloodGroup;
        this.gender = gender;
        this.picture = picture;
        this.isDoctor=false;
    }

    public User(String name, String email, String age, String gender, String weight, String BloodGroup) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.BloodGroup = BloodGroup;
        this.isDoctor=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.BloodGroup = bloodGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean getDoctorStatus() { return isDoctor;}

    public void setDoctorStatus(boolean doctor) { isDoctor = doctor;}
}
