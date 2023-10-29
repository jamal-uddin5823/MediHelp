package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private EditText name,editTextEmail, editTextPassword, confirmPassword,editAge,editWeight;
    private  Button buttonSignup;
    private Button buttonSign_in;
    private FirebaseAuth mAuth;
    private ProgressDialog progress_signup;
    private Spinner bloodGroupSpinner, genderSpinner;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    public static User myuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //init firebase
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        progress_signup=new ProgressDialog(this);
        progress_signup.setTitle("Please wait");
        progress_signup.setCanceledOnTouchOutside(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.editName);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.confirmpassword);
        editAge = findViewById(R.id.editAge);
        editWeight = findViewById(R.id.editWeight);
        buttonSignup = findViewById(R.id.signup);
        buttonSign_in = findViewById(R.id.signin);

        bloodGroupSpinner = findViewById(R.id.Blood);
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(this, R.array.blood_group_options, android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSpinner.setAdapter(bloodGroupAdapter);

        genderSpinner = findViewById(R.id.Gender);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);


        buttonSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();


            }
        });

    }

    private String username="", email="",password="", ConfirmPassword="",gender="", bloodGroup="",age="",weight="";
    private void validateData(){
        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        age = editAge.getText().toString().trim();
        weight = editWeight.getText().toString().trim();
        username=name.getText().toString().trim();
        ConfirmPassword=confirmPassword.getText().toString().trim();
        gender = genderSpinner.getSelectedItem().toString();
        bloodGroup = bloodGroupSpinner.getSelectedItem().toString();

        //validate data

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(gender)){
            Toast.makeText(this,"Select Gender",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(bloodGroup)){
            Toast.makeText(this,"Select Blood Group",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ConfirmPassword)){
            Toast.makeText(this,"Confirm Password",Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(ConfirmPassword)){
            Toast.makeText(this,"Passwords Doesn't Match",Toast.LENGTH_SHORT).show();
        }

        else{
            createUserAccount(age,weight,gender,bloodGroup);

        }


    }

    private void createUserAccount(String age, String weight, String gender, String bloodGroup){
        progress_signup.setMessage("Creating Account...");
        progress_signup.show();
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progress_signup.dismiss();
                        updateUserInfo(age,weight,gender,bloodGroup);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress_signup.dismiss();
                        Toast.makeText(Signup.this, "Hello: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateUserInfo(String age, String weight, String gender, String bloodGroup) {
        progress_signup.setMessage("Saving User Info...");
        long timestamp = System.currentTimeMillis();

        String uid = mAuth.getUid();
//        String age=editAge.;
//        String bloodGroup= "Blood Group not Added";
//        String weight= "Weight not Added";
//        String gender = "Gender not Added";

//        showAllUserData();

        myuser=new User(username,email,password);


        // Setup data to add in db

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("name", username);
        hashMap.put("password",password);
        hashMap.put("profileImage", "");
        hashMap.put("age",age);
        hashMap.put("gender",gender);
        hashMap.put("bloodGroup",bloodGroup);
        hashMap.put("weight",weight);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);

        // Setup data to db

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progress_signup.dismiss();
                        Toast.makeText(Signup.this, "Account Created...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress_signup.dismiss();
                        Toast.makeText(Signup.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDBError", "Error updating user info: " + e.getMessage(), e);
                    }
                });
    }




}