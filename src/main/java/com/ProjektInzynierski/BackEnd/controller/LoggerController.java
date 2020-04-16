package com.ProjektInzynierski.BackEnd.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerController {

    private static final Logger logger = LogManager.getLogger(LoggerController.class);

    private LoggerController() {
    }

    public static Logger getInstance() {
        return logger;
    }

}

