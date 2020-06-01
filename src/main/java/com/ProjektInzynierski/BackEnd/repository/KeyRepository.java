package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

//This class is responsible for providing queries to database regarding key data
@Repository
public interface KeyRepository extends JpaRepository<KeyEntity, Integer> {

    KeyEntity findByKey(String key);

    @Transactional
    @Modifying
    @Query("UPDATE KeyEntity k SET k.key=:key WHERE k.id=:id")
    void updateKey(String key, int id);
}

