package com.example.medihelp;

public class DoctorData {
    private String name;

    private String email;
    private String password;
    private String speciality;
    private String location;
    private String picture;

    public DoctorData() {
    }

    public DoctorData(String name, String email, String password, String speciality, String location, String picture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.speciality = speciality;
        this.location = location;
        this.picture = picture;
    }

    public DoctorData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
