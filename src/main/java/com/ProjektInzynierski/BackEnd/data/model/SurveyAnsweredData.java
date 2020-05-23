package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyAnsweredData {

    private int userId;

    private int surveyId;

    private String answered;
}
