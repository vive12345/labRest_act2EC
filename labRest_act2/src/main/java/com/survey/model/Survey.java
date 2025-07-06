package com.survey.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Survey {
    private final String id;
    private String title;
    private final List<String> surveyItemIds;
    private SurveyState state;

    // Primary constructor for JSON deserialization (creating new surveys)
    @JsonCreator
    public Survey(@JsonProperty("title") String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.surveyItemIds = new ArrayList<>();
        this.state = SurveyState.CREATED;
    }

    // Secondary constructor for internal use (when all fields are known)
    public Survey(String id, String title, List<String> surveyItemIds, SurveyState state) {
        this.id = id;
        this.title = title;
        this.surveyItemIds = new ArrayList<>(surveyItemIds);
        this.state = state;
    }

    public void addSurveyItem(String surveyItemId) {
        if (state == SurveyState.DELETED) {
            throw new IllegalStateException("Cannot modify deleted survey");
        }
        if (surveyItemIds.size() >= 5) {
            throw new IllegalStateException("Survey cannot have more than 5 items");
        }
        surveyItemIds.add(surveyItemId);
    }

    public void setState(SurveyState state) {
        if (this.state == SurveyState.DELETED) {
            throw new IllegalStateException("Cannot modify deleted survey");
        }
        this.state = state;
    }

    public void markDeleted() {
        this.state = SurveyState.DELETED;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSurveyItemIds() {
        return new ArrayList<>(surveyItemIds);
    }

    public SurveyState getState() {
        return state;
    }

    public void setTitle(String title) {
        if (state == SurveyState.DELETED) {
            throw new IllegalStateException("Cannot modify deleted survey");
        }
        this.title = title;
    }
}