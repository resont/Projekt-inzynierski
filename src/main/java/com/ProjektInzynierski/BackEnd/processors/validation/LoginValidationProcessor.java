package com.ProjektInzynierski.BackEnd.processors.validation;

import java.util.Map;

//This class is responsible for creating validator processor
public abstract class LoginValidationProcessor {

    public LoginValidationProcessor nextProcessor;

    LoginValidationProcessor(LoginValidationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract Map<String, String> process(Map<String, String> map);
}

