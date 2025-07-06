// SurveyRepository.java
package com.survey.repository;

import com.survey.model.Survey;
import com.survey.model.SurveyState;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SurveyRepository {
    private final Map<String, Survey> surveys = new ConcurrentHashMap<>();

    public Survey save(Survey survey) {
        surveys.put(survey.getId(), survey);
        return survey;
    }

    public Optional<Survey> findById(String id) {
        return Optional.ofNullable(surveys.get(id));
    }

    public List<Survey> findAll() {
        return new ArrayList<>(surveys.values());
    }

    public List<Survey> findByState(SurveyState state) {
        return surveys.values().stream()
                .filter(survey -> survey.getState() == state)
                .toList();
    }

    public boolean existsById(String id) {
        return surveys.containsKey(id);
    }

    public void deleteById(String id) {
        Survey survey = surveys.get(id);
        if (survey != null) {
            survey.markDeleted();
        }
    }
}