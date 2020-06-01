package com.ProjektInzynierski.BackEnd.processors.validation;

//This class is responsible for setting up validator chain
public class LoginValidationChain {

    public static LoginValidationProcessor getValidationChainProcessor() {
        LoginValidationProcessor passwordIsPresentValidation = new PasswordIsPresentValidation(null);
        return new EmailIsPresentValidation(passwordIsPresentValidation);
    }
}
