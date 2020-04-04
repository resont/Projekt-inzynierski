package com.ProjektInzynierski.BackEnd.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResultMap {

    public Map<String, String> createErrorMap(String error) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("error", error);
        return resultMap;
    }

    public Map<String, String> createSuccessMap(String result) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    public Map<String, String> createEmptyMap() {
        return new HashMap<>();
    }
}
