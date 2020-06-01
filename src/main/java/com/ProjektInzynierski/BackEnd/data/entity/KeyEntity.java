package com.ProjektInzynierski.BackEnd.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//This class is responsible for mapping data from database about key data
@Entity
@Table(name = "Keys")
@Getter
@Setter
public class KeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "k_id", unique = true)
    private int id;

    @Column(name = "k_key")
    private String key;
}
