// DataBootstrap.java - Initialize data on startup
package com.survey.repository;

import com.survey.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataBootstrap implements CommandLineRunner {

    @Autowired
    private SurveyItemRepository surveyItemRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyInstanceRepository surveyInstanceRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create 5 survey items
        SurveyItem item1 = new SurveyItem("What is 2 + 2?",
                Arrays.asList("3", "4", "5", "6"), "4");
        SurveyItem item2 = new SurveyItem("What is the capital of France?",
                Arrays.asList("London", "Berlin", "Paris", "Madrid"), "Paris");
        SurveyItem item3 = new SurveyItem("Which planet is closest to the Sun?",
                Arrays.asList("Venus", "Earth", "Mercury", "Mars"), "Mercury");
        SurveyItem item4 = new SurveyItem("What is the largest ocean?",
                Arrays.asList("Atlantic", "Pacific", "Indian", "Arctic"), "Pacific");
        SurveyItem item5 = new SurveyItem("Who wrote 'Romeo and Juliet'?",
                Arrays.asList("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"),
                "William Shakespeare");

        surveyItemRepository.save(item1);
        surveyItemRepository.save(item2);
        surveyItemRepository.save(item3);
        surveyItemRepository.save(item4);
        surveyItemRepository.save(item5);

        // Create 2 surveys
        Survey survey1 = new Survey("Math and Geography Quiz");
        survey1.addSurveyItem(item1.getId());
        survey1.addSurveyItem(item2.getId());
        survey1.setState(SurveyState.COMPLETED);

        Survey survey2 = new Survey("Science Quiz");
        survey2.addSurveyItem(item3.getId());
        survey2.addSurveyItem(item4.getId());
        survey2.addSurveyItem(item5.getId());

        surveyRepository.save(survey1);
        surveyRepository.save(survey2);

        // Create 1 survey instance
        List<SurveyItem> surveyItems = Arrays.asList(item1, item2);
        SurveyInstance instance1 = new SurveyInstance(survey1.getId(), "testuser", surveyItems);
        surveyInstanceRepository.save(instance1);

        System.out.println("Data bootstrap completed!");
        System.out.println("Created 5 survey items, 2 surveys, and 1 survey instance");
    }
}