package com.example.medihelp;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnDoctorContact = view.findViewById(R.id.btnDoctorContact);
        ConstraintLayout lytDiagnoseMe = view.findViewById(R.id.lytDiagnoseMe);
        ConstraintLayout lytFindaDoc = view.findViewById(R.id.lytFindaDoc);

        btnDoctorContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
            }
        });

        lytDiagnoseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new DiagnoseFragment());
            }
        });

        lytFindaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SearchFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment destinationFragment) {

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,destinationFragment)
                .commit();
    }
}