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

//This class is responsible for handling logout process
@Component
public class LogoutProcessor extends ProcessInterface {

    public static final String TOKEN = "token";

    private final UsersRepository usersRepository;

    private Logger logger = LoggerController.getInstance();

    public LogoutProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Map<String, String> process(Map<String, String> body) {

        if (body.get(TOKEN) == null) {
            return ResultMap.createErrorMap(LogoutMsg.LOGOUT_ERROR.getErrorMsg());
        }

        UserEntity userEntity = this.usersRepository.findByUuid(body.get(TOKEN));

        usersRepository.setUuidAndValidTo(body.get(TOKEN), CurrentDateProvider.getOldDate());

        logger.info("Finish logout process.");
        return ResultMap.createSuccessMap(LogoutMsg.LOGOUT.getErrorMsg());
    }
}
