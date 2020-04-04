package com.ProjektInzynierski.BackEnd.util;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CurrentDateProvider {

    public Date getCurrentDate() {
        return new Date();
    }
}
