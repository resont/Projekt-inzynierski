package com.ProjektInzynierski.BackEnd.processors.survey;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import com.ProjektInzynierski.BackEnd.data.model.SurveyAnsweredData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyToUserData;
import com.ProjektInzynierski.BackEnd.enums.SurveyToUserUpdateMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.repository.SurveyToUserRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class is responsible for handling updating connection between user and survey
 */
@Component
public class UsersAndSurveyHandler extends ProcessInterface {

    private Logger logger = LoggerController.getInstance();

    private final SurveyToUserRepository surveyToUserRepository;

    public UsersAndSurveyHandler(SurveyToUserRepository surveyToUserRepository) {
        this.surveyToUserRepository = surveyToUserRepository;
    }

    /**
     * This method persist connection between user and survey into database out of json mapped into surveyToUserData.
     *
     * @param surveyToUserData contains connection data to be persisted.
     * @return Map<String, String> contains data to be returned respectively success or error message.
     */
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

                switch (answered) {
                    case "true":
                        surveyToUserRepository.updateAnswered(surveyToUserId, true);
                        break;
                    case "false":
                        surveyToUserRepository.updateAnswered(surveyToUserId, false);
                        break;
                    case "no data":
                        surveyToUserRepository.deleteById(surveyToUserId);
                        break;
                }
            }

            logger.info("Updating successful");
            return ResultMap.createSuccessMap(SurveyToUserUpdateMsg.UPDATE_SUCCESS.getErrorMsg());

        } catch (Exception e) {
            logger.error("Updating went wrong, unexpected error.");
            return ResultMap.createErrorMap(SurveyToUserUpdateMsg.UPDATE_UNEXPECTED_ERROR.getErrorMsg());
        }
    }
}
