package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;


public class UserDetailsActivity extends AppCompatActivity {

    ShapeableImageView btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        btnEdit = findViewById(R.id.btnEditDetails);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                binding.bottomNavigationView.setSelectedItemId(R.id.profile);
                Intent intent = new Intent(getApplicationContext(),UpdateProfile.class);
                startActivity(intent);
                Log.d("Hello","hi");
            }
        });
    }
}