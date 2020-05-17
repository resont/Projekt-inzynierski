package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnswerDetailsData {

    String uuid;

    List<OpenQuestion> openQuestions;

    List<ClosedQuestion> closedQuestions;
}
