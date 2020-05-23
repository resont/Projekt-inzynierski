package com.ProjektInzynierski.BackEnd.processors.survey;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import com.ProjektInzynierski.BackEnd.data.model.SurveyAnsweredData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyToUserData;
import com.ProjektInzynierski.BackEnd.enums.SurveyToUserUpdateMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyToUserRepository;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UsersAndSurveyHandler extends ProcessInterface {

    private Logger logger = LoggerController.getInstance();

    private final SurveyToUserRepository surveyToUserRepository;

    private final UsersRepository usersRepository;

    private final SurveyRepository surveyRepository;

    public UsersAndSurveyHandler(SurveyToUserRepository surveyToUserRepository,
                                 UsersRepository usersRepository,
                                 SurveyRepository surveyRepository) {
        this.surveyToUserRepository = surveyToUserRepository;
        this.usersRepository = usersRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Map<String, String> process(SurveyToUserData surveyToUserData) {

        logger.info("Start of updating process.");

        int surveyToUserId, userId, surveyId;
        String answered;
        try {
            for (SurveyAnsweredData element : surveyToUserData.getData()) {
                userId = element.getUserId();
                surveyId = element.getSurveyId();
                answered = element.getAnswered();

                SurveyToUser surveyToUser = surveyToUserRepository.findAnsweredValue(userId, surveyId);

                if (surveyToUser != null) {

                    surveyToUserId = surveyToUser.getId();

                } else {

                    SurveyToUser emptySurveyToUser = new SurveyToUser();

                    SurveyToUser newElement = surveyToUserRepository.save(emptySurveyToUser);
                    surveyToUserId = newElement.getId();
                    surveyToUserRepository.addSurveyToUser(userId, surveyId, surveyToUserId);
                }

                if (answered.equals("true")) {
                    surveyToUserRepository.updateAnswered(surveyToUserId, true);
                } else if (answered.equals("false")) {
                    surveyToUserRepository.updateAnswered(surveyToUserId, false);
                } else if (answered.equals("no data")) {
                    surveyToUserRepository.deleteById(surveyToUserId);
                }
            }

            logger.info("Updating successful");
            return ResultMap.createSuccessMap(SurveyToUserUpdateMsg.UPDATE_SUCCESS.getErrorMsg());

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Updating went wrong, unexpected error.");
            return ResultMap.createErrorMap(SurveyToUserUpdateMsg.UPDATE_UNEXPECTED_ERROR.getErrorMsg());
        }
    }
}
