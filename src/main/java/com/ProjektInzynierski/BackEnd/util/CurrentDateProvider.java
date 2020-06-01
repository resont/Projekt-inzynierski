package com.ProjektInzynierski.BackEnd.util;

import java.util.Date;

/**
 * This class is responsible for providing current date
 */
public class CurrentDateProvider {

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date getOldDate() {
        Date date = new Date();
        date.setTime(1000);
        return date;
    }
}
