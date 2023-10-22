package com.example.medihelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarksFragment extends Fragment {

//    boolean isBookmarked = true;

    Button btnDoctorContact;
//    AppCompatImageButton btnBookmark;
//    ConstraintLayout btnBookmarkBack;

    RecyclerView recyclerView;
    TextView tvEmpty;

    public static ArrayList<Doctor> arrayList= new ArrayList<>();

    ArrayList<Doctor> doctors = new ArrayList<>();

    RoomDatabaseHelper roomDatabaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        roomDatabaseHelper = RoomDatabaseHelper.getInstance(requireContext());

//        btnDoctorContact = view.findViewById(R.id.btnDoctorContact);
//        btnBookmark = view.findViewById(R.id.btnBookmark);
//        btnBookmarkBack = view.findViewById(R.id.btnBookmarkBack);
//        btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
//        btnBookmarkBack.setBackgroundResource(R.drawable.round_border_solid);

        tvEmpty = view.findViewById(R.id.tvEmpty);

        recyclerView = view.findViewById(R.id.rvBookmarks);
        arrayList = (ArrayList<Doctor>) roomDatabaseHelper.DoctorDao().loadAll();
//        ArrayList<Doctor> array = Doctors.arrayList;
//
//        for(Doctor doctor: array) {
//            if(doctor.isBookmarked()) {
//                arrayList.add(doctor);
//            }
//        }


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BookmarkRecyclerAdapter adapter = new BookmarkRecyclerAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);

        if(arrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }

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

    private void replaceFragment(Fragment destinationFragment) {

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,destinationFragment)
                .commit();
    }

//    private void dialPhoneNumber(String phoneNumber) {
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + phoneNumber));
//        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
//
//    private void setUpDoctors() {
//        doctors.add(new Doctor(1,"Dr. Abul","Medicine","Dhanmondi","01714268748",false));
//        doctors.add(new Doctor(2,"Dr. Babu","Cardiologist","Banani","01918231233",false));
//    }
}