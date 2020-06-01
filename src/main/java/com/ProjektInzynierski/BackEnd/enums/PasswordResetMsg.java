package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

/**
 * This class is responsible for providing outcome messages regarding password reset process
 */
@Getter
public enum PasswordResetMsg {
    RESET_SUCCESSFUL("Password reset."),
    RESET_NOT_SUCCESSFUL("Cannot reset password. Try again.");

    private final String errorMsg;

    PasswordResetMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
