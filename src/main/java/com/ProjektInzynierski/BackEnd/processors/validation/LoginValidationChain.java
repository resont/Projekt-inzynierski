package com.ProjektInzynierski.BackEnd.processors.validation;

public class LoginValidationChain {

    public static LoginValidationProcessor getValidationChainProcessor() {
        LoginValidationProcessor passwordIsPresentValidation = new PasswordIsPresentValidation(null);
        return new EmailIsPresentValidation(passwordIsPresentValidation);
    }
}
