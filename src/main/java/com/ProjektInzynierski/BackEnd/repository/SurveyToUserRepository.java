package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SurveyToUserRepository extends JpaRepository<SurveyToUser, Integer> {

    SurveyToUser findById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE SurveyToUser s SET s.userId.id=:uId, s.surveyId.id=:sId WHERE s.id=:id")
    void addSurveyToUser(int uId, int sId, int id);

    @Query("SELECT s.surveyId.id FROM SurveyToUser s WHERE s.userId.id=:uId")
    int[] findAllAnsweredSurveys(int uId);

    @Query("SELECT s FROM SurveyToUser s WHERE s.surveyId.id=:sId AND s.userId.id=:uId")
    SurveyToUser findAnsweredValue(int uId, int sId);

    @Transactional
    @Modifying
    @Query("UPDATE SurveyToUser s SET s.surveyAnswer=:answered WHERE s.id=:id")
    void updateAnswered(int id, boolean answered);

}
