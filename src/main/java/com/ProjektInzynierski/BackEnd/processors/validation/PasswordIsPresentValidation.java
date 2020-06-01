package com.ProjektInzynierski.BackEnd.processors.validation;

import com.ProjektInzynierski.BackEnd.enums.LoginMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for validating password data
 */
public class PasswordIsPresentValidation extends LoginValidationProcessor {

    public PasswordIsPresentValidation(LoginValidationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * This method validate if password data is present.
     *
     * @param map contains data to be validated.
     * @return Map<String, String> contains error or input data itself.
     */
    @Override
    public Map<String, String> process(Map<String, String> map) {

        String password = map.get("password");
        if (password == null || password.equals("")) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", LoginMsg.EMPTY_PASSWORD.getErrorMsg());
            return errorMap;
        } else if (nextProcessor != null) {
            nextProcessor.process(map);
        }
        return map;
    }
}

