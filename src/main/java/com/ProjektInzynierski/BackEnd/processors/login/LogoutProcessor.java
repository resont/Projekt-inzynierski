package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.LogoutMsg;
import com.ProjektInzynierski.BackEnd.processors.validation.LoginValidationChain;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LogoutProcessor {

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    private ResultMap resultMap = new ResultMap();

    public LogoutProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Map<String, String> process(Map<String, String> body) {

        logger.info("Start of logout validation.");
        body = LoginValidationChain.getValidationChainProcessor().process(body);
        if (body.get("error") != null) {
            logger.error("Error while logout validating.");
            return body;
        }
        logger.info("End of logout validation.");

        UserData userData = new UserData(body.get("email"));
        userData.setToken(body.get("token"));

        usersRepository.setUuidAndValidTo(userData.getEmail(), userData.getToken(), CurrentDateProvider.getCurrentDate());

        logger.info("Finish logout process");
        return resultMap.createSuccessMap(userData.getEmail() + " " + LogoutMsg.LOGOUT.getErrorMsg());
    }
}
