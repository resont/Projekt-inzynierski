package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import com.ProjektInzynierski.BackEnd.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

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
}
