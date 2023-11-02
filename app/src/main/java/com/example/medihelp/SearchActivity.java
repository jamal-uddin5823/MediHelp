package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    Button btnSearch;
    ConstraintLayout clSearch;
    EditText etNameSearch;
    EditText etSpecialitySearch;
    EditText etLocationSearch;
    TextView home;
    private DatabaseReference databaseRef;

    public List<Doctor> doctorsList = new ArrayList<>();
    private static final String TAG = "SearchActivity";
    RecyclerView recview;
    private MyAdapter adapter;

    private DatabaseReference searchReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));


        btnSearch = findViewById(R.id.btnAnalyseSymptoms);
        clSearch = findViewById(R.id.clSearch);
        recview= findViewById(R.id.searchrecview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "RecyclerView initialized");

        home=findViewById(R.id.textView5);

        etNameSearch = findViewById(R.id.etDocNameSearch);
        etSpecialitySearch = findViewById(R.id.etDocSpecialitySearch);
        etLocationSearch = findViewById(R.id.etDocLocationSearch);
        searchReference = FirebaseDatabase.getInstance().getReference("Doctors");

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Searching",Toast.LENGTH_SHORT).show();
                String name=etNameSearch.getText().toString().trim();
                name = name.replaceFirst("\\Dr. ", "").trim();
                name = name.replaceFirst("\\Dr.", "").trim();
                name = name.replaceFirst("\\Dr ", "").trim();
                name = name.replaceFirst("\\Dr", "").trim();
                name=name.toLowerCase();
                String speciality=etSpecialitySearch.getText().toString().trim();
                speciality=speciality.toLowerCase();
                String location=etLocationSearch.getText().toString().trim();
                location=location.toLowerCase();
                Log.d(TAG,name+" "+speciality+" "+location);

//                Log.d(TAG,name);
//                if(name.isEmpty())Log.d(TAG,"AAAAAAAAAAA");

                searchReference = FirebaseDatabase.getInstance().getReference("Doctors");
                searchDoctorFinal(name,speciality,location);

                adapter = new MyAdapter(SearchActivity.this, doctorsList);

                recview.setAdapter(adapter);
                Log.d(TAG, "Adapter set");
                // Make sure you have a list of doctors
                Log.d("AdapterSetup", "Number of doctors: " + doctorsList.size());

                recview.setAdapter(adapter);
                clSearch.setVisibility(View.VISIBLE);
            }

        });

    }

    public static String capitalizeEachWord(String str) {
        String[] words = str.split(" ");
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            String firstLetter = word.substring(0, 1).toUpperCase();
            String remainingLetters = word.substring(1).toLowerCase();
            capitalizedString.append(firstLetter).append(remainingLetters).append(" ");
        }

        return capitalizedString.toString().trim();
    }


    public void searchDoctorFinal(String name,String speciality, String location) {

        String startname = name;
        String endname = name + "\uf8ff";

        String startspeciality = speciality;
        String endspeciality = speciality + "\uf8ff";

        String startlocation = location;
        String endlocation = location + "\uf8ff";

        Query nameQuery = searchReference.orderByChild("name").startAt(startname).endAt(endname);
        Query specialityQuery = searchReference.orderByChild("speciality").startAt(startspeciality).endAt(endspeciality);
        Query locationQuery = searchReference.orderByChild("location").startAt(startlocation).endAt(endlocation);

        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorsList.clear();
                Set<DataSnapshot> nameResults = new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    nameResults.add(snapshot);
                    Log.d(TAG, "namecount " + nameResults.size());
                }

                specialityQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        doctorsList.clear();
                        Set<DataSnapshot> specialityResults = new HashSet<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            specialityResults.add(snapshot);
                            Log.d(TAG, "speccount " + specialityResults.size());
                        }

                        locationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                doctorsList.clear();
                                Set<DataSnapshot> locationResults = new HashSet<>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    locationResults.add(snapshot);
                                    Log.d(TAG, "locacount " + locationResults.size());
                                }

                                for (DataSnapshot snap1 : nameResults) {
                                    for (DataSnapshot snap2 : specialityResults) {
                                        for (DataSnapshot snap3 : locationResults) {
                                            long a = snap1.child("ID").getValue(Long.class);
                                            long b = snap2.child("ID").getValue(Long.class);
                                            long c = snap3.child("ID").getValue(Long.class);
                                            if ((a == b) && (b == c)) {
                                                Log.d(TAG, a + " " + b + " " + c);
                                                Doctor doctor = snap1.getValue(Doctor.class);
                                                if (doctor != null) {
                                                    doctor.setID(snap1.child("ID").getValue(Long.class));
                                                    String name = capitalizeEachWord(snap1.child("name").getValue(String.class));
                                                    doctor.setName("Dr. " + name);
                                                    String speciality = capitalizeEachWord(snap1.child("speciality").getValue(String.class));
                                                    doctor.setSpeciality(speciality);
                                                    String location = capitalizeEachWord(snap1.child("location").getValue(String.class));
                                                    doctor.setLocation(location);
                                                    doctor.setContact(snap1.child("contact").getValue(String.class));
                                                    Log.d(TAG, "Doctor ID: " + doctor.getID());
                                                    Log.d(TAG, "Doctor Name: " + doctor.getName());
                                                    Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
                                                    Log.d(TAG, "Doctor Location: " + doctor.getLocation());
                                                    Log.d(TAG, "Doctor Contact: " + doctor.getContact());

                                                    doctorsList.add(doctor);
                                                } else {
                                                    Log.w(TAG, "Doctor is null for snapshot: " + snap1.getKey());
                                                }
                                            }
                                        }
                                    }
                                }

                                Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
                                System.out.println(doctorsList.size());
                                adapter.updateData(doctorsList);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getBaseContext(), "Error fetching data: Location Search", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getBaseContext(), "Error fetching data: Speciality Search", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error fetching data: Name Search", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


