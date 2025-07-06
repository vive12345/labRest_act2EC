// SurveyInstanceRepository.java
package com.survey.repository;

import com.survey.model.SurveyInstance;
import com.survey.model.SurveyInstanceState;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SurveyInstanceRepository {
    private final Map<String, SurveyInstance> surveyInstances = new ConcurrentHashMap<>();

    public SurveyInstance save(SurveyInstance surveyInstance) {
        surveyInstances.put(surveyInstance.getId(), surveyInstance);
        return surveyInstance;
    }

    public Optional<SurveyInstance> findById(String id) {
        return Optional.ofNullable(surveyInstances.get(id));
    }

    public List<SurveyInstance> findAll() {
        return new ArrayList<>(surveyInstances.values());
    }

    public List<SurveyInstance> findByState(SurveyInstanceState state) {
        return surveyInstances.values().stream()
                .filter(instance -> instance.getState() == state)
                .toList();
    }

    public boolean existsById(String id) {
        return surveyInstances.containsKey(id);
    }
}