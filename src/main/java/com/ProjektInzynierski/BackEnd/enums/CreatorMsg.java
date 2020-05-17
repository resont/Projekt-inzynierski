package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum CreatorMsg {
    CREATOR_ERROR("Error while creating survey."),
    CREATOR_NULL_ERROR("Survey data is null."),
    ANSWERING_ERROR("Error answering survey.");

    public final String errorMsg;

    CreatorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
