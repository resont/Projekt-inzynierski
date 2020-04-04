package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class LoginProcessor {

    private final UsersRepository usersRepository;

    private CurrentDateProvider currentDateProvider;

    private ResultMap resultMap;

    public LoginProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //ToDo implement session set validto +1h?
    //ToDo check this implementation
    //ToDo mby return Map<String, String>?
    //Todo test this processor
    //ToDo implement validation
    public Map<String, String> process(Map<String, String> body) {
        UserData userData = new UserData(body.get("email"), body.get("password"));

        UserEntity userEntity;
        try {
            userEntity = usersRepository.findByEmailAndPassword(userData.getEmail(), userData.getPassword());
        } catch (Exception e) {
            return resultMap.createErrorMap(LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        }

        if (userEntity.getUuid() == null) {
            //create session
            UUID uuid = UUID.randomUUID();
            usersRepository.setUuidAndValidToWithPassword(userEntity.getEmail(), userEntity.getPassword(), uuid.toString(), currentDateProvider.getCurrentDate());
            return resultMap.createSuccessMap(uuid.toString());
        }

        Date userDate = userEntity.getValidTo();
        int result = userDate.compareTo(currentDateProvider.getCurrentDate());
        if (result <= 0) {
            //update session
            return resultMap.createEmptyMap();
        } else {
            return resultMap.createSuccessMap(LoginMsg.IS_STILL_VALID.getErrorMsg());
        }

    }

    public void createSession() {

    }
}
