package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.*;

public class StartActivity extends AppCompatActivity {
    TextView btnGetStarted;
    LottieAnimationView lottieanim;
    private ProgressBar startProgressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));

        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();

        userDataRetrieval.retrieveUserData(user -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });


        startProgressBar = findViewById(R.id.startProgressBar);
//        btnGetStarted = findViewById(R.id.btnGetStarted);
        lottieanim=findViewById(R.id.animation);
        lottieanim.playAnimation();

//        btnGetStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Show the ProgressBar when the button is clicked
////                startProgressBar.setVisibility(View.VISIBLE);
//
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });



    }
}