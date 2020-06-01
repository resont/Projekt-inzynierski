package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.data.entity.Answers;
import com.ProjektInzynierski.BackEnd.data.entity.KeyEntity;
import com.ProjektInzynierski.BackEnd.data.entity.Questions;
import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.AnswerDetailsData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyDetailsData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyToUserData;
import com.ProjektInzynierski.BackEnd.processors.creator.CreatorProcessor;
import com.ProjektInzynierski.BackEnd.processors.creator.QuestionAndAnswerIdHandler;
import com.ProjektInzynierski.BackEnd.processors.survey.UsersAndSurveyHandler;
import com.ProjektInzynierski.BackEnd.repository.AnswersRepository;
import com.ProjektInzynierski.BackEnd.repository.KeyRepository;
import com.ProjektInzynierski.BackEnd.repository.KeyToAnswerRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyToUserRepository;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import com.ProjektInzynierski.BackEnd.util.StringHashCreator;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for handling every http request regarding survey data
 */
@CrossOrigin
@RestController
public class SurveyController {

    public static final String NO_DATA_MSG = "no data";
    public static final String KEY_ERROR_MSG = "Klucze nie pokrywają się! Nastąpiła manipulacja odpowiedziami!";
    public static final String KEY_SUCCESS_MSG = "Twoje odpowiedzi zgadzają się z danymi w bazie danych.";

    private Logger logger = LoggerController.getInstance();

    private final CreatorProcessor creatorProcessor;

    private final SurveyRepository surveyRepository;

    private final AnswersRepository answersRepository;

    private final QuestionAndAnswerIdHandler questionAndAnswerIdHandler;

    private final KeyRepository keyRepository;

    private final KeyToAnswerRepository keyToAnswerRepository;

    private final UsersRepository usersRepository;

    private final SurveyToUserRepository surveyToUserRepository;

    private final UsersAndSurveyHandler usersAndSurveyHandler;

    public SurveyController(CreatorProcessor creatorProcessor,
                            SurveyRepository surveyRepository,
                            AnswersRepository answersRepository,
                            QuestionAndAnswerIdHandler questionAndAnswerIdHandler,
                            UsersRepository usersRepository,
                            SurveyToUserRepository surveyToUserRepository,
                            UsersAndSurveyHandler usersAndSurveyHandler,
                            KeyRepository keyRepository,
                            KeyToAnswerRepository keyToAnswerRepository) {
        this.creatorProcessor = creatorProcessor;
        this.surveyRepository = surveyRepository;
        this.answersRepository = answersRepository;
        this.questionAndAnswerIdHandler = questionAndAnswerIdHandler;
        this.keyRepository = keyRepository;
        this.keyToAnswerRepository = keyToAnswerRepository;
        this.usersRepository = usersRepository;
        this.surveyToUserRepository = surveyToUserRepository;
        this.usersAndSurveyHandler = usersAndSurveyHandler;
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Up", HttpStatus.OK);
    }

    @GetMapping("/survey/all")
    List<Survey> getAllSurveysFromDatabase() {
        return this.surveyRepository.findAll();
    }

    @GetMapping("/survey/search")
    Survey search(@RequestParam(name = "topic") String topic) {
        return this.surveyRepository.findValidSurveyByTopic(topic);
    }

    @GetMapping("/survey/{id}")
    Survey show(@PathVariable("id") String id) {
        int blogId = Integer.parseInt(id);
        return this.surveyRepository.findById(blogId);
    }

    @PostMapping("/survey")
    Survey create(@RequestBody Map<String, String> body) {
        Survey survey = new Survey();
        survey.setTopic(body.get("topic"));
        survey.setDescription(body.get("description"));
        return this.surveyRepository.save(survey);
    }

    @PutMapping("/survey/{id}")
    Survey update(@PathVariable String id, @RequestBody Map<String, String> body) {
        Survey survey = this.surveyRepository.findById(Integer.parseInt(id));
        survey.setTopic(body.get("topic"));
        survey.setDescription(body.get("description"));
        return this.surveyRepository.save(survey);
    }

