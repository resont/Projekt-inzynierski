package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.LogoutMsg;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LogoutProcessor {

    private final UsersRepository usersRepository;

    private CurrentDateProvider currentDateProvider;

    private ResultMap resultMap;

    public LogoutProcessor(UsersRepository usersRepository, CurrentDateProvider currentDateProvider, ResultMap resultMap) {
        this.usersRepository = usersRepository;
        this.currentDateProvider = currentDateProvider;
        this.resultMap = resultMap;
    }

    public Map<String, String> process(Map<String, String> body) {
        UserData userData = new UserData(body.get("email"));
        userData.setToken(body.get("token"));

        usersRepository.setUuidAndValidTo(userData.getEmail(), userData.getToken(), currentDateProvider.getCurrentDate());

        return resultMap.createSuccessMap(userData.getEmail() + " " + LogoutMsg.LOGOUT.getErrorMsg());
    }
}
