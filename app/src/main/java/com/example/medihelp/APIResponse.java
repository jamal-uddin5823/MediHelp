package com.example.medihelp;

import java.util.List;

public class APIResponse {
    private String id;
    private String model;
    private List<APIChoice> choices;

    // Getter for choices
    public List<APIChoice> getChoices() {
        return choices;
    }
}


