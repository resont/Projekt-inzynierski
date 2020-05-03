package com.ProjektInzynierski.BackEnd.enums;

import lombok.Getter;

@Getter
public enum CreatorMsg {
    CREATOR_ERROR("Error while creating survey.");

    public final String errorMsg;

    CreatorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
