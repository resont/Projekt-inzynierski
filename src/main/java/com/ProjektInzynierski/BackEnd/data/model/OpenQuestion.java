package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

//This class is responsible for mapping json data from http request about open question
@Setter
@Getter
public class OpenQuestion {

    private String questionId;

    private String answer;
}
