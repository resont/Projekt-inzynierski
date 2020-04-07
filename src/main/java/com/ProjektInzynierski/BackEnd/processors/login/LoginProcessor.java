package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import com.ProjektInzynierski.BackEnd.processors.validation.LoginValidationChain;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class LoginProcessor {

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    private ResultMap resultMap = new ResultMap();

    public LoginProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //ToDo implement session set validto +1h?
    //ToDo check this implementation
    //ToDo mby return Map<String, String>?
    //Todo test this processor
    public Map<String, String> process(Map<String, String> body) {

        logger.info("Start of login validation.");
        body = LoginValidationChain.getValidationChainProcessor().process(body);
        if (body.get("error") != null) {
            logger.error("Error while login validating.");
            return body;
        }
        logger.info("End of login validation.");

        UserData userData = new UserData(body.get("email"), body.get("password"));

        logger.info("Start of login authentication.");
        try {
            UserEntity userEntity = usersRepository.findByEmailAndPassword(userData.getEmail(), userData.getPassword());

            if (userEntity.getUuid() == null) {
                //create session
                UUID uuid = UUID.randomUUID();
                usersRepository.setUuidAndValidToWithPassword(userEntity.getEmail(), userEntity.getPassword(), uuid.toString(), CurrentDateProvider.getCurrentDate());
                return resultMap.createSuccessMap(uuid.toString());
            }

            Date userDate = userEntity.getValidTo();
            int result = userDate.compareTo(CurrentDateProvider.getCurrentDate());
            if (result <= 0) {
                //update session
                return resultMap.createEmptyMap();
            } else {
                return resultMap.createSuccessMap(LoginMsg.IS_STILL_VALID.getErrorMsg());
            }
        } catch (Exception e) {
            return resultMap.createErrorMap(LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        }

    }

    public void createSession() {

    }
}
