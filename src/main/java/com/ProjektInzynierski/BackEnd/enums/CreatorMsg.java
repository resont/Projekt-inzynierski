package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum CreatorMsg {
    CREATOR_ERROR("Error while creating survey."),
    CREATOR_NULL_ERROR("Survey data is null."),
    CREATOR_NULL_QUESTIONS_DATA_ERROR("Questions data is null."),
    CREATOR_UNEXPECTED_ERROR("Creation went wrong unexpected error.."),
    CREATOR_SUCCESS("Survey has been created."),
    ANSWERING_ERROR("Error answering survey.");

    public final String errorMsg;

    CreatorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
