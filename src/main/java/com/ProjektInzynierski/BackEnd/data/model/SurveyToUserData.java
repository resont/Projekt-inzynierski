package com.ProjektInzynierski.BackEnd.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//This class is responsible for mapping json data from http request about connection between survey and user
@Getter
@Setter
public class SurveyToUserData {

    List<SurveyAnsweredData> data;
}

