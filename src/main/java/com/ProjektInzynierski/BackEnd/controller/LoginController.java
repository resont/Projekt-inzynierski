package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.processors.login.LoginProcessor;
import com.ProjektInzynierski.BackEnd.processors.login.LogoutProcessor;
import com.ProjektInzynierski.BackEnd.processors.registration.RegistrationProcessor;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {

    private LoginProcessor loginProcessor;

    private RegistrationProcessor registrationProcessor;

    private LogoutProcessor logoutProcessor;


    public LoginController(LoginProcessor loginProcessor, RegistrationProcessor registrationProcessor, LogoutProcessor logoutProcessor) {
        this.loginProcessor = loginProcessor;
        this.registrationProcessor = registrationProcessor;
        this.logoutProcessor = logoutProcessor;
    }

    @PostMapping("/login")
    Map<String, String> tryToLogInto(@RequestBody Map<String, String> body) {
        if (body != null && body.size() != 0)
            return loginProcessor.process(body);
        else return ResultMap.createNullBodyErrorMap();
    }

    @PostMapping("/registration")
    Map<String, String> registerAccount(@RequestBody Map<String, String> body) {
        if (body != null && body.size() != 0)
            return registrationProcessor.process(body);
        else return ResultMap.createNullBodyErrorMap();
    }

    @PostMapping("/logout")
    Map<String, String> logout(@RequestBody Map<String, String> body) {
        if (body != null && body.size() != 0)
            return logoutProcessor.process(body);
        else return ResultMap.createNullBodyErrorMap();
    }

    @PostMapping("/reset")
    Map<String, String> resetPassword(@RequestBody Map<String, String> body) {
        if (body != null && body.size() != 0)
            return loginProcessor.resetPassword(body);
        else return ResultMap.createNullBodyErrorMap();
    }
}
