package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//This class is responsible for mapping json data from http request about answer details
@Setter
@Getter
public class AnswerDetailsData {

    String uuid;

    List<OpenQuestion> openQuestions;

    List<ClosedQuestion> closedQuestions;
}
