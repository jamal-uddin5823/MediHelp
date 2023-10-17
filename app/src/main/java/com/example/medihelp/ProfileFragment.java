package com.example.medihelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.res.Configuration;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;


public class ProfileFragment extends Fragment {
    View view;
    SwitchCompat switchmode;
    boolean isNightmode;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnUserDetails;
    Button btnLogout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isNightmode = isSystemInDarkMode();
        if(isNightmode)
            Log.d("Theme","Dark");
        else
            Log.d("Theme","Light");

        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        }

    }

    private void updateTheme(boolean isNightMode) {

        if (isNightmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnUserDetails = view.findViewById(R.id.btnUserDetails);

        switchmode = view.findViewById(R.id.switchMode);

        // Check the system theme and set the appropriate drawables
        int thumbDrawable = isSystemInDarkMode() ? R.drawable.thumb_dark : R.drawable.thumb_light;

        // Set the thumb and track drawables
        switchmode.setThumbResource(thumbDrawable);
        switchmode.setChecked(isNightmode);



        btnLogout = view.findViewById(R.id.btn_logout);

        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Showing user details",Toast.LENGTH_SHORT).show();
            }
        });


        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
            isNightmode = sharedPreferences.getBoolean("nightmode",false);
        }


        switchmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isNightmode = switchmode.isChecked();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("nightmode", isNightmode);
                editor.apply();


                updateTheme(isNightmode);



                // Check if the fragment is already in ProfileFragment
                if (!(getActivity() instanceof MainActivity) || !((MainActivity) getActivity()).isProfileFragmentVisible()) {
//                     Navigate to ProfileFragment only if it's not the current fragment
                    MainActivity activity = (MainActivity) getActivity();
                    activity.replaceFragment(new ProfileFragment());
                }
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Logging out",Toast.LENGTH_SHORT).show();
                Context context = getActivity();
                Intent intent = new Intent(context, Login.class);

                context.startActivity(intent);

            }
        });

        return view;
    }

    private boolean isSystemInDarkMode() {
        int theme =  getContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        return theme == Configuration.UI_MODE_NIGHT_YES;
    }


}