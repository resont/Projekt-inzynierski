package com.ProjektInzynierski.BackEnd.processors.validation;

import com.ProjektInzynierski.BackEnd.enums.LoginMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for validating passwords data
 */
public class PasswordsArePresentValidation {

    /**
     * This method validate if two passwords data is present.
     *
     * @param map contains data to be validated.
     * @return Map<String, String> contains error or input data itself.
     */
    public Map<String, String> process(Map<String, String> map) {

        String newPassword = map.get("newPassword");
        String oldPassword = map.get("oldPassword");
        if (newPassword == null || newPassword.equals("") || oldPassword == null || oldPassword.equals("")) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", LoginMsg.EMPTY_PASSWORD.getErrorMsg());
            return errorMap;
        }
        return map;
    }
}
