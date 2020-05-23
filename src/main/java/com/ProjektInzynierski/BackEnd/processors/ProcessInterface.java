package com.ProjektInzynierski.BackEnd.processors;

import com.ProjektInzynierski.BackEnd.data.model.AnswerDetailsData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyDetailsData;
import com.ProjektInzynierski.BackEnd.data.model.SurveyToUserData;

import java.util.Map;

public abstract class ProcessInterface {

    public Map<String, String> process(Map<String, String> body) {
        return null;
    }

    public Map<String, String> process(SurveyDetailsData surveyDetailsData) {
        return null;
    }

    public Map<String, String> process(AnswerDetailsData answerDetailsData) {
        return null;
    }

    public Map<String, String> process(SurveyToUserData surveyToUserData) {
        return null;
    }

}
