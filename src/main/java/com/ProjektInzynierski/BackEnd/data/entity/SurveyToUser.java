package com.ProjektInzynierski.BackEnd.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

//This class is responsible for mapping data from database about connection between survey and user
@Entity
@Table(name = "Conn_us_su")
@Getter
@Setter
public class SurveyToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id", unique = true)
    private int id;

    @JsonBackReference
    @ManyToOne
    @MapsId("con_u_id")
    @JoinColumn(name = "con_u_id")
    UserEntity userId;

    @JsonBackReference
    @ManyToOne
    @MapsId("con_s_id")
    @JoinColumn(name = "con_s_id")
    Survey surveyId;

    @Column(name = "con_answer")
    private boolean surveyAnswer;

}
