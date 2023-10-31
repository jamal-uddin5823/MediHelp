package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.*;

public class StartActivity extends AppCompatActivity {
    TextView btnGetStarted;
    public static LottieAnimationView lottieanim;
    Handler handler;
    private ProgressBar startProgressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));

        handler = new Handler();



        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();

//        userDataRetrieval.retrieveUserData(user -> {
//            if(MainActivity.currentUserData!=null) {
//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                Intent intent = new Intent(this,Login.class);
//                startActivity(intent);
//                finish();
//            }
//
//        });


        startProgressBar = findViewById(R.id.startProgressBar);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        lottieanim=findViewById(R.id.animation);
        lottieanim.playAnimation();

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

//package com.example.medihelp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.animation.Animator;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.airbnb.lottie.*;
//
//public class StartActivity extends AppCompatActivity {
//    TextView btnGetStarted;
//    public static LottieAnimationView lottieanim;
//    Handler handler;
//    private ProgressBar startProgressBar;
//    SharedPreferences Onboarding;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
//
////        new Handler().postDelayed(new Runnable() {
////            Intent homeIntent;
////            @Override
////            public void run() {
////                Intent onBoardingIntent = new Intent(getApplicationContext(), Login.class);
////                startActivity(onBoardingIntent);
////                Onboarding=getSharedPreferences("Onboarding",MODE_PRIVATE);
////                finish();
////            }
////        },3000);
//
//        //prolly jamal
//
////        btnGetStarted.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                // Show the ProgressBar when the button is clicked
//////                startProgressBar.setVisibility(View.VISIBLE);
////=======
////        handler = new Handler();
////>>>>>>> e3f21c3d522178ae6251bc17e5c40d06e65f3a4c
////
////
////
////        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();
////
////        userDataRetrieval.retrieveUserData(user -> {
////            if(MainActivity.currentUserData!=null) {
////                Intent intent = new Intent(this,MainActivity.class);
////                startActivity(intent);
////                finish();
////            } else {
////
////                Intent intent = new Intent(this,Login.class);
////                startActivity(intent);
////                finish();
////            }
////
////        });
//
//
//        startProgressBar = findViewById(R.id.startProgressBar);
////        btnGetStarted = findViewById(R.id.btnGetStarted);
//        lottieanim=findViewById(R.id.animation);
//        lottieanim.playAnimation();
//
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
//
//
//
//    }
//}