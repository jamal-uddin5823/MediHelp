package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserDetails extends AppCompatActivity {
    TextView profileName, profileEmail, profilePassword;
    TextView   Age, Weight, Blood;
    Button back,update;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        profileName = findViewById(R.id.editName);
        profileEmail = findViewById(R.id.editEmail);
        profilePassword = findViewById(R.id.editPassword);
        progressBar = findViewById(R.id.progressBar);

        Age = findViewById(R.id.Age);
        Weight = findViewById(R.id.Weight);
        Blood = findViewById(R.id.Blood);
        back=findViewById(R.id.backButton);
        update=findViewById(R.id.updateButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            showAllUserData(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), lebsProfile.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showAllUserData(FirebaseUser firebaseUser) {
        progressBar.setVisibility(View.VISIBLE); // Show the ProgressBar

        String uid = firebaseUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    if (user != null) {
                        String nameUser = user.getName();
                        String emailUser = user.getEmail();
                        String passwordUser = user.getPassword();
                        String age= user.getAge();
                        String weight=user.getWeight();
                        String bloodGroup=user.getBloodGroup();
                        profileName.setText(nameUser);
                        profileEmail.setText(emailUser);
                        profilePassword.setText(passwordUser);
                        Age.setText(age);
                        Weight.setText(weight);
                        Blood.setText(bloodGroup);
                    }
                }

                // Hide the ProgressBar when data retrieval is complete
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserDetails.this, "Data not retrieved from Firebase", Toast.LENGTH_SHORT).show();

                // Hide the ProgressBar if an error occurs during data retrieval
                progressBar.setVisibility(View.GONE);
            }
        });

        ImageView toggleEyeIcon = findViewById(R.id.toggleEyeIcon);
        Drawable visibleIcon = ContextCompat.getDrawable(this, R.drawable.ic_eye);
        Drawable hiddenIcon = ContextCompat.getDrawable(this, R.drawable.ic_not_eye);

        PasswordToggleHelper passwordToggleHelper = new PasswordToggleHelper(profilePassword, toggleEyeIcon, visibleIcon, hiddenIcon);
        toggleEyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordToggleHelper.togglePasswordVisibilityForImageView();
            }
        });

    }




}