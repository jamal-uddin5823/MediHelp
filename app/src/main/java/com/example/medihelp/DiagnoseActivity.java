package com.example.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.List;

public class DiagnoseActivity extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    private MedicalAssistant medicalAssistant;
    ImageButton btnVoiceInput;
    Button btnAnalyseSymptoms;

    Button btnDiagtoSearch;

    ConstraintLayout clDiagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        btnVoiceInput = findViewById(R.id.btnVoiceInput);
        btnAnalyseSymptoms = findViewById(R.id.btnAnalyseSymptoms);
        clDiagnosis = findViewById(R.id.clDiagnosis);
        btnDiagtoSearch = findViewById(R.id.btnDiagtoSearch);

        medicalAssistant = new MedicalAssistant();

        btnDiagtoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


        btnVoiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        btnAnalyseSymptoms.setOnClickListener(new View.OnClickListener() {
            EditText etSymptoms = findViewById(R.id.etSymptoms);
            TextView diagnosis=findViewById(R.id.diagnosis);
            @Override
            public void onClick(View view) {
                String symptoms;
                symptoms=etSymptoms.getText().toString();
                System.out.println(symptoms);
                String diagnosetext = medicalAssistant.recommendSpecialist(symptoms);
                diagnosis.setText(diagnosetext);
                System.out.println(diagnosetext);
//                Toast.makeText(view.getContext(),"Analysing the symptoms",Toast.LENGTH_SHORT).show();
                clDiagnosis.setVisibility(View.VISIBLE);

            }
        });

    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText.

            EditText etSymptoms = findViewById(R.id.etSymptoms);
            etSymptoms.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}