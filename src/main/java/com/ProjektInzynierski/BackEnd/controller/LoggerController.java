package com.ProjektInzynierski.BackEnd.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for providing logger singleton for another class
 */
@Component
public class LoggerController {

    private static final Logger logger = LogManager.getLogger(LoggerController.class);

    public static Logger getInstance() {
        return logger;
    }

}

