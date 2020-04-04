package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmailAndPassword(String email, String password);

    UserEntity findByEmailAndPassword(String email, String password);

    @Query("UPDATE UserEntity u\n" +
            "SET uuid=:uuid, validTo=:date\n" +
            "WHERE u.email=:email AND u.password=:password")
    UserEntity setUuidAndValidTo(String email, String password, String uuid, Date date);
}
