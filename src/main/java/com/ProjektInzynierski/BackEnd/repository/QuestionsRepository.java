package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.datamodel.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer>, JpaSpecificationExecutor<Questions> {

    List<Questions> findByIdAndQuestion(int id, String question);

    Questions findById(int id);

    @Query("SELECT q FROM Questions q WHERE q.question=:question")
    Questions findValidQuestionByTopic(String question);
}
