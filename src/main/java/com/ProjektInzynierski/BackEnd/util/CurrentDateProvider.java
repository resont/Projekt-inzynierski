package com.ProjektInzynierski.BackEnd.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CurrentDateProvider {

    public static Date getCurrentDate() {
        return new Date();
    }
}
