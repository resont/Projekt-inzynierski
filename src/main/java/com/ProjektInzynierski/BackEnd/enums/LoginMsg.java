package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum LoginMsg {
    WRONG_EMAIL_OR_PASSWORD("Wrong Email address or password. Please try again."),
    EMPTY_EMAIL("Email cannot be empty."),
    EMPTY_PASSWORD("Password cannot be empty."),
    IS_STILL_VALID("Your token is still valid.");

    private final String errorMsg;

    LoginMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
