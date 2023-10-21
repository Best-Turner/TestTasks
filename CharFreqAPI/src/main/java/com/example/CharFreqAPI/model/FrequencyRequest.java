package com.example.CharFreqAPI.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FrequencyRequest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotEmpty(message = "There must be an incoming string")
    @Size(message = "Text length must be from 3 to 100 characters", min = 3, max = 100)
    private String input;


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
