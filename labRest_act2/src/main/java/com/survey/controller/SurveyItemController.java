// SurveyItemController.java - CORRECTED VERSION
package com.survey.controller;

import com.survey.model.SurveyItem;
import com.survey.service.SurveyItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @api {post} /api/survey-items Create Survey Item
 * @apiName CreateSurveyItem
 * @apiGroup SurveyItems
 * @apiDescription Creates a new survey item (MCSA question)
 * 
 * @apiParam {String} questionStem The question text
 * @apiParam {String[]} candidateAnswers Array of possible answers
 * @apiParam {String} correctAnswer The correct answer from candidateAnswers
 * 
 * @apiSuccess {String} id Unique identifier for the survey item
 * @apiSuccess {String} questionStem The question text
 * @apiSuccess {String[]} candidateAnswers Array of possible answers
 * @apiSuccess {String} correctAnswer The correct answer
 * 
 * @apiSuccessExample Success-Response:
 *                    HTTP/1.1 201 Created
 *                    {
 *                    "id": "123e4567-e89b-12d3-a456-426614174000",
 *                    "questionStem": "What is 2 + 2?",
 *                    "candidateAnswers": ["3", "4", "5", "6"],
 *                    "correctAnswer": "4"
 *                    }
 * 
 * @apiError 400 Bad Request - Invalid input data
 */
@RestController
@RequestMapping("/api/survey-items")
public class SurveyItemController {

    @Autowired
    private SurveyItemService surveyItemService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SurveyItem> createSurveyItem(@RequestBody SurveyItem surveyItem) {
        try {
            SurveyItem created = surveyItemService.createSurveyItem(surveyItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SurveyItem>> getAllSurveyItems() {
        List<SurveyItem> items = surveyItemService.getAllSurveyItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyItem> getSurveyItem(@PathVariable String id) {
        Optional<SurveyItem> item = surveyItemService.getSurveyItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}