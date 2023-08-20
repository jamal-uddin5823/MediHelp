package com.example.medihelp;

import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.medihelp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ConstraintLayout lytDiagnoseMe = rootView.findViewById(R.id.lytDiagnoseMe);
        ImageView imgDiagnoseMe = rootView.findViewById(R.id.imgDiagnoseMe);


        lytDiagnoseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","Touching");
                DiagnoseFragment fragmentDiagnose = new DiagnoseFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragmentDiagnose)
                        .addToBackStack(null) // Add to back stack if needed
                        .commit();
            }
        });

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void replaceFragment(Fragment destinationFragment) {


        // Get the fragment manager and start a transaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the destination fragment
        fragmentTransaction.replace(R.id.frame_layout, destinationFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }
}