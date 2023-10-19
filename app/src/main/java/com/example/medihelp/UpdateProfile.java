package com.example.medihelp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateProfile extends AppCompatActivity {

    // Firebase components
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;

    // UI components
    private EditText editName, editEmail, editPassword, Age, Weight, Blood, Gender;
    private Button saveButton, backButton;
    private ProgressBar progressBar;
    private static final String TAG = "YourClassName";

    private String prevPass, prevEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize UI components
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        Age = findViewById(R.id.Age);
        Gender = findViewById(R.id.Gender);
        Weight = findViewById(R.id.Weight);
        Blood = findViewById(R.id.Blood);
        saveButton = findViewById(R.id.SaveButton);
        backButton = findViewById(R.id.backButton);
//        progressBar = findViewById(R.id.progressBar);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileData();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button click or navigation to previous screen
                Intent intent = new Intent(getApplicationContext(), UserDetails.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void saveProfileData() {
        // Retrieve user input
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String age = Age.getText().toString().trim();
        String gender = Gender.getText().toString().trim();
        String weight = Weight.getText().toString().trim();
        String bloodGroup = Blood.getText().toString().trim();

        // Check if any of the fields have non-empty values
        if (!name.isEmpty() || !email.isEmpty() || !password.isEmpty() || !age.isEmpty() || !weight.isEmpty() || !bloodGroup.isEmpty() || !gender.isEmpty()) {
            // At least one field has a non-empty value, so we can proceed to update the profile

            // Validate user input (add your own validation logic)

            // Update the user's profile data in Firebase
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String uid = currentUser.getUid();


                // Fetch the existing user data from Firebase
                userDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User existingUser = dataSnapshot.getValue(User.class);

                            // Update the fields with non-empty values and change text color to black
                            if (!name.isEmpty()) {
                                existingUser.setName(name);
                                editName.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                            }
                            if (!email.isEmpty()) {
                                prevEmail = existingUser.getEmail();
                                prevPass = existingUser.getPassword();
                                UpdateEmail(prevPass,  email);
                                existingUser.setEmail(email);
                                editEmail.setTextColor(getResources().getColor(R.color.black)); // Change text color to black

                            }
                            if (!password.isEmpty()) {
                                prevPass = existingUser.getPassword();
                                existingUser.setPassword(password);
                                editPassword.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                                UpdatePassword(prevPass, password);
                            }
                            if (!age.isEmpty()) {
                                existingUser.setAge(age);
                                Age.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                            }
                            if (!gender.isEmpty()) {
                                existingUser.setGender(gender);
                                Gender.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                            }
                            if (!weight.isEmpty()) {
                                existingUser.setWeight(weight);
                                Weight.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                            }
                            if (!bloodGroup.isEmpty()) {
                                existingUser.setBloodGroup(bloodGroup);
                                Blood.setTextColor(getResources().getColor(R.color.black)); // Change text color to black
                            }

                            // Save the updated user data to Firebase
                            userDatabase.child(uid).setValue(existingUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UpdateProfile.this, "Saved Changes", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(UpdateProfile.this, "Something went wrong...", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error

                    }


                });
            }
        }
    }

    private void UpdatePassword(String oldPassword, String newPassword) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(currentUser.getEmail(), oldPassword);
        currentUser.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        currentUser.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //password updated
                                        Toast.makeText(UpdateProfile.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //password failed

                                        Toast.makeText(UpdateProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }


    private void UpdateEmail(String currentPassword, String newEmail) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);

        currentUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> reauthTask) {
                        if (reauthTask.isSuccessful()) {
                            // Re-authentication successful, update email
                            currentUser.updateEmail(newEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> emailUpdateTask) {
                                            if (emailUpdateTask.isSuccessful()) {
                                                // Email updated successfully and verification email sent
                                                Log.d(TAG, "Email updated successfully. Verification email sent.");
                                                Toast.makeText(UpdateProfile.this, "Email updated successfully. Please check your inbox for a verification email.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.e(TAG, "Failed to update email: " + emailUpdateTask.getException().getMessage());
                                                Toast.makeText(UpdateProfile.this, "Failed to update email: " + emailUpdateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Log.e(TAG, "Re-authentication failed: " + reauthTask.getException().getMessage());
                            Toast.makeText(UpdateProfile.this, "Re-authentication failed: " + reauthTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Verification email sent successfully
                            Log.d(TAG, "Email verification sent.");
                            Toast.makeText(UpdateProfile.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Log the error message
                            Log.e(TAG, "Failed to send email verification: " + task.getException().getMessage());
                            Toast.makeText(UpdateProfile.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





}






