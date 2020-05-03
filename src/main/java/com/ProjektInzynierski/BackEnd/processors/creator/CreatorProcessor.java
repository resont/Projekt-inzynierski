package com.ProjektInzynierski.BackEnd.processors.creator;

import com.ProjektInzynierski.BackEnd.controller.LoggerController;
import com.ProjektInzynierski.BackEnd.data.entity.Answers;
import com.ProjektInzynierski.BackEnd.data.entity.Questions;
import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import com.ProjektInzynierski.BackEnd.data.model.QuestionData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyDetailsData;
import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import com.ProjektInzynierski.BackEnd.processors.ProcessInterface;
import com.ProjektInzynierski.BackEnd.repository.AnswersRepository;
import com.ProjektInzynierski.BackEnd.repository.QuestionRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import com.ProjektInzynierski.BackEnd.util.AnswerRep;
import com.ProjektInzynierski.BackEnd.util.Iterator;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreatorProcessor extends ProcessInterface {

    private Logger logger = LoggerController.getInstance();

    private ResultMap resultMap = new ResultMap();

    private final SurveyRepository surveyRepository;

    private final QuestionRepository questionRepository;

    private final AnswersRepository answersRepository;

    public CreatorProcessor(SurveyRepository surveyRepository, QuestionRepository questionRepository, AnswersRepository answersRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
    }

    @Override
    public Map<String, String> process(SurveyDetailsData surveyDetailsData) {

        logger.info("Start of creation process.");
        try {
            Survey survey = saveSurvey(surveyDetailsData);
            for (QuestionData element : surveyDetailsData.getQuestions()) {
                Questions questions = new Questions();
                questions.setType(element.getType());
                questions.setQuestion(element.getQuestion());
                questions.setSurvey(survey);
                Questions question = questionRepository.save(questions);
                if (element.getAnswers() != null) {

                    AnswerRep answers = new AnswerRep();
                    answers.setAnswers(element.getAnswers());

                    for (Iterator iter = answers.getIterator(); iter.hasNext();) {
                        String string = (String)iter.next();
                        if (!string.equals("") && !string.equals(" ")) {
                            Answers answersObj = new Answers();
                            answersObj.setAnswer(string);
                            answersObj.setQuestion(question);
                            answersRepository.save(answersObj);
                        }
                    }
                }
            }
            logger.info("Creation successful.");
            return resultMap.createSuccessMap("Pomyślnie dodano ankietę.");
        } catch (Exception e) {
            logger.error("Creation went wrong.");
            return resultMap.createErrorMap(LoginMsg.WRONG_EMAIL_OR_PASSWORD.getErrorMsg());
        }
    }

    private Survey saveSurvey(SurveyDetailsData surveyDetailsData) {
        Survey survey = new Survey();
        survey.setTopic(surveyDetailsData.getTitle());
        survey.setDescription(surveyDetailsData.getDescription());
        return surveyRepository.save(survey);
    }
}
