package com.example.CharFreqAPI.controller;

import com.example.CharFreqAPI.model.FrequencyRequest;
import com.example.CharFreqAPI.service.CharacterFrequencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CharacterFrequencyController {
    private final CharacterFrequencyService service;

    @Autowired
    public CharacterFrequencyController(CharacterFrequencyService service) {
        this.service = service;
    }

    @PostMapping("/character-frequency")
    public ResponseEntity<Map<String, Integer>> calculateCharacterFrequency(@RequestBody @Valid FrequencyRequest request,
                                                                            BindingResult bindingResult) {
        Map<String, Integer> response;
        if (bindingResult.hasErrors()) {
            List<FieldError> errorMap = bindingResult.getFieldErrors();
            response = new HashMap<>();
            for (FieldError error : errorMap) {
                response.put(error.getField() + " - " + error.getDefaultMessage(), null);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = service.sortByDescendingOrderCharacters(request.getInput());
        return ResponseEntity.ok(response);
    }
}
