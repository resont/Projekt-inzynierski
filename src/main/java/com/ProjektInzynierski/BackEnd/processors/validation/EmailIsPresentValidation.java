package com.ProjektInzynierski.BackEnd.processors.validation;

import com.ProjektInzynierski.BackEnd.enums.LoginMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for validating email data
 */
public class EmailIsPresentValidation extends LoginValidationProcessor {

    public EmailIsPresentValidation(LoginValidationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * This method validate if email data is present.
     *
     * @param map contains data to be validated.
     * @return Map<String, String> contains error or input data itself.
     */
    @Override
    public Map<String, String> process(Map<String, String> map) {

        String email = map.get("email");
        if (email == null || email.equals("")) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", LoginMsg.EMPTY_EMAIL.getErrorMsg());
            return errorMap;
        } else if (nextProcessor != null) {
            map = nextProcessor.process(map);
        }
        return map;
    }
}
