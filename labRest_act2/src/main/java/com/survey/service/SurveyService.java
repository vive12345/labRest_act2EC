// SurveyService.java
package com.survey.service;

import com.survey.model.*;
import com.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyItemService surveyItemService;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey addSurveyItemToSurvey(String surveyId, String surveyItemId) {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        if (!surveyItemService.existsById(surveyItemId)) {
            throw new IllegalArgumentException("Survey item not found");
        }

        Survey survey = surveyOpt.get();
        survey.addSurveyItem(surveyItemId);
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(String id) {
        return surveyRepository.findById(id);
    }

    public List<SurveyItem> getSurveyItems(String surveyId) {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        Survey survey = surveyOpt.get();
        return surveyItemService.getSurveyItemsByIds(survey.getSurveyItemIds());
    }

    public void deleteSurvey(String id) {
        if (!surveyRepository.existsById(id)) {
            throw new IllegalArgumentException("Survey not found");
        }
        surveyRepository.deleteById(id);
    }

    public Survey updateSurveyState(String surveyId, SurveyState state) {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        Survey survey = surveyOpt.get();
        survey.setState(state);
        return surveyRepository.save(survey);
    }
}