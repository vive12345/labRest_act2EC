// SurveyItem.java - Immutable MCSA question
package com.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public class SurveyItem {
    private final String id;
    private final String questionStem;
    private final List<String> candidateAnswers;
    private final String correctAnswer;

    public SurveyItem(@JsonProperty("questionStem") String questionStem,
            @JsonProperty("candidateAnswers") List<String> candidateAnswers,
            @JsonProperty("correctAnswer") String correctAnswer) {
        this.id = UUID.randomUUID().toString();
        this.questionStem = questionStem;
        this.candidateAnswers = List.copyOf(candidateAnswers); // Immutable copy
        this.correctAnswer = correctAnswer;
    }

    // Constructor for existing items (e.g., during deserialization)
    public SurveyItem(@JsonProperty("id") String id,
            @JsonProperty("questionStem") String questionStem,
            @JsonProperty("candidateAnswers") List<String> candidateAnswers,
            @JsonProperty("correctAnswer") String correctAnswer) {
        this.id = id;
        this.questionStem = questionStem;
        this.candidateAnswers = List.copyOf(candidateAnswers);
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public List<String> getCandidateAnswers() {
        return candidateAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}