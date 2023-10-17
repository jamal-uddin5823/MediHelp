package com.example.medihelp;

import java.util.ArrayList;

public class Doctors {
    public static final ArrayList<Doctor> arrayList= new ArrayList<Doctor>(){
        {
            add(new Doctor(1, "Dr. X", "Cardiologist", "Dhanmondi, Dhaka", "+8801714368748",false));
            add(new Doctor(2, "Dr. Y", "Oncologist", "Banani, Dhaka", "+8801714268748",false));
        }
    };
}
