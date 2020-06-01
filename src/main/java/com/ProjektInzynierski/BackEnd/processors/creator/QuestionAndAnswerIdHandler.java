package com.ProjektInzynierski.BackEnd.processors.creator;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.AnswerToKeyEntity;
import com.ProjektInzynierski.BackEnd.data.entity.Answers;
import com.ProjektInzynierski.BackEnd.data.entity.KeyEntity;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.AnswerDetailsData;
import com.ProjektInzynierski.BackEnd.data.model.ClosedQuestion;
import com.ProjektInzynierski.BackEnd.data.model.OpenQuestion;
import com.ProjektInzynierski.BackEnd.enums.AnsweringMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.repository.AnswersRepository;
import com.ProjektInzynierski.BackEnd.repository.KeyRepository;
import com.ProjektInzynierski.BackEnd.repository.KeyToAnswerRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import com.ProjektInzynierski.BackEnd.util.StringHashCreator;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class is responsible for handling answering process
 */
@Component
public class QuestionAndAnswerIdHandler extends ProcessInterface {

    private Logger logger = LoggerController.getInstance();

    private final SurveyRepository surveyRepository;

    private final AnswersRepository answersRepository;

    private final KeyToAnswerRepository keyToAnswerRepository;

    private final KeyRepository keyRepository;

    private final UsersRepository usersRepository;

    public QuestionAndAnswerIdHandler(SurveyRepository surveyRepository,
                                      AnswersRepository answersRepository,
                                      KeyToAnswerRepository keyToAnswerRepository,
                                      KeyRepository keyRepository, UsersRepository usersRepository) {
        this.surveyRepository = surveyRepository;
        this.answersRepository = answersRepository;
        this.keyToAnswerRepository = keyToAnswerRepository;
        this.keyRepository = keyRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * This method persist into database answer data created out of json mapped into answerDetailsData.
     *
     * @param answerDetailsData contains data of answers to be persisted.
     * @return Map<String, String> contains data to be returned respectively success or error message.
     */
    @Override
    public Map<String, String> process(AnswerDetailsData answerDetailsData) {

        logger.info("Start of answering process.");
        try {
            if (!answerDetailsData.getOpenQuestions().isEmpty() || !answerDetailsData.getClosedQuestions().isEmpty()) {

                KeyEntity keyEntity = new KeyEntity();
                KeyEntity keyEntitySaved = keyRepository.save(keyEntity);

                if (answerDetailsData.getOpenQuestions() != null) {
                    for (OpenQuestion openQuestion : answerDetailsData.getOpenQuestions()) {
                        String questionId = openQuestion.getQuestionId();

                        Answers answers = new Answers();
                        answers.setAnswer(openQuestion.getAnswer());

                        Answers answer = answersRepository.save(answers);
                        answersRepository.updateAnswer(Integer.valueOf(questionId), answer.getId());
                        updateKeyToAnswer(answer.getId(), keyEntitySaved);
                    }
                }

                if (answerDetailsData.getClosedQuestions() != null) {
                    for (ClosedQuestion closedQuestion : answerDetailsData.getClosedQuestions()) {
                        int answerId = Integer.parseInt(closedQuestion.getAnswerId());

                        surveyRepository.updateCount(answerId);
                        updateKeyToAnswer(answerId, keyEntitySaved);
                    }
                }

                UserEntity userEntity = usersRepository.findByUuid(answerDetailsData.getUuid());

                int[] answerToKeyEntities = keyToAnswerRepository.findAnswerIdWithKeyEntityId(keyEntitySaved.getId());
                String key = StringHashCreator.createSimpleHash(answerToKeyEntities, userEntity.getEmail());
                keyEntitySaved.setKey(key);
                keyRepository.updateKey(keyEntitySaved.getKey(), keyEntitySaved.getId());

                logger.info("Answering successful.");
                return ResultMap.createSuccessMap(keyEntitySaved.getKey());
            }
            logger.error("Answering data is null.");
            return ResultMap.createErrorMap(AnsweringMsg.ANSWERING_ERROR_EMPTY_SURVEY.getErrorMsg());
        } catch (Exception e) {
            logger.error("Answering went wrong.");
            return ResultMap.createErrorMap(AnsweringMsg.ANSWERING_UNEXPECTED_ERROR.getErrorMsg());
        }
    }

    /**
     * This method persist AnswerToKeyEntity (ids) into database to the Conn_an_key table.
     *
     * @param answerId  contains answer id to be persisted.
     * @param keyEntity contains key id to be persisted.
     */
    private void updateKeyToAnswer(int answerId, KeyEntity keyEntity) {
        AnswerToKeyEntity answerToKeyEntity = new AnswerToKeyEntity();
        AnswerToKeyEntity keyToAnswerId = this.keyToAnswerRepository.save(answerToKeyEntity);
        this.keyToAnswerRepository.addAnswerToKey(keyToAnswerId.getId(), keyEntity.getId(), answerId);
    }
}