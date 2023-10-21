package com.example.CharFreqAPI.controller;

import com.example.CharFreqAPI.model.FrequencyRequest;
import com.example.CharFreqAPI.service.CharacterFrequencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacterFrequencyControllerTest {
    private CharacterFrequencyController controller;
    private CharacterFrequencyService service;

    @BeforeEach
    public void setUp() {
        service = mock(CharacterFrequencyService.class);
        controller = new CharacterFrequencyController(service);
    }

    @Test
    public void testCalculateCharacterFrequency_ValidInput() {
        FrequencyRequest request = new FrequencyRequest();
        request.setInput("hello");

        Map<String, Integer> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put("h", 1);
        expectedResponse.put("l", 2);
        expectedResponse.put("e", 1);
        expectedResponse.put("o", 1);

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");

        when(service.sortByDescendingOrderCharacters(request.getInput())).thenReturn((LinkedHashMap<String, Integer>) expectedResponse);

        ResponseEntity<Map<String, Integer>> response = controller.calculateCharacterFrequency(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testCalculateCharacterFrequency_InvalidInput() {
        FrequencyRequest request = new FrequencyRequest();
        request.setInput(""); // Неверный ввод, должен вызвать ошибку валидации

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        bindingResult.rejectValue("input", "There must be an incoming string");
        bindingResult.rejectValue("input", "Text length must be from 3 to 100 characters");

        ResponseEntity<Map<String, Integer>> response = controller.calculateCharacterFrequency(request, bindingResult);
        FieldError fieldError = bindingResult.getFieldError();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Integer> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNull(responseBody.get("input - There must be an incoming string"));
    }
}