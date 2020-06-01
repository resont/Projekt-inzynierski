package com.ProjektInzynierski.BackEnd.data.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is responsible for mapping json data from http request about user data
 */
@Setter
@Getter
public class UserData {

    @Setter(AccessLevel.NONE)
    private String email;

    @Setter(AccessLevel.NONE)
    private String password;

    private String token;

    public UserData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserData(String email) {
        this.email = email;
    }

    public UserData() {
    }

}
