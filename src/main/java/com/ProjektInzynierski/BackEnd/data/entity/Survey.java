package com.ProjektInzynierski.BackEnd.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * This class is responsible for mapping data from database about survey data
 */
@Entity
@Table(name = "Survey")
@Getter
@Setter
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id", unique = true)
    private int id;

    @Column(name = "s_topic")
    private String topic;

    @Column(name = "s_description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey")
    private Set<Questions> questions;

//    For selecting users attached to survey
//
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "surveyId")
//    private Set<SurveyToUser> surveyToUser;

}
