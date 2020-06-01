package com.ProjektInzynierski.BackEnd.repository;

import com.ProjektInzynierski.BackEnd.data.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class is responsible for providing queries to database regarding questions data
 */
@Repository
public interface QuestionRepository extends JpaRepository<Questions, Integer> {

}
