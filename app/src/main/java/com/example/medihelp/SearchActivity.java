package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Button btnSearch;
    ConstraintLayout clSearch;
    private DatabaseReference databaseRef;
    private SearchAdapter adapter;
    public List<Doctor> doctorsList = new ArrayList<>();
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = findViewById(R.id.btnAnalyseSymptoms);
        clSearch = findViewById(R.id.clSearch);
        RecyclerView recyclerView= findViewById(R.id.suggDoc);
        EditText etNameSearch = findViewById(R.id.etDocNameSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Searching",Toast.LENGTH_SHORT).show();
                String name=etNameSearch.getText().toString();
                searchDoctorByName(name);

                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                Log.d(TAG, "RecyclerView initialized");

                adapter = new SearchAdapter(SearchActivity.this, doctorsList);
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "Search Adapter set");

                clSearch.setVisibility(View.VISIBLE);
            }

        });

    }

    public void searchDoctorByName(String name) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Doctors");
        Query query;

        if (name != null) {
            query = databaseRef.orderByChild("name").startAt(name).endAt(name + "\uf8ff");
        } else {
            query = databaseRef;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                        Log.w(TAG, "Doctor is null for Search by Name: " + snapshot.getKey());
                    }
                }
                Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
                adapter.updateData(doctorsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error fetchinf data: Name Search", Toast.LENGTH_SHORT).show();
            }
        });
    }


}