package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is responsible for mapping json data from http request about question data
 */
@Setter
@Getter
public class QuestionData {

    int type;

    String question;

    List<String> answers;
}
