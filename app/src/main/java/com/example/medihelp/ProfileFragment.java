package com.example.medihelp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ProfileFragment extends Fragment {
    View view;
    private int currentTheme=R.style.Light_Theme_MediHelp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnUserDetails = view.findViewById(R.id.btnUserDetails);
        Button btnTheme = view.findViewById(R.id.btnTheme);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Showing user details",Toast.LENGTH_SHORT).show();
            }
        });

        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Changing theme",Toast.LENGTH_SHORT).show();
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
}