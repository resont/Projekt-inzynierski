package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.processors.login.AuthProcessor;
import com.ProjektInzynierski.BackEnd.repository.SurveyToUserRepository;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import com.ProjektInzynierski.BackEnd.util.ResultMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This class is responsible for handling every http request regarding user data
@CrossOrigin
@RestController
public class UsersController {

    private final UsersRepository usersRepository;

    private final SurveyToUserRepository surveyToUserRepository;

    private final AuthProcessor authProcessor;

    public UsersController(UsersRepository usersRepository,
                           SurveyToUserRepository surveyToUserRepository,
                           AuthProcessor authProcessor) {
        this.usersRepository = usersRepository;
        this.surveyToUserRepository = surveyToUserRepository;
        this.authProcessor = authProcessor;
    }

    @GetMapping("/users/all")
    List<UserEntity> getAllUsersFromDatabase() {
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{uuid}")
    Map<String, String> getUser(@PathVariable("uuid") String uuid) {
        if (uuid == null || uuid.equals(" "))
            return ResultMap.createErrorMap("Error uuid is null.");
        Map<String, String> user = new HashMap<>();
        UserEntity userEntity = this.usersRepository.findByUuid(uuid);
        if (userEntity != null) {
            user.put("email", userEntity.getEmail());
            user.put("group", userEntity.getGroup());
            return user;
        }
        return ResultMap.createErrorMap("Error getUser.");
    }

    @PostMapping("/admin")
    Map<String, String> setAdmin(@RequestBody Map<String, String> body) {
        this.usersRepository.setAdmin(Integer.parseInt(body.get("id")), body.get("group"));
        return ResultMap.createSuccessMap("Group updated.");
    }

    @PostMapping("/surveyToUser")
    Map<String, String> setSurveyToUser(@RequestBody Map<String, String> body) {
        SurveyToUser surveyToUser = new SurveyToUser();
        surveyToUser.setSurveyAnswer(false);
        SurveyToUser surveyToUser1 = this.surveyToUserRepository.save(surveyToUser);
        int uId = Integer.parseInt(body.get("uId"));
        int sId = Integer.parseInt(body.get("sId"));
        this.surveyToUserRepository.addSurveyToUser(uId, sId, surveyToUser1.getId());
        return ResultMap.createSuccessMap("Survey add to user.");
    }

    @PostMapping("/auth")
    Map<String, String> checkIfAuth(@RequestBody Map<String, String> body) {
        if (body != null && body.size() != 0)
            return authProcessor.process(body);
        else return ResultMap.createNullBodyErrorMap();
    }

}
