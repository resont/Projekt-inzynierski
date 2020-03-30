package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.processors.login.LoginProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    private LoginProcessor loginProcessor;

    public LoginController(LoginProcessor loginProcessor) {
        this.loginProcessor = loginProcessor;
    }

    @PostMapping("/login")
    Boolean tryToLogInto(@RequestBody Map<String, String> body) {
        return loginProcessor.process(body);
    }
}
