package com.example.medihelp;
import com.example.medihelp.BuildConfig;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
//import com.google.logging.type.HttpRequest;
import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import java.net.http.HttpRequest;
//import java.net.http.HttpRequest.BodyPublishers;


public class DiagnoseActivity extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;

    ImageButton btnVoiceInput;
    Button btnAnalyseSymptoms;
    TextView suggested;

    Button btnDiagtoSearch;

    ConstraintLayout clDiagnosis;
    String apiKey = BuildConfig.API_KEY;
    private static final String TAG = "DiagnoseActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        btnVoiceInput = findViewById(R.id.btnVoiceInput);
        btnAnalyseSymptoms = findViewById(R.id.btnAnalyseSymptoms);
        clDiagnosis = findViewById(R.id.clDiagnosis);
        btnDiagtoSearch = findViewById(R.id.btnDiagtoSearch);
        suggested=findViewById(R.id.textView7);

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

            @Override
            public void onClick(View view) {
                EditText etSymptoms = findViewById(R.id.etSymptoms);
                String symptoms=etSymptoms.getText().toString();
                Message mes = new Message();
                mes.setContent(symptoms);
                List<Message> list = new ArrayList<Message>();
                list.add(mes);

                APIRequestBody req = new APIRequestBody();
                req.setModel("mistralai/mistral-7b-instruct");
                req.setMessages(list);

                Gson gson=new Gson();
                String jsonRequest = gson.toJson(req);
                Log.d(TAG,jsonRequest);
                Log.d(TAG, "Request Prep completed");


                OkHttpClient client = new OkHttpClient.Builder().build();

                Log.d(TAG, "Client initialized");

                Request request = new Request.Builder()
                        .url("https://openrouter.ai/api/v1/chat/completions")
                        .header("Authorization", "Bearer " + apiKey)
                        .header("HTTP-Referer","http://localhost:3000")
                        .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonRequest))
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback(){
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e){
                        Log.d(TAG, "API Call failed: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                        String responseBody;
                        try {
                            responseBody = response.body().string();
                            if (response.isSuccessful()) {
                                runOnUiThread(() -> {
                                    Log.d(TAG,responseBody);

                                    // Parse the JSON response to extract the desired content
                                    Gson gson = new Gson();
                                    APIResponse jsonResponse = gson.fromJson(responseBody, APIResponse.class);
                                    String content = jsonResponse.getChoices().get(0).getAPIMessage().getContent();
                                    content = content.replaceFirst("\\*", "").trim();

                                    // Set the extracted content to the TextView
                                    suggested.setText(content);

//                                    suggested.setText(responseBody);
                                    clDiagnosis.setVisibility(View.VISIBLE);
                                });
                            } else {
                                Log.d(TAG, responseBody);
                                runOnUiThread(() -> {
                                    suggested.setText("API Response unsuccessful: " + responseBody);
                                });
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Error reading response body: " + e.getMessage());
                        }
                    }

//                    @Override
//                    public void onResponse(okhttp3.Call call, okhttp3.Response response) {
//                        String responseBody;
//                        try {
//                            responseBody = response.body().string();
//                            if (response.isSuccessful()) {
//                                DiagnoseActivity.this.runOnUiThread(() -> suggested.setText(responseBody));
//                                clDiagnosis.setVisibility(view.VISIBLE);
//                            } else {
//                                Log.d(TAG, responseBody);
//                                DiagnoseActivity.this.runOnUiThread(() -> {
//                                    suggested.setText("API Response unsuccessful: " + responseBody);
//                                });
//                            }
//                        } catch (IOException e) {
//                            Log.e(TAG, "Error reading response body: " + e.getMessage());
//                        }
//                    }
                });
                Log.d(TAG, "Request handled");
//                delay(3000);

//                clDiagnosis.setVisibility(view.VISIBLE);
//                try {
//                    HttpRequest postRequest = HttpRequest.newBuilder()
//                            .uri(new URI("https://openrouter.ai/api/v1/chat/completions"))
//                            .header("Authorization", "Bearer")
//                            .header("HTTP-Referer", "http://localhost:3000")
//                            .POST(BodyPublishers.ofString(jsonRequest))
//                            .build();
//                    //                Toast.makeText(view.getContext(),"Analysing the symptoms",Toast.LENGTH_SHORT).show();
//                    clDiagnosis.setVisibility(View.VISIBLE);
//                }
//                catch(URISyntaxException e){
//                    System.out.println("API Call failed");
//                }

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