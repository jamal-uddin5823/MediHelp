package com.example.medihelp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;
import com.example.medihelp.R;
import com.example.medihelp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UpdateDoctorActivity extends AppCompatActivity {

    // Firebase components
    private FirebaseAuth mAuth;

    // UI components
    private EditText  Speciality, Location,editContact;
    private Button saveButton, backButton;
    private ProgressBar progressBar;
    private static final String TAG = "UpdateDoctor";

    private String prevPass, prevEmail;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));


        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        Speciality = findViewById(R.id.Speciality);
        Location = findViewById(R.id.Location);
        editContact = findViewById(R.id.Contact);
        saveButton = findViewById(R.id.btnSave);
        backButton = findViewById(R.id.backButton);

//        progressBar = findViewById(R.id.progressBar);
// Assuming you have the doctor's ID
        String doctorId = MainActivity.currentUserData.getDoctorId();

// Get a reference to the specific doctor in the "Doctors" schema
        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);
//        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference("Doctors").child(MainActivity.currentUserData.getDoctorId());
        doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Doctor doctorData = snapshot.getValue(Doctor.class);

                    if(doctorData!=null) {
                        String speciality= doctorData.getSpeciality();
                        String location = doctorData.getLocation();
                        String contact = doctorData.getContact();

                        Location.setText(location);
                        editContact.setText(contact);
                        Speciality.setText(speciality);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




// Now, you can update the doctor's information
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ... Your code for getting the updated values from the UI fields

                doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String speciality = Speciality.getText().toString().trim();
                            String contact = editContact.getText().toString().trim();
                            String location = Location.getText().toString().trim();
                            Doctor existingUser = snapshot.getValue(Doctor.class);

                            // Update the fields with non-empty values
                            if (!speciality.isEmpty()) {
                                existingUser.setSpeciality(speciality);
                            }
                            if (!location.isEmpty()) {
                                existingUser.setLocation(location);
                            }
                            if (!contact.isEmpty()) {
                                existingUser.setContact(contact);
                            }

                            // Save the updated user data to Firebase using the specific doctor's key
                            doctorRef.setValue(existingUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UpdateDoctorActivity.this, "Saved Changes", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(UpdateDoctorActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            Intent intent = new Intent(getApplicationContext(),DoctorDetailsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle any database error
                    }
                });

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button click or navigation to previous screen
                Intent intent = new Intent(getApplicationContext(), DoctorDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}






