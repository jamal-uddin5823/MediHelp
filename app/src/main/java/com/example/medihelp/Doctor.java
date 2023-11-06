package com.example.medihelp;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarkeddoc")
public class Doctor {
    @PrimaryKey()
    @NonNull
    private String ID;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "speciality")
    private String speciality;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "contact")
    private String contact;

    @ColumnInfo(name="picture")
    private String picture;

    public Doctor(@NonNull String ID, String name, String speciality, String location, String contact,String picture) {
        this.ID = ID;
        this.name = name;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
        this.picture = picture;
    }

    @Ignore
    public Doctor(String name, String speciality, String location, String contact) {
        this.name = name;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
    }


//    @Ignore
//    public Doctor(String name, String speciality, String location, String contact, String picture) {
//        this.name = name;
//        this.speciality = speciality;
//        this.location = location;
//        this.contact = contact;
//        this.picture = picture;
//    }

    @Ignore
    public Doctor() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
