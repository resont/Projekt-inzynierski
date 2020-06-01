package com.ProjektInzynierski.BackEnd.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//This class is responsible for mapping data from database about answer data
@Entity
@Table(name = "Answers")
@Getter
@Setter
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id", unique = true)
    private int id;

    @Column(name = "a_answer")
    private String answer;

    @Column(name = "a_count")
    private int count;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "a_q_id")
    private Questions question;
}