//    //implementation of search
//    public void searchDoctor(String name,String speciality, String location) {
//
//        String startname = name;
//        String endname = name + "\uf8ff";
//
//        String startspeciality = speciality;
//        String endspeciality = speciality + "\uf8ff";
//
//        String startlocation = location;
//        String endlocation = location + "\uf8ff";
//        Query query = searchReference.orderByChild("name").startAt(startname).endAt(endname);
//        // Create a query that filters doctors based on the name substring
//        if(!name.isEmpty() && !speciality.isEmpty() && !location.isEmpty()) {
//            Log.d(TAG,"Case all");
//            Query nameQuery = searchReference.orderByChild("name").startAt(startname).endAt(endname);
//            Query specialityQuery = searchReference.orderByChild("speciality").startAt(startspeciality).endAt(endspeciality);
//            Query locationQuery = searchReference.orderByChild("location").startAt(startlocation).endAt(endlocation);
//
//            nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    doctorsList.clear();
//                    Set<DataSnapshot> nameResults = new HashSet<>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        nameResults.add(snapshot);
//                        Log.d(TAG, "namecount "+nameResults.size());
//                    }
//
//                    specialityQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            doctorsList.clear();
//                            Set<DataSnapshot> specialityResults = new HashSet<>();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                specialityResults.add(snapshot);
//                                Log.d(TAG, "speccount "+specialityResults.size());
//                            }
//
//                            locationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    doctorsList.clear();
//                                    Set<DataSnapshot> locationResults = new HashSet<>();
//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                        locationResults.add(snapshot);
//                                        Log.d(TAG, "locacount "+locationResults.size());
//                                    }
//
//                                    for(DataSnapshot snap1: nameResults){
//                                        for(DataSnapshot snap2:specialityResults){
//                                            for(DataSnapshot snap3:locationResults){
//                                                long a=snap1.child("ID").getValue(Long.class);
//                                                long b=snap2.child("ID").getValue(Long.class);
//                                                long c=snap3.child("ID").getValue(Long.class);
//                                                if((a==b)&&(b==c)){
//                                                    Log.d(TAG, a+" "+b+" "+c);
//                                                    Doctor doctor = snap1.getValue(Doctor.class);
//                                                    if (doctor != null) {
//                                                        doctor.setID(snap1.child("ID").getValue(Long.class));
//                                                        String name = capitalizeEachWord(snap1.child("name").getValue(String.class));
//                                                        doctor.setName("Dr. " + name);
//                                                        String speciality = capitalizeEachWord(snap1.child("speciality").getValue(String.class));
//                                                        doctor.setSpeciality(speciality);
//                                                        String location= capitalizeEachWord(snap1.child("location").getValue(String.class));
//                                                        doctor.setLocation(location);
//                                                        doctor.setContact(snap1.child("contact").getValue(String.class));
//                                                        Log.d(TAG, "Doctor ID: " + doctor.getID());
//                                                        Log.d(TAG, "Doctor Name: " + doctor.getName());
//                                                        Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
//                                                        Log.d(TAG, "Doctor Location: " + doctor.getLocation());
//                                                        Log.d(TAG, "Doctor Contact: " + doctor.getContact());
//
//                                                        doctorsList.add(doctor);
//                                                    } else {
//                                                        Log.w(TAG, "Doctor is null for snapshot: " + snap1.getKey());
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                    Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
//                                    System.out.println(doctorsList.size());
//                                    adapter.updateData(doctorsList);
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    Toast.makeText(getBaseContext(), "Error fetching data: Location Search", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(getBaseContext(), "Error fetching data: Speciality Search", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getBaseContext(), "Error fetching data: Name Search", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else if(!name.isEmpty() && !speciality.isEmpty()) {
//            Log.d(TAG,"Case Name and Speciality");
//            Query nameQuery = searchReference.orderByChild("name").startAt(startname).endAt(endname);
//            Query specialityQuery = searchReference.orderByChild("speciality").startAt(startspeciality).endAt(endspeciality);
//            nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Set<DataSnapshot> nameResults = new HashSet<>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        nameResults.add(snapshot);
//                        Log.d(TAG, "namecount "+nameResults.size());
//                    }
//
//                    specialityQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Set<DataSnapshot> specialityResults = new HashSet<>();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                specialityResults.add(snapshot);
//                                Log.d(TAG, "speccount "+specialityResults.size());
//                            }
//
//
//                            Set<DataSnapshot> FinalResults = new HashSet<>();
//
//                            for(DataSnapshot snap1: nameResults){
//                                for(DataSnapshot snap2:specialityResults){
//                                    long a=snap1.child("ID").getValue(Long.class);
//                                    long b=snap2.child("ID").getValue(Long.class);
//                                    if((snap1.equals(snap2))){
////                                                    Log.d(TAG, a.longValue()+" "+b.longValue()+" "+c.longValue());
//                                        FinalResults.add(snap1);
//                                    }
//                                }
//                            }
//
//                            for (DataSnapshot snapshot : FinalResults) {
//                                Doctor doctor = snapshot.getValue(Doctor.class);
//                                if (doctor != null) {
//                                    doctor.setID(snapshot.child("ID").getValue(Long.class));
//                                    String name = capitalizeEachWord(snapshot.child("name").getValue(String.class));
//                                    doctor.setName("Dr. " + name);
//                                    String speciality = capitalizeEachWord(snapshot.child("speciality").getValue(String.class));
//                                    doctor.setSpeciality(speciality);
//                                    String location= capitalizeEachWord(snapshot.child("location").getValue(String.class));
//                                    doctor.setLocation(location);
//                                    doctor.setContact(snapshot.child("contact").getValue(String.class));
//                                    Log.d(TAG, "Doctor ID: " + doctor.getID());
//                                    Log.d(TAG, "Doctor Name: " + doctor.getName());
//                                    Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
//                                    Log.d(TAG, "Doctor Location: " + doctor.getLocation());
//                                    Log.d(TAG, "Doctor Contact: " + doctor.getContact());
//
//                                    doctorsList.add(doctor);
//                                } else {
//                                    Log.w(TAG, "Doctor is null for snapshot: " + snapshot.getKey());
//                                }
//                            }
//                            Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
//                            System.out.println(doctorsList.size());
//                            adapter.updateData(doctorsList);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(getBaseContext(), "Error fetching data: Speciality Search", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getBaseContext(), "Error fetching data: Name Search", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else if(!name.isEmpty() && !location.isEmpty()) {
//            Log.d(TAG,"Name and Location");
//            Query nameQuery = searchReference.orderByChild("name").startAt(startname).endAt(endname);
//            Query locationQuery = searchReference.orderByChild("location").startAt(startlocation).endAt(endlocation);
//            nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Set<DataSnapshot> nameResults = new HashSet<>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        nameResults.add(snapshot);
//                        Log.d(TAG, "namecount "+nameResults.size());
//                    }
//
//                    locationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Set<DataSnapshot> locationResults = new HashSet<>();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                locationResults.add(snapshot);
//                                Log.d(TAG, "locacount "+locationResults.size());
//                            }
//
//
//                            Set<DataSnapshot> FinalResults = new HashSet<>();
//
//                            for(DataSnapshot snap1: nameResults){
//                                for(DataSnapshot snap2:locationResults){
//                                    long a=snap1.child("ID").getValue(Long.class);
//                                    long b=snap2.child("ID").getValue(Long.class);
//                                    if((snap1.equals(snap2))){
////                                                    Log.d(TAG, a.longValue()+" "+b.longValue()+" "+c.longValue());
//                                        FinalResults.add(snap1);
//                                    }
//                                }
//                            }
//
//                            for (DataSnapshot snapshot : FinalResults) {
//                                Doctor doctor = snapshot.getValue(Doctor.class);
//                                if (doctor != null) {
//                                    doctor.setID(snapshot.child("ID").getValue(Long.class));
//                                    String name = capitalizeEachWord(snapshot.child("name").getValue(String.class));
//                                    doctor.setName("Dr. " + name);
//                                    String speciality = capitalizeEachWord(snapshot.child("speciality").getValue(String.class));
//                                    doctor.setSpeciality(speciality);
//                                    String location= capitalizeEachWord(snapshot.child("location").getValue(String.class));
//                                    doctor.setLocation(location);
//                                    doctor.setContact(snapshot.child("contact").getValue(String.class));
//                                    Log.d(TAG, "Doctor ID: " + doctor.getID());
//                                    Log.d(TAG, "Doctor Name: " + doctor.getName());
//                                    Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
//                                    Log.d(TAG, "Doctor Location: " + doctor.getLocation());
//                                    Log.d(TAG, "Doctor Contact: " + doctor.getContact());
//
//                                    doctorsList.add(doctor);
//                                } else {
//                                    Log.w(TAG, "Doctor is null for snapshot: " + snapshot.getKey());
//                                }
//                            }
//                            Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
//                            System.out.println(doctorsList.size());
//                            adapter.updateData(doctorsList);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(getBaseContext(), "Error fetching data: Speciality Search", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getBaseContext(), "Error fetching data: Name Search", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else {
//
//            if (!name.isEmpty())
//                query = searchReference.orderByChild("name").startAt(startname).endAt(endname);
//            else if (!speciality.isEmpty())
//                query = searchReference.orderByChild("speciality").startAt(startspeciality).endAt(endspeciality);
//            else if (!location.isEmpty())
//                query = searchReference.orderByChild("location").startAt(startlocation).endAt(endlocation);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    doctorsList.clear();
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Doctor doctor = snapshot.getValue(Doctor.class);
//
//                        if (doctor != null) {
//                            doctor.setID(snapshot.child("ID").getValue(Long.class));
//                            String name = capitalizeEachWord(snapshot.child("name").getValue(String.class));
//                            doctor.setName("Dr. " + name);
//                            String speciality = capitalizeEachWord(snapshot.child("speciality").getValue(String.class));
//                            doctor.setSpeciality(speciality);
//                            String location = capitalizeEachWord(snapshot.child("location").getValue(String.class));
//                            doctor.setLocation(location);
//                            doctor.setContact(snapshot.child("contact").getValue(String.class));
//                            Log.d(TAG, "Doctor ID: " + doctor.getID());
//                            Log.d(TAG, "Doctor Name: " + doctor.getName());
//                            Log.d(TAG, "Doctor Speciality: " + doctor.getSpeciality());
//                            Log.d(TAG, "Doctor Location: " + doctor.getLocation());
//                            Log.d(TAG, "Doctor Contact: " + doctor.getContact());
//
//                            doctorsList.add(doctor);
//                        } else {
//                            Log.w(TAG, "Doctor is null for snapshot: " + snapshot.getKey());
//                        }
//                    }
//                    Log.d("DoctorsListSize", "Size of doctorsList: " + doctorsList.size());
//                    System.out.println(doctorsList.size());
//                    adapter.updateData(doctorsList);
//                }
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle any errors
//                    Toast.makeText(getBaseContext(), "Error fetching data: Name Search", Toast.LENGTH_SHORT).show();
//                }
//
//
//            });
//        }
//    }