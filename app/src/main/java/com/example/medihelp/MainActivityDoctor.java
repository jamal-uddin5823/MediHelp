package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;




public class MainActivityDoctor extends AppCompatActivity {


    private static final String TAG = "MainActivityDoctor";
    TextView welcomeTextView;
    ImageView imgProfileHome;

    SwitchCompat switchmode;
    boolean isNightmode;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    Button btnDoctorDetails,btnAboutUs;
    Button btnLogout;

    TextView tvUserName;
    ImageView imgProfileFrag;


    public static DoctorData currentDoctorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);
//        finish();

        Log.d(TAG,"CREATEDDDDDDDD");

        isNightmode = isSystemInDarkMode();
        if(isNightmode)
            Log.d("Theme","Dark");
        else
            Log.d("Theme","Light");


        welcomeTextView = findViewById(R.id.welcome);
        imgProfileHome = findViewById(R.id.imgProfileHome);
        btnDoctorDetails = findViewById(R.id.btnDoctorDetails);
        btnAboutUs = findViewById(R.id.btn_aboutUs);

        switchmode = findViewById(R.id.switchMode);
        btnLogout = findViewById(R.id.btn_logout);
        showName(welcomeTextView);


//        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
//        int thumbDrawable = isSystemInDarkMode() ? R.drawable.thumb_dark : R.drawable.thumb_light;
//
//        // Set the thumb and track drawables
//        switchmode.setThumbResource(thumbDrawable);
//        switchmode.setChecked(isNightmode);
//        isNightmode = sharedPreferences.getBoolean("nightmode",false);



        btnAboutUs.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
//            finish();
        });


        btnDoctorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DoctorDetailsActivity.class);
                startActivity(intent);
                Toast.makeText(view.getContext(),"Showing doctor details",Toast.LENGTH_SHORT).show();
//                finish();
            }
        });


//        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);



        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        isNightmode = sharedPreferences.getBoolean("nightmode",false);

        if(isNightmode) {
            switchmode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switchmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNightmode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightmode",false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightmode",true);
                }
                editor.apply();

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Logging out",Toast.LENGTH_SHORT).show();
                Context context = getApplicationContext();
                FirebaseAuth.getInstance().signOut();
                MainActivity.currentUserData = null;
                MainActivityDoctor.currentDoctorData = null;
                Intent intent = new Intent(context, LoginDoctorActivity.class);

//                Log.d("Hllo","Redreictb=ing to lgin");

                startActivity(intent);
                finish();

//                MainActivity.currFragment="Home";
//                MainActivity.currentUserData=null;
//                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    void showName(TextView textView) {
        if (MainActivityDoctor.currentDoctorData == null) {
            DoctorDataRetrieval doctorDataRetrieval = new DoctorDataRetrieval();


            doctorDataRetrieval.retrieveDoctorData(new DoctorDataRetrieval.OnDoctorDataReceivedListener() {
                @Override
                public void onDoctorReceived(DoctorData user) {
                    if (user != null) {
                        // The user object contains the current user's data
                        String userName = user.getName();
                        String imageUrl = user.getPicture();
                        String userEmail = user.getEmail();
                        textView.setText("Welcome \n" + userName);
                        Picasso.get().load(imageUrl).into(imgProfileHome);
//                        Log.d(TAG, "onUserReceived: " +userName);
                        // ... and so on
                    } else {
                        // Handle the case where the user is not found or not authenticated
                        Log.d(TAG, "No welcome");
                    }
                }
            });

        } else {
            textView.setText("Welcome \n" + MainActivityDoctor.currentDoctorData.getName());
            Picasso.get().load(MainActivityDoctor.currentDoctorData.getPicture()).into(imgProfileHome);
        }
    }

    private boolean isSystemInDarkMode() {
        int theme =  getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        return theme == Configuration.UI_MODE_NIGHT_YES;
    }


}