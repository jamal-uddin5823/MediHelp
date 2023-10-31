package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class AboutUs extends AppCompatActivity {

    RecyclerView rvAboutUs;

    final ArrayList<Developer> developers = new ArrayList<>(Arrays.asList(
            new Developer("Jamal Uddin Mallick","jamaluddin10775@gmail.com",(Integer) R.drawable.jamal)
            ,new Developer("Tasnia Iffat", "tasniaiffat@gmail.com",(Integer) R.drawable.tasnia)
            ,new Developer("Afia Lubaina", "lubainafia@gmail.com",(Integer) R.drawable.lubaina)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));


        rvAboutUs = findViewById(R.id.rvAboutUs);
        rvAboutUs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DeveloperRecyclerAdapter adapter = new DeveloperRecyclerAdapter(this,developers);
        rvAboutUs.setAdapter(adapter);


    }
}