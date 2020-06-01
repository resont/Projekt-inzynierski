package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

/**
 * This class is responsible for providing outcome messages regarding registration process
 */
@Getter
public enum RegistrationMsg {
    REGISTRY_SUCCESSFUL("Registered."),
    REGISTRY_NOT_SUCCESSFUL("Cannot register. Try again.");

    private final String errorMsg;

    RegistrationMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
