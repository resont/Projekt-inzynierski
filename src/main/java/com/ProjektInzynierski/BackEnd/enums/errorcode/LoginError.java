package com.ProjektInzynierski.BackEnd.enums.errorcode;

import lombok.Getter;

@Getter
public enum LoginError {
    WRONG_EMAIL("Wrong Email adress. Please try again");

    private final String errorMsg;

    LoginError(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
