package com.ProjektInzynierski.BackEnd.controller;

import com.ProjektInzynierski.BackEnd.data.entity.Questions;
import com.ProjektInzynierski.BackEnd.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class QuestionsController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping("/questions/all")
    List<Questions> getAllQuestionsFromDatabase() {
        return this.questionsRepository.findAll();
    }

    @GetMapping("/questions/search")
    Questions search(@RequestParam(name = "question") String question) {
        return this.questionsRepository.findValidQuestionByTopic(question);
    }

    @GetMapping("/questions/{id}")
    Questions show(@PathVariable("id") String id) {
        int q_Id = Integer.parseInt(id);
        return this.questionsRepository.findById(q_Id);
    }

    @PostMapping("/questions")
    Questions create(@RequestBody Map<String, String> body) {
        Questions questions = new Questions();
        questions.setQuestion(body.get("question"));
        questions.setQ_s_id(Integer.parseInt(body.get("q_s_id")));
        return this.questionsRepository.save(questions);
    }

    //TODO possible null value
    @PutMapping("/questions/{id}")
    Questions update(@PathVariable String id, @RequestBody Map<String, String> body) {
        Questions questions = this.questionsRepository.findById(Integer.parseInt(id));
        questions.setQuestion(body.get("question"));
        questions.setQ_s_id(Integer.parseInt(body.get("q_s_id")));
        return this.questionsRepository.save(questions);
    }

    @DeleteMapping("/questions/{id}")
    boolean delete(@PathVariable String id) {
        Questions questions = new Questions();
        questions.setId(Integer.parseInt(id));
        this.questionsRepository.delete(questions);
        return true;
    }
}
