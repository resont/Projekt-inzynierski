package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.UserDataFactory;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.UserData;
import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import com.ProjektInzynierski.BackEnd.enums.PasswordResetMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.processors.validation.LoginValidationChain;
import com.ProjektInzynierski.BackEnd.processors.validation.PasswordsArePresentValidation;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.CurrentDateProvider;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * This class is responsible for handling login process
 */
@Component
public class LoginProcessor extends ProcessInterface {

    private static final String ERROR = "error";

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    LoginProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * This method validate input data and login user to the system by provided token (body).
     *
     * @param body contains token.
     * @return Map<String, String> contains data to be returned respectively success or error message.
     */
    @Override
    public Map<String, String> process(Map<String, String> body) {

        Map<String, String> result;

        logger.info("Start of login validation.");
        body = LoginValidationChain.getValidationChainProcessor().process(body);
        if (body.get(ERROR) != null) {
            logger.error("Error while login validating.");
            return body;
        }
        logger.info("End of login validation.");

        UserData userData = UserDataFactory.create(body.get("email"), body.get("password"));

        logger.info("Start of login authentication.");
        try {
            UserEntity userEntity = usersRepository.findByEmailAndPassword(userData.getEmail(), userData.getPassword());

            UUID uuid = UUID.randomUUID();
            Instant instant = CurrentDateProvider.getCurrentDate().toInstant().plusSeconds(3600);
            Date newDate = Date.from(instant);
            usersRepository.setUuidAndValidToWithPassword(userEntity.getEmail(), userEntity.getPassword(), uuid.toString(), newDate);
            result = ResultMap.createSuccessMap(uuid.toString());
        } catch (Exception e) {
            result = ResultMap.createErrorMap(LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        }

        checkIfError(result);

        return result;
    }

    /**
     * This method log respectively success or error message.
     *
     * @param result contains map with or without error data.
     */
    private void checkIfError(Map<String, String> result) {
        if (result.get(ERROR) != null) {
            logger.error("Authentication went wrong.");
        } else {
            logger.info("Authentication successful.");
        }
    }

    /**
     * This method validate input data and change password value in database for new one.
     *
     * @param body contains old and new password.
     * @return Map<String, String> contains respectively success or error message
     */
    public Map<String, String> resetPassword(Map<String, String> body) {
        body = new PasswordsArePresentValidation().process(body);
        if (body.get("error") != null) {
            logger.error("Error while password reset validating.");
            return body;
        }

        UserData oldUserData = UserDataFactory.create(null, body.get("oldPassword"));
        oldUserData.setToken(body.get("token"));

        UserData newUserData = UserDataFactory.create(null, body.get("newPassword"));


        try {
            UserEntity userEntity = usersRepository.findByUuidAndPassword(oldUserData.getToken(), oldUserData.getPassword());

            usersRepository.setNewPassword(userEntity.getPassword(), newUserData.getPassword(), userEntity.getUuid());
            logger.info("Password reset successful.");
            return ResultMap.createSuccessMap(PasswordResetMsg.RESET_SUCCESSFUL.getErrorMsg());

        } catch (Exception e) {
            logger.error("Reset went wrong.");
            return ResultMap.createErrorMap(PasswordResetMsg.RESET_NOT_SUCCESSFUL.getErrorMsg());
        }

    }

}
