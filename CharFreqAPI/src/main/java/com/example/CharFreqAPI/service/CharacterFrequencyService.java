package com.example.CharFreqAPI.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CharacterFrequencyService {


    public LinkedHashMap<String, Integer> sortByDescendingOrderCharacters(String inputCharacters) {
        Map<String, Integer> allInputChars = new HashMap<>();

        for (char ch : inputCharacters.toCharArray()) {
            String key = String.valueOf(ch);
            allInputChars.put(key, allInputChars.getOrDefault(key, 0) + 1);
        }
        return sortedDescendingOrder(allInputChars);

    }

    private LinkedHashMap<String, Integer> sortedDescendingOrder(Map<String, Integer> allInputChars) {
        return allInputChars.entrySet()
                .stream()
                .sorted((entry1, entry2) -> -(entry1.getValue().compareTo(entry2.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
