package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class SelectUserTypeActivity extends AppCompatActivity {

    CardView userDoctor,userPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        userDoctor = findViewById(R.id.user_doctor);
        userPatient = findViewById(R.id.user_patient);

        userPatient.setOnClickListener(view -> {
            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra("role","patient");
            startActivity(intent);
        });

        userDoctor.setOnClickListener(view -> {
            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra("role","doctor");
            startActivity(intent);
        });

    }
}