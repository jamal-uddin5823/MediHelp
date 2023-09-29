package com.example.medihelp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class lebsProfile extends Fragment {

    private int currentTheme = R.style.Light_Theme_MediHelp;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTheme(currentTheme);

        View view = inflater.inflate(R.layout.activity_lebs_profile, container, false);

        Button btnUserDetails = view.findViewById(R.id.btn_btnUserDetails);
        Button btnTheme = view.findViewById(R.id.btnTheme);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        progressBar = view.findViewById(R.id.progress_profile); // Find the ProgressBar by its ID

        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(); // Show ProgressBar when starting an operation
                Toast.makeText(getActivity(),"Showing user details",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UserDetails.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTheme == R.style.Light_Theme_MediHelp) {
                    setAppTheme(R.style.DarkTheme);
                } else {
                    setAppTheme(R.style.Light_Theme_MediHelp);
                }

                // Recreate the current activity to apply the new theme
                getActivity().recreate();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(); // Show ProgressBar when starting an operation
                Toast.makeText(getActivity(),"Logging out",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setAppTheme(int themeResId) {
        currentTheme = themeResId;
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
