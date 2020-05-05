package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    List<Survey> findByIdAndTopic(int id, String topic);

    Survey findById(int id);

    @Query("SELECT s FROM Survey s WHERE s.topic=:topic")
    Survey findValidSurveyByTopic(String topic);

    @Query("SELECT s FROM Survey s WHERE s.id=:id")
    Survey findCompleteSurveyById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Answers a SET a.count = a.count+1 WHERE a.id=:id")
    void updateCount(int id);

    @Query("SELECT con_u_s.surveyId.id FROM SurveyToUser con_u_s WHERE con_u_s.userId.uuid=:uuid AND con_u_s.surveyAnswer = FALSE")
    int[] findSurveysByUserUuid(String uuid);

    @Query("SELECT con_u_s.surveyId.id FROM SurveyToUser con_u_s WHERE con_u_s.userId.uuid=:uuid AND con_u_s.surveyAnswer = TRUE")
    int[] findAnsweredSurveysByUserUuid(String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE SurveyToUser con_u_s SET con_u_s.surveyAnswer = TRUE WHERE con_u_s.id=:id")
    void updateAnswer(int id);

    @Query("SELECT s.id FROM SurveyToUser s WHERE s.userId.uuid=:uuid AND s.surveyId.id=:surveyId")
    int findIdByUuidAndSurveyId(String uuid, int surveyId);

    @Query("SELECT s FROM Survey s WHERE s.id<>:id")
    Survey findSurveyForUser(int id);
}
