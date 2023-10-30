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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Configuration;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    View view;
    SwitchCompat switchmode;
    boolean isNightmode;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    Button btnUserDetails,btnAboutUs;
    Button btnLogout;

    TextView tvUserName;
    ImageView imgProfileFrag;

    private static final String TAG = "ProfileFragment";


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

        imgProfileFrag = view.findViewById(R.id.imgProfileFrag);

        btnUserDetails = view.findViewById(R.id.btnUserDetails);
        btnAboutUs = view.findViewById(R.id.btn_aboutUs);

        switchmode = view.findViewById(R.id.switchMode);

        tvUserName = view.findViewById(R.id.tvUserName);
        btnLogout = view.findViewById(R.id.btn_logout);



        showName(tvUserName);

        // Check the system theme and set the appropriate drawables
        int thumbDrawable = isSystemInDarkMode() ? R.drawable.thumb_dark : R.drawable.thumb_light;

        // Set the thumb and track drawables
        switchmode.setThumbResource(thumbDrawable);
        switchmode.setChecked(isNightmode);


        btnAboutUs.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AboutUs.class);
            startActivity(intent);
        });




//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),UpdateProfile.class);
//                startActivity(intent);
//            }
//        });

        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),UserDetails.class);
                startActivity(intent);
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
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(context, Login.class);
                MainActivity.currFragment="Home";
                MainActivity.currentUserData=null;
                startActivity(intent);

            }
        });

        return view;
    }

    private boolean isSystemInDarkMode() {
        int theme =  getContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        return theme == Configuration.UI_MODE_NIGHT_YES;
    }

    void showName(TextView textView){
        if(MainActivity.currentUserData==null) {
            UserDataRetrieval userDataRetrieval = new UserDataRetrieval();


            userDataRetrieval.retrieveUserData(new UserDataRetrieval.OnUserDataReceivedListener() {
                @Override
                public void onUserReceived(User user) {
                    if (user != null) {
                        // The user object contains the current user's data
                        String userName = user.getName();
                        // String userEmail = user.getEmail();
                        textView.setText(userName);
                        Log.d(TAG, "onUserReceived: " +userName);
                        String imageUrl = user.getPicture();
                        Picasso.get().load(imageUrl).into(imgProfileFrag);
                        // ... and so on
                    } else {
                        // Handle the case where the user is not found or not authenticated
                        Log.d(TAG,"No welcome");
                    }
                }
            });

        }  else {
            textView.setText(MainActivity.currentUserData.getName());
            Picasso.get().load(MainActivity.currentUserData.getPicture()).into(imgProfileFrag);
        }



    }


}