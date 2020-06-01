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

/**
 * This class is responsible for mapping data from database about connection between answers and keys
 */
@Entity
@Table(name = "Conn_an_key")
@Getter
@Setter
public class AnswerToKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id", unique = true)
    private int id;

    @JsonBackReference
    @ManyToOne
    @MapsId("con_a_id")
    @JoinColumn(name = "con_a_id")
    Answers answersId;

    @JsonBackReference
    @ManyToOne
    @MapsId("con_k_id")
    @JoinColumn(name = "con_k_id")
    KeyEntity keyEntityId;
}
