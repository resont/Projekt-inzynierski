package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is responsible for mapping json data from http request about answered survey
 */
@Getter
@Setter
public class SurveyAnsweredData {

    private int userId;

    private int surveyId;

    private String answered;
}
