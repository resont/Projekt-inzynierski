package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

/**
 * This class is responsible for providing outcome messages regarding answering process
 */
@Getter
public enum AnsweringMsg {
    ANSWERING_ERROR_EMPTY_SURVEY("Can't send empty survey."),
    ANSWERING_UNEXPECTED_ERROR("Error while answering survey.");

    public final String errorMsg;

    AnsweringMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
