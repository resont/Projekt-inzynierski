package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.enums.LogoutMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class is responsible for handling authorisation process
 */
@Component
public class AuthProcessor extends ProcessInterface {

    private static final String TOKEN = "token";

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    AuthProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * This method validate input data and authenticate user by provided token (body).
     *
     * @param body contains token.
     * @return Map<String, String> contains data to be returned respectively success or error message.
     */
    @Override
    public Map<String, String> process(Map<String, String> body) {

        if (body.get(TOKEN) == null) {
            return ResultMap.createErrorMap(LogoutMsg.LOGOUT_ERROR.getErrorMsg());
        }

        UserEntity userEntity = this.usersRepository.findByUuid(body.get(TOKEN));

        if (userEntity == null || CurrentDateProvider.getCurrentDate().compareTo(userEntity.getValidTo()) > 0) {
            logger.warn("Unauthorised access.");
            return ResultMap.createErrorMap("Error.");
        }

        return ResultMap.createSuccessMap("Succes.");
    }
}
