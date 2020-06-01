package com.ProjektInzynierski.BackEnd.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

//This class is responsible for mapping data from database about question data
@Entity
@Table(name = "Questions")
@Getter
@Setter
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "q_id", unique = true)
    private int id;

    @Column(name = "q_question")
    private String question;

    @Column(name = "q_type")
    private int type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private Set<Answers> answers;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "q_s_id")
    private Survey survey;

    public static final class QuestionsBuilder {
        private int type;

        private String question;

        private Survey survey;

        public QuestionsBuilder setType(int type) {
            this.type = type;
            return this;
        }

        public QuestionsBuilder setQuestion(String question) {
            this.question = question;
            return this;
        }

        public QuestionsBuilder setSurvey(Survey survey) {
            this.survey = survey;
            return this;
        }

        public Questions build() {
            Questions questions = new Questions();
            questions.setType(this.type);
            questions.setQuestion(this.question);
            questions.setSurvey(this.survey);
            return questions;
        }
    }

}
