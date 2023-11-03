package com.example.medihelp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.medihelp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.grpc.android.BuildConfig;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    public static String currFragment="Home";
    public static User currentUserData;
//    public static DoctorData currentDoctorData;

    //this is tasnia


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        finish();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));

        switch (currFragment.toLowerCase()) {
            case "home":
                replaceFragment(new HomeFragment());
                break;
            case "bookmark":
                replaceFragment(new BookmarksFragment());
                break;
            case "profile":
                replaceFragment(new ProfileFragment());
                break;

        }
//            replaceFragment(new HomeFragment());

        handleBottomNavBar();


    }



    void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    private void handleBottomNavBar() {

        binding.bottomNavigationView.setBackground(null);

//        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currFragment = "Diagnose";
//                replaceFragment(new DiagnoseFragment());
//            }
//        });



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                currFragment = "Home";
                replaceFragment(new HomeFragment());
            }
//            else if (itemId == R.id.search) {
//                currFragment = "Search";
//                replaceFragment(new SearchFragment());
//            }
//            else if (itemId == R.id.blank) {
//                currFragment = "Diagnose";
//                replaceFragment(new DiagnoseFragment());
//            }
            else if (itemId == R.id.bookmark) {
                currFragment = "Bookmark";
                replaceFragment(new BookmarksFragment());
            } else if (itemId == R.id.profile) {
                currFragment = "Profile";
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Handle configuration change if needed
        // You can update your UI accordingly for landscape or portrait mode
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public boolean isProfileFragmentVisible() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        return currentFragment instanceof ProfileFragment;
    }


//    private void handleActionBar() {
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.appbar_layout);
//    }
}