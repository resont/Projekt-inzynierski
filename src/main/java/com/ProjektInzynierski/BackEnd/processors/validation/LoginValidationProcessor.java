package com.ProjektInzynierski.BackEnd.processors.validation;

import java.util.Map;

public abstract class LoginValidationProcessor {

    public LoginValidationProcessor nextProcessor;

    LoginValidationProcessor(LoginValidationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract Map<String, String> process(Map<String, String> map);
}

