// SurveyInstance.java - Instance of a survey for a user
package com.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SurveyInstance {
    private final String id;
    private final String surveyId;
    private final String userName;
    private final List<SurveyItemInstance> surveyItemInstances;
    private SurveyInstanceState state;

    public SurveyInstance(@JsonProperty("surveyId") String surveyId,
            @JsonProperty("userName") String userName,
            List<SurveyItem> surveyItems) {
        this.id = UUID.randomUUID().toString();
        this.surveyId = surveyId;
        this.userName = userName;
        this.surveyItemInstances = new ArrayList<>();
        this.state = SurveyInstanceState.CREATED;

        // Create instances for each survey item
        for (SurveyItem item : surveyItems) {
            this.surveyItemInstances.add(new SurveyItemInstance(item));
        }
    }

    public SurveyInstance(@JsonProperty("id") String id,
            @JsonProperty("surveyId") String surveyId,
            @JsonProperty("userName") String userName,
            @JsonProperty("surveyItemInstances") List<SurveyItemInstance> surveyItemInstances,
            @JsonProperty("state") SurveyInstanceState state) {
        this.id = id;
        this.surveyId = surveyId;
        this.userName = userName;
        this.surveyItemInstances = new ArrayList<>(surveyItemInstances);
        this.state = state;
    }

    public void answerItem(String itemInstanceId, String answer) {
        if (state == SurveyInstanceState.COMPLETED) {
            throw new IllegalStateException("Cannot modify completed survey instance");
        }

        SurveyItemInstance instance = surveyItemInstances.stream()
                .filter(si -> si.getId().equals(itemInstanceId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Survey item instance not found"));

        instance.setAnswer(answer, false);
        updateState();
    }

    private void updateState() {
        long completedCount = surveyItemInstances.stream()
                .filter(si -> si.getState() == SurveyItemInstanceState.COMPLETED)
                .count();

        if (completedCount == 0) {
            state = SurveyInstanceState.CREATED;
        } else if (completedCount == surveyItemInstances.size()) {
            state = SurveyInstanceState.COMPLETED;
        } else {
            state = SurveyInstanceState.IN_PROGRESS;
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public String getUserName() {
        return userName;
    }

    public List<SurveyItemInstance> getSurveyItemInstances() {
        return new ArrayList<>(surveyItemInstances);
    }

    public SurveyInstanceState getState() {
        return state;
    }
}