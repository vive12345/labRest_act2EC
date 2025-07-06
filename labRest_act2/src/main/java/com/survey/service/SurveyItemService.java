// SurveyItemService.java
package com.survey.service;

import com.survey.model.SurveyItem;
import com.survey.repository.SurveyItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyItemService {

    @Autowired
    private SurveyItemRepository surveyItemRepository;

    public SurveyItem createSurveyItem(SurveyItem surveyItem) {
        return surveyItemRepository.save(surveyItem);
    }

    public Optional<SurveyItem> getSurveyItemById(String id) {
        return surveyItemRepository.findById(id);
    }

    public List<SurveyItem> getAllSurveyItems() {
        return surveyItemRepository.findAll();
    }

    public List<SurveyItem> getSurveyItemsByIds(List<String> ids) {
        return surveyItemRepository.findByIds(ids);
    }

    public boolean existsById(String id) {
        return surveyItemRepository.existsById(id);
    }
}