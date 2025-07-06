// SurveyInstanceService.java
package com.survey.service;

import com.survey.model.*;
import com.survey.repository.SurveyInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyInstanceService {

    @Autowired
    private SurveyInstanceRepository surveyInstanceRepository;

    @Autowired
    private SurveyService surveyService;

    public SurveyInstance createSurveyInstance(String surveyId, String userName) {
        Optional<Survey> surveyOpt = surveyService.getSurveyById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        Survey survey = surveyOpt.get();
        if (survey.getState() != SurveyState.COMPLETED) {
            throw new IllegalStateException("Cannot create instance from non-completed survey");
        }

        List<SurveyItem> surveyItems = surveyService.getSurveyItems(surveyId);
        SurveyInstance instance = new SurveyInstance(surveyId, userName, surveyItems);
        return surveyInstanceRepository.save(instance);
    }

    public SurveyInstance answerSurveyItemInstance(String instanceId, String itemInstanceId, String answer) {
        Optional<SurveyInstance> instanceOpt = surveyInstanceRepository.findById(instanceId);
        if (instanceOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey instance not found");
        }

        SurveyInstance instance = instanceOpt.get();
        instance.answerItem(itemInstanceId, answer);
        return surveyInstanceRepository.save(instance);
    }

    public List<SurveyInstance> getAllSurveyInstances() {
        return surveyInstanceRepository.findAll();
    }

    public List<SurveyInstance> getSurveyInstancesByState(SurveyInstanceState state) {
        return surveyInstanceRepository.findByState(state);
    }

    public Optional<SurveyInstance> getSurveyInstanceById(String id) {
        return surveyInstanceRepository.findById(id);
    }
}