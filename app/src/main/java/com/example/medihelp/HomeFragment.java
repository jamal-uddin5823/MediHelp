package com.example.medihelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    boolean isBookmarked = false;

    ShapeableImageView imgProfile;

    RecyclerView recyclerView;

    CardView cvDiagnose;
    CardView cvSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cvDiagnose = view.findViewById(R.id.cvDiagnose);
        cvSearch = view.findViewById(R.id.cvSearch);

        cvDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DiagnoseActivity.class);
                startActivity(intent);
            }
        });

        cvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
            }
        });


        imgProfile = view.findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ProfileFragment());
            }
        });

        recyclerView = view.findViewById(R.id.rvNearDoc);
        ArrayList<Doctor> arrayList = Doctors.arrayList;




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DocRecyclerAdapter adapter = new DocRecyclerAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
//        Button btnDoctorContact = view.findViewById(R.id.btnDoctorContact);
//        ConstraintLayout lytDiagnoseMe = view.findViewById(R.id.lytDiagnoseMe);
//        ConstraintLayout lytFindaDoc = view.findViewById(R.id.lytFindaDoc);
//        ImageButton btnBookmark = view.findViewById(R.id.btnBookmark);
//        ConstraintLayout btnBookmarkBack = view.findViewById(R.id.btnBookmarkBack);


//        btnBookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isBookmarked) {
//                    btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
//                    btnBookmarkBack.setBackgroundResource(R.drawable.round_border_solid);
//                } else {
//                    btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lavender));
//                    btnBookmarkBack.setBackgroundResource(R.drawable.round_border_trans);
//                }
//                isBookmarked=!isBookmarked;
//            }
//        });
//
//        btnDoctorContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialPhoneNumber("+8801714268748");
////                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        lytDiagnoseMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new DiagnoseFragment());
//            }
//        });
//
//        lytFindaDoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new SearchFragment());
//            }
//        });

        return view;
    }

    private void replaceFragment(Fragment destinationFragment) {

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,destinationFragment)
                .commit();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}