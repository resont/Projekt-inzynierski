package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.AnswerToKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface KeyToAnswerRepository extends JpaRepository<AnswerToKeyEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE AnswerToKeyEntity a SET a.keyEntityId.id=:kId, a.answersId.id=:aId WHERE a.id=:id")
    void addAnswerToKey(int id, int kId, int aId);

    @Query("SELECT a.answersId.id FROM AnswerToKeyEntity a WHERE a.keyEntityId.id=:keyId")
    int[] findAnswerIdWithKeyEntityId(int keyId);
}
