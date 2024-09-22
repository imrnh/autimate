package com.thjavafest.wigglewonders.Wigglewonders.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class QuestionExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Integer> questionAnswers;

    public QuestionExamEntity() {}

    public QuestionExamEntity(List<Integer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionAnswers(List<Integer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
