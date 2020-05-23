package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum SurveyToUserUpdateMsg {

    UPDATE_ERROR("Error while updating user data."),
    UPDATE_UNEXPECTED_ERROR("Updating went wrong unexpected error."),
    UPDATE_SUCCESS("User data has been updated.");

    public final String errorMsg;

    SurveyToUserUpdateMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

