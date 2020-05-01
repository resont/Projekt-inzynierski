package com.ProjektInzynierski.BackEnd.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SurveyToUserKey implements Serializable {

    @Column(name = "con_u_id")
    int userId;

    @Column(name = "con_s_id")
    int surveyId;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
