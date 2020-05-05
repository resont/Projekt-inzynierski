package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.data.entity.Answers;
import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.data.model.SurveyDetailsData;
import com.ProjektInzynierski.BackEnd.processors.creator.CreatorProcessor;
import com.ProjektInzynierski.BackEnd.repository.AnswersRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import com.ProjektInzynierski.BackEnd.repository.SurveyToUserRepository;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
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

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class SurveyController {

    private Logger logger = LoggerController.getInstance();

    private ResultMap resultMap = new ResultMap();

    private CreatorProcessor creatorProcessor;

    private final SurveyRepository surveyRepository;

    private final AnswersRepository answersRepository;

    private final UsersRepository usersRepository;

    private final SurveyToUserRepository surveyToUserRepository;

    public SurveyController(CreatorProcessor creatorProcessor,
                            SurveyRepository surveyRepository,
                            AnswersRepository answersRepository,
                            UsersRepository usersRepository,
                            SurveyToUserRepository surveyToUserRepository) {
        this.creatorProcessor = creatorProcessor;
        this.surveyRepository = surveyRepository;
        this.answersRepository = answersRepository;
        this.usersRepository = usersRepository;
        this.surveyToUserRepository = surveyToUserRepository;
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

    @PostMapping("/answer/{id}")
    Map<String, String> update(@PathVariable("id") String id) {
        int answerId = Integer.parseInt(id);
        this.surveyRepository.updateCount(answerId);
        return resultMap.createSuccessMap("Survey send!");
    }

    @GetMapping("/con_us_su/{uuid}")
    int[] showCon(@PathVariable("uuid") String uuid) {
        return this.surveyRepository.findSurveysByUserUuid(uuid);
    }

    @PostMapping("/con_us_su")
    Map<String, String> updateAnswer(@RequestBody Map<String, String> body) {
        int id = this.surveyRepository.findIdByUuidAndSurveyId(body.get("token"), Integer.parseInt(body.get("surveyId")));
        this.surveyRepository.updateAnswer(id);
        return resultMap.createSuccessMap("Survey answered.");
    }

    @PostMapping("/answer")
    Map<String, String> insertAnswer(@RequestBody Map<String, String> body) {
        Answers answers = new Answers();
        answers.setAnswer(body.get("answer"));
        Answers answer = this.answersRepository.save(answers);
        String questionId = body.get("questionID");
        this.answersRepository.updateAnswer(Integer.valueOf(questionId), answer.getId());
        return resultMap.createSuccessMap("Survey answered.");
    }

    @PostMapping("/surveyCreator")
    Map<String, String> createSurvey(@RequestBody SurveyDetailsData body) {
        if (body != null) {
            return creatorProcessor.process(body);
        } else return resultMap.createNullBodyErrorMap();
    }

    @GetMapping("/users/all")
    List<UserEntity> getAllUsersFromDatabase() {
        return this.usersRepository.findAll();
    }

//    @GetMapping("/unansweredSurveys/{id}")
//    Set<Survey> getAllUnasweredSurveys(@PathVariable("id") String id){
//        int[] ids = this.surveyToUserRepository.findAllAnsweredSurveys(Integer.parseInt(id));
//        Set<Survey> surveys = new HashSet<>();
//        for(int i: ids){
//            surveys.add(this.surveyRepository.findSurveyForUser(i));
//        }
//        return surveys;
//    }

    @PostMapping("/admin")
    Map<String, String> setAdmin(@RequestBody Map<String, String> body) {
        this.usersRepository.setAdmin(Integer.parseInt(body.get("id")), body.get("group"));
        return resultMap.createSuccessMap("Group updated.");
    }

    @PostMapping("/surveyToUser")
    Map<String, String> setSurveyToUser(@RequestBody Map<String, String> body) {
        SurveyToUser surveyToUser = new SurveyToUser();
        surveyToUser.setSurveyAnswer(false);
        SurveyToUser surveyToUser1 = this.surveyToUserRepository.save(surveyToUser);
        int uId = Integer.parseInt(body.get("uId"));
        int sId = Integer.parseInt(body.get("sId"));
        this.surveyToUserRepository.addSurveyToUser(uId, sId, surveyToUser1.getId());
        return resultMap.createSuccessMap("Survey add to user.");
    }


}
