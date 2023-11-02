package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

        Intent intent = getIntent();
        String role = intent.getStringExtra("role");

        assert role != null;
        Log.d("HEEEYYY",role);

        if(role.equals("patient")) {
            initPatient();
        } else if(role.equals("doctor")) {
            initDoctor();
        }

//        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();
//
//        userDataRetrieval.retrieveUserData(user -> {
//            if(MainActivity.currentUserData!=null) {
//
//                new Handler().postDelayed(new Runnable() {
//                    Intent homeIntent;
//                    @Override
//                    public void run() {
//                        Intent onBoardingIntent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(onBoardingIntent);
//                        finish();
//                    }
//                },3000);
//
////                Intent intent = new Intent(this,MainActivity.class);
////                startActivity(intent);
////                finish();
//            } else {
//
//                new Handler().postDelayed(new Runnable() {
//                    Intent homeIntent;
//                    @Override
//                    public void run() {
//                        Intent onBoardingIntent = new Intent(getApplicationContext(), Login.class);
//                        startActivity(onBoardingIntent);
//                        finish();
//                    }
//                },3000);
//
////                Intent intent = new Intent(this,Login.class);
////                startActivity(intent);
////                finish();
//            }
//
//        });
//
//
//        startProgressBar = findViewById(R.id.startProgressBar);
////        btnGetStarted = findViewById(R.id.btnGetStarted);
//        lottieanim=findViewById(R.id.animation);
//        lottieanim.playAnimation();

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

    private void initPatient() {
        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();

        userDataRetrieval.retrieveUserData(user -> {
            MainActivityDoctor.currentDoctorData=null;
            if(MainActivity.currentUserData !=null) {

                new Handler().postDelayed(new Runnable() {
                    Intent homeIntent;
                    @Override
                    public void run() {
                        Intent onBoardingIntent = new Intent(getApplicationContext(), MainActivity.class);
                        onBoardingIntent.putExtra("role","patient");
                        startActivity(onBoardingIntent);
//                        finish();
                    }
                },3000);

//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
//                finish();
            } else {

                new Handler().postDelayed(new Runnable() {
                    Intent homeIntent;
                    @Override
                    public void run() {
                        Intent onBoardingIntent = new Intent(getApplicationContext(), Login.class);
                        onBoardingIntent.putExtra("role","patient");
                        startActivity(onBoardingIntent);
//                        finish();
                    }
                },3000);

//                Intent intent = new Intent(this,Login.class);
//                startActivity(intent);
//                finish();
            }

        });


        startProgressBar = findViewById(R.id.startProgressBar);
//        btnGetStarted = findViewById(R.id.btnGetStarted);
        lottieanim=findViewById(R.id.animation);
        lottieanim.playAnimation();
    }

    private void initDoctor() {
        DoctorDataRetrieval doctorDataRetrieval = new DoctorDataRetrieval();

        doctorDataRetrieval.retrieveDoctorData(user -> {
            MainActivity.currentUserData=null;
            if(MainActivityDoctor.currentDoctorData!=null) {

                new Handler().postDelayed(new Runnable() {
                    Intent homeIntent;
                    @Override
                    public void run() {
                        Intent onBoardingIntent = new Intent(getApplicationContext(), MainActivityDoctor.class);
                        onBoardingIntent.putExtra("role","doctor");
                        startActivity(onBoardingIntent);
//                        finish();
                    }
                },3000);

//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
//                finish();
            } else {

                new Handler().postDelayed(new Runnable() {
                    Intent homeIntent;
                    @Override
                    public void run() {
                        Intent onBoardingIntent = new Intent(getApplicationContext(), LoginDoctorActivity.class);
                        onBoardingIntent.putExtra("role","doctor");
                        startActivity(onBoardingIntent);
//                        finish();
                    }
                },3000);

//                Intent intent = new Intent(this,Login.class);
//                startActivity(intent);
//                finish();
            }

        });


        startProgressBar = findViewById(R.id.startProgressBar);
//        btnGetStarted = findViewById(R.id.btnGetStarted);
        lottieanim=findViewById(R.id.animation);
        lottieanim.playAnimation();
    }
}