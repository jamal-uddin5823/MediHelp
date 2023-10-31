package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.*;

public class StartActivity extends AppCompatActivity {
    TextView btnGetStarted;
    LottieAnimationView lottieanim;
    private ProgressBar startProgressBar;
    SharedPreferences Onboarding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startProgressBar = findViewById(R.id.startProgressBar);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        lottieanim=findViewById(R.id.animation);
        lottieanim.playAnimation();

        new Handler().postDelayed(new Runnable() {
            Intent homeIntent;
            @Override
            public void run() {
                Intent onBoardingIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(onBoardingIntent);
                Onboarding=getSharedPreferences("Onboarding",MODE_PRIVATE);
                finish();
            }
        },3000);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the ProgressBar when the button is clicked
//                startProgressBar.setVisibility(View.VISIBLE);

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });



    }
}