package com.ProjektInzynierski.BackEnd.util;

import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultMap {

    private static final String EMAIL = "email";
    private static final String RESULT = "result";
    private static final String ERROR = "error";

    public Map<String, String> createErrorMap(String error) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(ERROR, error);
        return resultMap;
    }

    public Map<String, String> createSuccessMap(String result) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(RESULT, result);
        return resultMap;
    }

    public Map<String, String> createRegistrationSuccessMap(String result, String email) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(RESULT, result);
        resultMap.put(EMAIL, email);
        return resultMap;
    }

    public Map<String, String> createNullBodyErrorMap() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(ERROR, LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        return resultMap;
    }

    public Map<String, String> createSurveyAndAnswerSuccessMap(int id, boolean answer) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("id", String.valueOf(id));
        resultMap.put("answer", String.valueOf(answer));
        return resultMap;
    }

    public Map<String, String> createEmptyMap() {
        return new HashMap<>();
    }
}
