package com.example.medihelp;

public class User {

    private String name, email, password, age, weight, BloodGroup,gender, picture,userType,doctorId;


    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.doctorId = null;
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
        this.userType = "user";
        this.doctorId = null;
    }

    public User(String name, String email, String age, String gender, String weight, String BloodGroup) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.BloodGroup = BloodGroup;
        this.userType = "user";
        this.doctorId = null;
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


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
