package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.processors.login.LoginProcessor;
import com.ProjektInzynierski.BackEnd.processors.registration.RegistrationProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    private LoginProcessor loginProcessor;

    private RegistrationProcessor registrationProcessor;

    public LoginController(LoginProcessor loginProcessor, RegistrationProcessor registrationProcessor) {
        this.loginProcessor = loginProcessor;
        this.registrationProcessor = registrationProcessor;
    }

    @PostMapping("/login")
    String tryToLogInto(@RequestBody Map<String, String> body) {
        return loginProcessor.process(body);
    }

    @PostMapping("/registration")
    Map<String, String> registerAccount(@RequestBody Map<String, String> body) {
        return registrationProcessor.process(body);
    }
}
