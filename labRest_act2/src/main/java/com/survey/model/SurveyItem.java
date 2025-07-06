package com.survey.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;
import java.util.UUID;

@JacksonXmlRootElement(localName = "surveyItem")
public class SurveyItem {
    @JacksonXmlProperty(isAttribute = true)
    private final String id;

    @JacksonXmlProperty
    private final String questionStem;

    @JsonProperty("candidateAnswers")
    @JacksonXmlElementWrapper(localName = "candidateAnswers")
    @JacksonXmlProperty(localName = "answer")
    private final List<String> candidateAnswers;

    @JacksonXmlProperty
    private final String correctAnswer;

    // Primary constructor for JSON deserialization (creating new items)
    @JsonCreator
    public SurveyItem(@JsonProperty("questionStem") String questionStem,
            @JsonProperty("candidateAnswers") List<String> candidateAnswers,
            @JsonProperty("correctAnswer") String correctAnswer) {
        this.id = UUID.randomUUID().toString();
        this.questionStem = questionStem;
        this.candidateAnswers = List.copyOf(candidateAnswers);
        this.correctAnswer = correctAnswer;
    }

    // Secondary constructor for internal use (when ID is known)
    public SurveyItem(String id, String questionStem, List<String> candidateAnswers, String correctAnswer) {
        this.id = id;
        this.questionStem = questionStem;
        this.candidateAnswers = List.copyOf(candidateAnswers);
        this.correctAnswer = correctAnswer;
    }

    // Getters
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