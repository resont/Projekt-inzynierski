package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.SurveyToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SurveyToUserRepository extends JpaRepository<SurveyToUser, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE SurveyToUser s SET s.userId.id=:uId, s.surveyId.id=:sId WHERE s.id=:id")
    void addSurveyToUser(int uId, int sId, int id);

    @Query("SELECT s.surveyId.id FROM SurveyToUser s WHERE s.userId.id=:uId")
    int[] findAllAnsweredSurveys(int uId);
}
