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

import java.util.Map;
import java.util.UUID;

@Component
public class LoginProcessor {

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    private ResultMap resultMap = new ResultMap();

    LoginProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

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

            UUID uuid = UUID.randomUUID();
            usersRepository.setUuidAndValidToWithPassword(userEntity.getEmail(), userEntity.getPassword(), uuid.toString(), CurrentDateProvider.getCurrentDate());
            logger.info("Authentication successful.");
            return resultMap.createSuccessMap(uuid.toString());
        } catch (Exception e) {
            logger.error("Authentication went wrong.");
            return resultMap.createErrorMap(LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        }

    }

}
