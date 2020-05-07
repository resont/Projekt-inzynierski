package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum LogoutMsg {
    LOGOUT("Logout."),
    LOGOUT_ERROR("Logout error.");

    private final String errorMsg;

    LogoutMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}