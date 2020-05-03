package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SurveyDetailsData {

    private String title;

    private String description;

    List<QuestionData> questions;
}
