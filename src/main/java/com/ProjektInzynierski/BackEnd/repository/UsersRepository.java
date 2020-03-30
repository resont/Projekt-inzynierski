package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.datamodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    boolean existsByEmailAndPassword(String email, String password);
}
