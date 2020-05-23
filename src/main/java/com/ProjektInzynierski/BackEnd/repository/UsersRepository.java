package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmailAndPassword(String email, String password);

    UserEntity findByEmailAndPassword(String email, String password);

    UserEntity findByUuidAndPassword(String token, String password);

    UserEntity findByUuid(String uuid);

    UserEntity findById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u\n" +
            "SET u.uuid=:uuid, u.validTo=:date\n" +
            "WHERE u.email=:email AND u.password=:password")
    int setUuidAndValidToWithPassword(String email, String password, String uuid, Date date);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u\n" +
            "SET u.uuid=NULL, u.validTo=:date\n" +
            "WHERE u.uuid=:uuid")
    void setUuidAndValidTo(String uuid, Date date);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u\n" +
            "SET u.password=:newPassword \n" +
            "WHERE u.password=:oldPassword AND u.uuid=:uuid")
    void setNewPassword(String oldPassword, String newPassword, String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.group=:group WHERE u.id=:id")
    void setAdmin(int id, String group);
}
