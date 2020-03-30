package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginProcessor {

    private final UsersRepository usersRepository;

    public LoginProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean process(Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email != null && password != null) {
            return usersRepository.existsByEmailAndPassword(email, password);
        } else {
            return false;
        }
    }
}
