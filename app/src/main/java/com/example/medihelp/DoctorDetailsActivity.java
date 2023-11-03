package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView profileName, profileEmail, profilePassword;

    TextView profileSpeciality, profileLocation;
    Button back,update;
    ImageView imgProfile;
    private FirebaseAuth mAuth;
//    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
//        Toast.makeText(getApplicationContext(),MainActivityDoctor.currentDoctorData.getName()+" "+MainActivityDoctor.currentDoctorData.getLocation(),Toast.LENGTH_SHORT).show();

        profileName = findViewById(R.id.editName);
        profileEmail = findViewById(R.id.editEmail);
        profilePassword = findViewById(R.id.editPassword);

//        progressBar = findViewById(R.id.progressBar);

        profileSpeciality = findViewById(R.id.editSpeciality);
        profileLocation = findViewById(R.id.editLocation);
        imgProfile = findViewById(R.id.imgProfile);
//        back=findViewById(R.id.backButton);

//        update=findViewById(R.id.updateButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();



        if (firebaseUser == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            showAllUserData(firebaseUser);
//            progressBar.setVisibility(View.VISIBLE);
        }



//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), lebsProfile.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), UpdateProfile.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        Log.d("YourActivity", "Attempting to find LinearLayout ll3...");
        LinearLayoutCompat ll3 = findViewById(R.id.ll3);

        if (ll3 != null) {
            Log.d("YourActivity", "LinearLayout ll3 found!");
        } else {
            Log.e("YourActivity", "LinearLayout ll3 not found!");
        }



        ImageView toggleEyeIcon = ll3.findViewById(R.id.toggleEyeIcon);
        Drawable visibleIcon = ContextCompat.getDrawable(this, R.drawable.ic_eye);
        Drawable hiddenIcon = ContextCompat.getDrawable(this, R.drawable.ic_not_eye);

        PasswordToggleHelper passwordToggleHelper = new PasswordToggleHelper(profilePassword, toggleEyeIcon, hiddenIcon, visibleIcon);

//        toggleEyeIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("YourActivity", "toggleEyeIcon clicked");
//                // Add other actions if needed
//            }
//        });

        toggleEyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Hello","KUKURRRRRRRRRRR");
                passwordToggleHelper.togglePasswordVisibilityForImageView();
            }
        });

        ShapeableImageView btnEdit = findViewById(R.id.btnEditDetails);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                binding.bottomNavigationView.setSelectedItemId(R.id.profile);
//                Log.d("Hello","hiiiiiiiiiiiiiiiiii");
                Intent intent = new Intent(getApplicationContext(),UpdateDoctorActivity.class);
                startActivity(intent);
                finish();

            }
        });

        TextView profile = findViewById(R.id.textView4);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("EIIIIIIIIIIIIIIIII", "onClick: ALUEEEEEEEEEEEEEEE");
            }
        });
    }



    private void showAllUserData(FirebaseUser firebaseUser) {


//        progressBar.setVisibility(View.VISIBLE); // Show the ProgressBar

        if(MainActivityDoctor.currentDoctorData==null) {
            String uid = firebaseUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Doctors").child(uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Doctor doctorData = dataSnapshot.getValue(Doctor.class);

                        if (doctorData != null) {
                            String nameUser = doctorData.getName();
                            String emailUser = doctorData.getEmail();
                            String passwordUser = doctorData.getPassword();
                            String speciality= doctorData.getSpeciality();
                            String location = doctorData.getLocation();
                            String imageUrl = doctorData.getPicture();
                            profileName.setText(nameUser);
                            profileEmail.setText(emailUser);
                            profilePassword.setText(passwordUser);
                            profileLocation.setText(location);

                            DoctorDetailsActivity.this.profileSpeciality.setText(speciality);
                            Picasso.get().load(imageUrl).into(imgProfile);
                        }
                    }

                    // Hide the ProgressBar when data retrieval is complete
//                progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DoctorDetailsActivity.this, "Data not retrieved from Firebase", Toast.LENGTH_SHORT).show();

                    // Hide the ProgressBar if an error occurs during data retrieval
//                progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            profileName.setText(MainActivityDoctor.currentDoctorData.getName());
            profileEmail.setText(MainActivityDoctor.currentDoctorData.getEmail());
            profilePassword.setText(MainActivityDoctor.currentDoctorData.getPassword());
            profileSpeciality.setText(MainActivityDoctor.currentDoctorData.getSpeciality());
            profileLocation.setText(MainActivityDoctor.currentDoctorData.getLocation());
            if(MainActivityDoctor.currentDoctorData!=null) {
                String imageUrl = MainActivityDoctor.currentDoctorData  .getPicture(); // Replace with the actual method or key to access the image URL
                if(imageUrl!=null) {
                    Picasso.get().load(imageUrl).into(imgProfile);
                }
            }

        }




    }







}