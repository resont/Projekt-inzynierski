package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum LoginMsg {
    WRONG_EMAIL_OR_PASSWORD("Wrong Email address or password. Please try again."),
    IS_STILL_VALID("Your token is still valid.");

    private final String errorMsg;

    LoginMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
