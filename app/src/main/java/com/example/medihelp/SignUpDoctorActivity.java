package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class SignUpDoctorActivity extends AppCompatActivity {
    private EditText editTextPassword, editSpeciality, editLocation, editContact;
    private Button buttonSignup;
    private Button btnBack;
    private FirebaseAuth mAuth;
    private DatabaseReference docDatabase;
    ImageView ivSignUp;

    static Long count= 30L;


    public static Doctor myuser;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //init firebase
        mAuth = FirebaseAuth.getInstance();
//        docDatabase = FirebaseDatabase.getInstance().getReference("Doctors");

        setContentView(R.layout.activity_sign_up_doctor);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_doctor);
        editTextPassword = findViewById(R.id.editPassword);
        editSpeciality = findViewById(R.id.editSpeciality);
        editLocation = findViewById(R.id.editLocation);
        editContact = findViewById(R.id.editContact);
        buttonSignup = findViewById(R.id.signup);
        btnBack = findViewById(R.id.btnChangeRole);

//        ivSignUp = findViewById(R.id.ivSignUp);


        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

//        ivSignUp.setOnClickListener(view -> {
//            openImagePicker();
//        });


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // Get the selected image's URI
            selectedImageUri = data.getData();
            ivSignUp.setImageURI(selectedImageUri); // Set the selected image in your ImageView
        }
    }


    private String name="", email="",password="", ConfirmPassword="",speciality="",location="",contact="";
    private void validateData(){
        name=MainActivity.currentUserData.getName().trim().toLowerCase();
        speciality = editSpeciality.getText().toString().trim().toLowerCase();
        location = editLocation.getText().toString().trim().toLowerCase();
        contact = editContact.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        //validate data

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(speciality)){
            Toast.makeText(this,"Enter Speciality",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(location)){
            Toast.makeText(this,"Enter Location",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(contact)){
            Toast.makeText(this,"Enter Contact no",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(MainActivity.currentUserData.getPassword())){
            Toast.makeText(this,"Passwords Doesn't Match",Toast.LENGTH_SHORT).show();
        }


        else{
            addNewDoctor(name,speciality,location,contact);
//            createUserAccount(speciality,location);
        }


    }



    private void addNewDoctor(String name, String speciality, String location, String contact) {

        String userId = FirebaseAuth.getInstance().getUid();

        addUserAsDoctor(name, speciality, location, contact);

        // Set the userType to "doctor"
        updateUserToDoctor(userId);

    }

    private void updateUserToDoctor(String userId) {
        // Set the userType to "doctor"
        DatabaseReference userTypeRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("userType");
        userTypeRef.setValue("doctor");
    }

    private void addUserAsDoctor(String name, String speciality, String location, String contact) {
        long timestamp = System.currentTimeMillis();
        myuser = new Doctor(name, speciality, location, contact);

        DatabaseReference docRef = FirebaseDatabase.getInstance().getReference("Doctors").push();
        String doctorId = docRef.getKey();

        // Construct the doctor's data
        HashMap<String, Object> doctorData = new HashMap<>();
        doctorData.put("ID", doctorId);
        doctorData.put("name", name);
        doctorData.put("speciality", speciality);
        doctorData.put("location", location);
        doctorData.put("contact", contact);
        doctorData.put("picture", MainActivity.currentUserData.getPicture());

        // Update the "Doctors" database
        docDatabase = FirebaseDatabase.getInstance().getReference("Doctors");
        docDatabase.child(doctorId)
                .setValue(doctorData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Now that the doctor data is successfully added, update the user's data
                        updateUserToDoctor(mAuth.getCurrentUser().getUid(), doctorId); // Pass the current user's UID and doctorId
                        Toast.makeText(SignUpDoctorActivity.this, "Signed Up!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpDoctorActivity.this, "Error updating user info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDBError", "Error updating user info: " + e.getMessage(), e);
                    }
                });
    }

    private void updateUserToDoctor(String userId, String doctorId) {
        // Update the user's data to indicate they are now a doctor
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        userRef.child("userType").setValue("doctor");

        // Additionally, store the doctorId in the user's data
        userRef.child("doctorId").setValue(doctorId);

        // You can also update other user information as needed
    }

//    private void updateUserToDoctor(String userId) {
//        // Update the user's data to indicate they are now a doctor
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
//
//        // Assuming "isDoctor" is a field to indicate the user's doctor status
//        userRef.child("isDoctor").setValue(true);
//
//        // You can update other user information as needed
//    }


}



