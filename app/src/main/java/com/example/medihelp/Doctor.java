package com.example.medihelp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarkeddoc")
public class Doctor {
    @PrimaryKey()
    private Long ID;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "speciality")
    private String speciality;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "contact")
    private String contact;

    public Doctor(Long ID, String name, String speciality, String location, String contact) {
        this.ID = ID;
        this.name = name;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
    }

    @Ignore
    public Doctor(String name, String speciality, String location, String contact) {
        this.name = name;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
    }
    public Doctor() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
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
}
