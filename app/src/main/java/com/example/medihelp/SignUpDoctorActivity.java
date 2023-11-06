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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        ivSignUp = findViewById(R.id.ivSignUp);


        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        ivSignUp.setOnClickListener(view -> {
            openImagePicker();
        });


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void openImagePicker() {
        // Use an Intent to open the image picker
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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


    private void uploadImageToFirebaseStorage(Uri imageUri, OnImageUploadComplete callback) {
        // Get a reference to the Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("doctors/profile_images");

        // Generate a unique name for the image file (e.g., using a timestamp)
        String imageFileName = "image_" + System.currentTimeMillis();

        // Create a reference to the file with the generated name
        StorageReference imageRef = storageRef.child(imageFileName);

        // Upload the image
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully, get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Save the image URL in the Realtime Database
                        String imageUrl = uri.toString();
                        saveImageUrlToDatabase(imageUrl);

                        // Pass the image URL back to the caller
                        callback.onImageUploadComplete(imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Get a reference to the Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            // Save the image URL under the current user's node
            databaseReference.child("Doctors").child(userId).child("picture").setValue(imageUrl)
                    .addOnSuccessListener(aVoid -> {
                        // Image URL saved successfully
                        Toast.makeText(this, "Image URL saved to database", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                        Toast.makeText(this, "Failed to save image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(this, "Doctor not authenticated", Toast.LENGTH_SHORT).show();
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
        else if(selectedImageUri==null) {
            Toast.makeText(this, "Profile pic not added", Toast.LENGTH_SHORT).show();
        }

        else{
            addNewDoctor(name,speciality,location,contact);
//            createUserAccount(speciality,location);
        }


    }

//    private void createUserAccount(String speciality, String location) {
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnSuccessListener(authResult -> {
//                    // User account created successfully
//                    sendVerificationEmail( speciality,location);
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("Firebase Auth", "Error: " + e.getMessage(), e);
//                    Toast.makeText(SignUpDoctorActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }
//
//    private void sendVerificationEmail(String speciality, String location) {
//        mAuth.getCurrentUser().sendEmailVerification()
//                .addOnSuccessListener(unused -> {
//                    // Verification email sent successfully
//                    Toast.makeText(SignUpDoctorActivity.this, "Verification email sent. Please verify and login.", Toast.LENGTH_SHORT).show();
//
//                    updateUserInfo(speciality, location);
//                })
//                .addOnFailureListener(e -> {
//                    // Handle the error
//                    Toast.makeText(SignUpDoctorActivity.this, "Failed to send verification email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("VerificationEmailError", "Error sending verification email: " + e.getMessage(), e);
//                });
//    }


    private void addNewDoctor(String name, String speciality, String location, String contact) {
        uploadImageToFirebaseStorage(selectedImageUri, imageUrl -> {
            long timestamp = System.currentTimeMillis();
            myuser = new Doctor(name, speciality,location, contact );

            // Use push() to generate a unique ID for the new doctor entry
            DatabaseReference docRef = FirebaseDatabase.getInstance().getReference("Doctors").push();
            String doctorId = docRef.getKey(); // This is the new unique ID generated by Firebase

            // Create a HashMap with the doctor's information
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ID", doctorId); // Use the generated ID
            hashMap.put("name", name);
            hashMap.put("speciality", speciality);
            hashMap.put("location", location);
            hashMap.put("contact", contact);

//             Setup data to db
            docDatabase = FirebaseDatabase.getInstance().getReference("Doctors");
            docDatabase.child(doctorId)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            MainActivity.currentUserData.setDoctorStatus(true);
                            Toast.makeText(SignUpDoctorActivity.this, "Signed Up!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            progress_signup.dismiss();
                            Toast.makeText(SignUpDoctorActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseDBError", "Error updating user info: " + e.getMessage(), e);
                        }
                    });
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginDoctorActivity.class);
        startActivity(intent);
        finish();
    }
}



//
////        progress_signup.setMessage("Saving User Info...");
//        long timestamp = System.currentTimeMillis();
//
//        String uid = mAuth.getUid();
//
//        if(selectedImageUri!=null) {
//
//        }
//        uploadImageToFirebaseStorage(selectedImageUri,imageUrl -> {
//            myuser=new Doctor(name,email,password);
//
//
//            // Setup data to add in db
//
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("uid", uid);
//            hashMap.put("email", email);
//            hashMap.put("name", name);
//            hashMap.put("password",password);
////            hashMap.put("profileImage", "");
//            hashMap.put("speciality",speciality);
//            hashMap.put("location",location);
//            hashMap.put("contact",contact);
//            hashMap.put("picture",imageUrl);
//            hashMap.put("userType", "doctor");
//            hashMap.put("timestamp", timestamp);
//
//            // Setup data to db
//
//            userDatabase = FirebaseDatabase.getInstance().getReference("Doctors");
//            userDatabase.child(uid)
//                    .setValue(hashMap)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
////                            progress_signup.dismiss();
//                            Toast.makeText(SignUpDoctorActivity.this, "Account Created...", Toast.LENGTH_SHORT).show();
////                            Intent intent = new Intent(getApplicationContext(),LoginDoctorActivity.class);
////                            startActivity(intent);
////                            finish();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                            progress_signup.dismiss();
//                            Toast.makeText(SignUpDoctorActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.e("FirebaseDBError", "Error updating user info: " + e.getMessage(), e);
//                        }
//                    });
//        });
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, LoginDoctorActivity.class);
//        startActivity(intent);
//        finish();
//    }
//}