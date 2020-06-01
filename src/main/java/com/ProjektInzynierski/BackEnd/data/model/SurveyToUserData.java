package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is responsible for mapping json data from http request about survey details
 */
@Getter
@Setter
public class SurveyToUserData {

    List<SurveyAnsweredData> data;
}