    @DeleteMapping("/survey/{id}")
    boolean delete(@PathVariable String id) {
        Survey survey = new Survey();
        survey.setId(Integer.parseInt(id));
        this.surveyRepository.delete(survey);
        return true;
    }

    @GetMapping("/con_us_su/{uuid}")
    int[] showCon(@PathVariable("uuid") String uuid) {
        return this.surveyRepository.findSurveysWithUserUuid(uuid);
    }

    @GetMapping("/answered/{uuid}")
    int[] showConAns(@PathVariable("uuid") String uuid) {
        return this.surveyRepository.findAnsweredSurveysWithUserUuid(uuid);
    }

    @PostMapping("/con_us_su")
    Map<String, String> updateAnswer(@RequestBody Map<String, String> body) {
        int id = this.surveyRepository.findIdByUuidAndSurveyId(body.get("token"), Integer.parseInt(body.get("surveyId")));
        this.surveyRepository.updateAnswer(id);
        return ResultMap.createSuccessMap("Survey answered.");
    }

    @PostMapping("/surveyCreator")
    Map<String, String> createSurvey(@RequestBody SurveyDetailsData body) {
        if (body != null) {
            return creatorProcessor.process(body);
        } else return ResultMap.createNullBodyErrorMap();
    }

    @PostMapping("/answerKey")
    Map<Integer, Questions> getAnswersByKey(@RequestBody Map<String, String> body) {
        List<Answers> answers = answersRepository.findAnswersByKey(body.get("key"));
        Map<Integer, Questions> map = new HashMap<>();

        UserEntity userEntity = usersRepository.findByUuid(body.get("uuid"));
        KeyEntity keyEntity = keyRepository.findByKey(body.get("key"));
        int[] answerToKeyEntities = keyToAnswerRepository.findAnswerIdWithKeyEntityId(keyEntity.getId());
        String key = StringHashCreator.createSimpleHash(answerToKeyEntities, userEntity.getEmail());
        if (body.get("key").compareTo(key) < 0) {
            key = KEY_ERROR_MSG;
        } else {
            key = KEY_SUCCESS_MSG;
        }

        Answers answers1 = new Answers();
        answers1.setAnswer("");

        Set<Answers> answersSet = new HashSet<>();
        answersSet.add(answers1);

        Questions question = new Questions();
        question.setQuestion(key);
        question.setType(4);
        question.setAnswers(answersSet);
        map.put(0, question);

        for (Answers a : answers) {
            map.put(a.getId(), a.getQuestion());
        }
        return map;
    }

    @PostMapping("/answers")
    Map<String, String> updateAnswersCount(@RequestBody AnswerDetailsData body) {
        if (body != null) {
            return questionAndAnswerIdHandler.process(body);
        } else return ResultMap.createNullBodyErrorMap();
    }

    @GetMapping("/surveyToUser/{id}")
    List<Map<String, String>> getSurveyToUser(@PathVariable("id") String id) {
        int surveyId = Integer.valueOf(id);
        List<UserEntity> users = usersRepository.findAll();
        List<Map<String, String>> list = new ArrayList<>();

        for (UserEntity u : users) {
            Map<String, String> map = new HashMap<>();
            String answer;
            SurveyToUser surveyToUser = surveyToUserRepository.findAnsweredValue(u.getId(), surveyId);
            if (surveyToUser != null) {
                answer = String.valueOf(surveyToUser.isSurveyAnswer());
            } else {
                answer = NO_DATA_MSG;
            }

            map.put("id", Integer.toString(u.getId()));
            map.put("email", u.getEmail());
            map.put("answer", answer);
            list.add(map);
        }

        return list;
    }

    @PostMapping("/updateSurveyToUser")
    Map<String, String> updateSurveyToUser(@RequestBody SurveyToUserData body) {
        if (body != null) {
            return usersAndSurveyHandler.process(body);
        } else {
            return ResultMap.createNullBodyErrorMap();
        }
    }
}
