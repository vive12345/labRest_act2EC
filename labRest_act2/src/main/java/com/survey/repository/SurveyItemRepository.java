// SurveyItemRepository.java
package com.survey.repository;

import com.survey.model.SurveyItem;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SurveyItemRepository {
    private final Map<String, SurveyItem> surveyItems = new ConcurrentHashMap<>();

    public SurveyItem save(SurveyItem surveyItem) {
        surveyItems.put(surveyItem.getId(), surveyItem);
        return surveyItem;
    }

    public Optional<SurveyItem> findById(String id) {
        return Optional.ofNullable(surveyItems.get(id));
    }

    public List<SurveyItem> findAll() {
        return new ArrayList<>(surveyItems.values());
    }

    public List<SurveyItem> findByIds(List<String> ids) {
        return ids.stream()
                .map(surveyItems::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public boolean existsById(String id) {
        return surveyItems.containsKey(id);
    }
}