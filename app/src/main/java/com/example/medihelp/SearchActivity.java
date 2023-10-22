package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    Button btnSearch;
    ConstraintLayout clSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = findViewById(R.id.btnAnalyseSymptoms);
        clSearch = findViewById(R.id.clSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Searching",Toast.LENGTH_SHORT).show();
                clSearch.setVisibility(View.VISIBLE);
            }

        });
    }
}