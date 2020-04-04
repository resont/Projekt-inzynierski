package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum RegistrationMsg {
    REGISTRY_SUCCESSFUL("Registered."),
    REGISTRY_NOT_SUCCESSFUL("Cannot register. Try again.");

    private final String errorMsg;

    RegistrationMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
