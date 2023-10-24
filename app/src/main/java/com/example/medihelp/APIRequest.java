package com.example.medihelp;

import com.google.gson.annotations.SerializedName;

public class APIRequest {
    @SerializedName("prompt")
    private String prompt;

    public APIRequest(String prompt) {
        this.prompt = prompt;
    }
}
