package com.ProjektInzynierski.BackEnd.processors;

import com.ProjektInzynierski.BackEnd.data.model.SurveyDetailsData;

import java.util.Map;

public abstract class ProcessInterface {

    public Map<String, String> process(Map<String, String> body) {
        return null;
    }

    public Map<String, String> process(SurveyDetailsData surveyDetailsData) {
        return null;
    }
}
