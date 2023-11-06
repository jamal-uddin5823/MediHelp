package com.example.medihelp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AlreadyDoctorActivity extends AppCompatActivity {

    private Button editButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_doctor);

        editButton = findViewById(R.id.editButton);
        backButton = findViewById(R.id.backButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlreadyDoctorActivity.this, DoctorDetailsActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlreadyDoctorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

