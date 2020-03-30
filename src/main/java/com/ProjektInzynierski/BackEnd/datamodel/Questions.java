package com.ProjektInzynierski.BackEnd.datamodel;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Questions")
@Getter
@Setter
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "q_id", unique = true)
    private int id;

    @Column(name = "q_question")
    private String question;

    @Column(name = "q_s_id")
    private int q_s_id;
}
