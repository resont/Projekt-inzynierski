package com.ProjektInzynierski.BackEnd.processors.registration;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.RegistrationMsg;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RegistrationProcessor {

    private final UsersRepository usersRepository;

    RegistrationProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //ToDo this looks terrible mby create util? Do we need User class at all?
    public Map<String, String> process(Map<String, String> body) {
        UserData userData = new UserData(body.get("email"), body.get("password"));
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userData.getEmail());
        userEntity.setPassword(userData.getPassword());

        UserEntity userEntityResult = usersRepository.save(userEntity);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", RegistrationMsg.REGISTRY_SUCCESSFUL.getErrorMsg());
        resultMap.put("email", userEntityResult.getEmail());

        return resultMap;
    }
}
