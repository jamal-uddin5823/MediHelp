package com.example.medihelp;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicalAssistant {
    private static final String BASE_URL = "https://api.openai.com/v1/";
    private MyAPI gpt3Service;

    public MedicalAssistant() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gpt3Service = retrofit.create(MyAPI.class);
    }

    public String recommendSpecialist(String symptoms) {
        APIRequest request = new APIRequest("You are an AI assistant that is an expert in medical health and is part of a hospital system called medicare AI\n" +
                "You know about symptoms and signs of various types of illnesses.\n" +
                "Recommend the medical symptom queries with which specialist they should consult\n" +
                "If you are asked a question that is not related to medical health respond with \"I'm sorry but your question is beyond my functionalities\".\n" +
                "Just give one word answer which is the specialist to be consulted as output. Nothing more\n" +
                "Do not use external URLs or blogs to refer\n" +
                "Format any lists on individual lines with a dash and a space in front of each line.\n" +
                symptoms);
        Call<APIResponse> call = gpt3Service.generateMedicalRecommendation(request);

        try {
            Response<APIResponse> response = call.execute();
            if (response.isSuccessful()) {
                APIResponse gpt3Response = response.body();
                if (gpt3Response != null && !gpt3Response.getChoices().isEmpty()) {
                    String recommendation = gpt3Response.getChoices().get(0).getText();
                    return recommendation;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I'm sorry but your question is beyond my functionalities";
    }
}
