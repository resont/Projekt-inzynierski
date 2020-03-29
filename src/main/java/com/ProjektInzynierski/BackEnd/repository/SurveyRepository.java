package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.datamodel.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer>, JpaSpecificationExecutor<Survey> {

    List<Survey> findByIdAndTopic(int id, String topic);

    Survey findById(int id);

    @Query("SELECT s FROM Survey s WHERE s.topic=:topic")
    Survey findValidSurveyByTopic(String topic);
}
