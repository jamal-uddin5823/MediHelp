package com.example.medihelp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medihelp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ActivityMainBinding binding;
    boolean isBookmarked = false;

    ShapeableImageView imgProfile;

    private static final String TAG = "HomeFragment";
    private MyAdapter adapter;
    public List<Doctor> doctorsList = new ArrayList<>();
    private DatabaseReference databaseReference;
    TextView welcomeTextView;

    CardView cvDiagnose;
    CardView cvSearch;
    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

//        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

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
//                binding.bottomNavigationView.setSelectedItemId(R.id.profile);
                replaceFragment(new ProfileFragment());
            }
        });

        welcomeTextView = view.findViewById(R.id.welcome);
        ShowName();

//        Log.d(TAG, "Welcome user: "+username);


        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        retrieveDoctorsData();

        RecyclerView recyclerView= view.findViewById(R.id.rvNearDoc);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Log.d(TAG, "RecyclerView initialized");


        recyclerView.setAdapter(new MyAdapter(getActivity().getApplicationContext(),doctorsList));

        adapter = new MyAdapter(requireContext(), doctorsList);
        Log.d(TAG, "Adapter set");
        // Make sure you have a list of doctors
        Log.d("AdapterSetup", "Number of doctors: " + doctorsList.size());

        recyclerView.setAdapter(adapter);

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

//        btnDoctorContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialPhoneNumber("+8801714268748");
////                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

    public void retrieveDoctorsData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);

                    if (doctor != null) {
                        doctor.setID(snapshot.child("ID").getValue(Long.class));
                        doctor.setName(snapshot.child("name").getValue(String.class));
                        doctor.setSpeciality(snapshot.child("speciality").getValue(String.class));
                        doctor.setLocation(snapshot.child("location").getValue(String.class));
                        doctor.setContact(snapshot.child("contact").getValue(String.class));
                        Log.d(TAG, "Doctor ID: " + doctor.getID());
                        Log.d(TAG, "Doctor Name: " + doctor.getName());
                        Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
                        Log.d(TAG, "Doctor Location: " + doctor.getLocation());
                        Log.d(TAG, "Doctor Contact: " + doctor.getContact());

                        doctorsList.add(doctor);
                    } else {
                        Log.w(TAG, "Doctor is null for snapshot: " + snapshot.getKey());
                    }
                }
                Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
                System.out.println(doctorsList.size());
                adapter.updateData(doctorsList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }

        });
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

    void ShowName(){
        UserDataRetrieval userDataRetrieval = new UserDataRetrieval();

        userDataRetrieval.retrieveUserData(new UserDataRetrieval.OnUserDataReceivedListener() {
            @Override
            public void onUserReceived(User user) {
                if (user != null) {
                    // The user object contains the current user's data
                    String userName = user.getName();
                   // String userEmail = user.getEmail();
                    welcomeTextView.setText("Welcome \n" +userName );
                    Log.d(TAG, "onUserReceived: " +userName);
                    // ... and so on
                } else {
                    // Handle the case where the user is not found or not authenticated
                    Log.d(TAG,"No welcome");
                }
            }
        });

    }
}