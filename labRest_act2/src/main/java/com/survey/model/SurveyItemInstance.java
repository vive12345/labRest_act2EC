// SurveyItemInstance.java - Instance of a survey item in a survey instance
package com.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class SurveyItemInstance {
    private final String id;
    private final String surveyItemId;
    private final String questionStem;
    private final java.util.List<String> candidateAnswers;
    private final String correctAnswer;
    private String selectedAnswer;
    private SurveyItemInstanceState state;

    public SurveyItemInstance(SurveyItem surveyItem) {
        this.id = UUID.randomUUID().toString();
        this.surveyItemId = surveyItem.getId();
        this.questionStem = surveyItem.getQuestionStem();
        this.candidateAnswers = surveyItem.getCandidateAnswers();
        this.correctAnswer = surveyItem.getCorrectAnswer();
        this.selectedAnswer = null;
        this.state = SurveyItemInstanceState.NOT_COMPLETED;
    }

    public SurveyItemInstance(@JsonProperty("id") String id,
            @JsonProperty("surveyItemId") String surveyItemId,
            @JsonProperty("questionStem") String questionStem,
            @JsonProperty("candidateAnswers") java.util.List<String> candidateAnswers,
            @JsonProperty("correctAnswer") String correctAnswer,
            @JsonProperty("selectedAnswer") String selectedAnswer,
            @JsonProperty("state") SurveyItemInstanceState state) {
        this.id = id;
        this.surveyItemId = surveyItemId;
        this.questionStem = questionStem;
        this.candidateAnswers = candidateAnswers;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
        this.state = state;
    }

    public void setAnswer(String answer, boolean surveyInstanceCompleted) {
        if (surveyInstanceCompleted) {
            throw new IllegalStateException("Cannot modify completed survey instance");
        }
        this.selectedAnswer = answer;
        this.state = SurveyItemInstanceState.COMPLETED;
    }

    public boolean isCorrect() {
        return selectedAnswer != null && selectedAnswer.equals(correctAnswer);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getSurveyItemId() {
        return surveyItemId;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public java.util.List<String> getCandidateAnswers() {
        return candidateAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public SurveyItemInstanceState getState() {
        return state;
    }
}