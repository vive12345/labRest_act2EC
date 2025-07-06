// SurveyController.java - CORRECTED VERSION
package com.survey.controller;

import com.survey.model.*;
import com.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @api {post} /api/surveys Create Survey
 * @apiName CreateSurvey
 * @apiGroup Surveys
 * @apiDescription Creates a new survey
 * 
 * @apiParam {String} title The survey title
 * 
 * @apiSuccess {String} id Unique identifier for the survey
 * @apiSuccess {String} title The survey title
 * @apiSuccess {String[]} surveyItemIds Array of survey item IDs
 * @apiSuccess {String} state Survey state (CREATED, COMPLETED, DELETED)
 * 
 * @apiSuccessExample Success-Response:
 *                    HTTP/1.1 201 Created
 *                    {
 *                    "id": "123e4567-e89b-12d3-a456-426614174000",
 *                    "title": "Math Quiz",
 *                    "surveyItemIds": [],
 *                    "state": "CREATED"
 *                    }
 */
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // API 2: Create a survey
    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        try {
            Survey created = surveyService.createSurvey(survey);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @api {post} /api/surveys/{surveyId}/items Add Survey Item to Survey
     * @apiName AddSurveyItemToSurvey
     * @apiGroup Surveys
     * @apiDescription Adds a survey item to an existing survey
     * 
     * @apiParam {String} surveyId Survey unique ID
     * @apiParam {String} surveyItemId Survey item ID to add
     * 
     * @apiParamExample {json} Request-Example:
     *                  {
     *                  "surveyItemId": "123e4567-e89b-12d3-a456-426614174000"
     *                  }
     * 
     * @apiSuccess {Object} survey Updated survey object
     * @apiError 404 Survey or Survey Item not found
     * @apiError 400 Cannot add item (survey deleted or at capacity)
     */
    // API 3: Add a survey item to a survey
    @PostMapping("/{surveyId}/items")
    public ResponseEntity<Survey> addSurveyItemToSurvey(
            @PathVariable String surveyId,
            @RequestBody Map<String, String> request) {
        try {
            String surveyItemId = request.get("surveyItemId");
            Survey updated = surveyService.addSurveyItemToSurvey(surveyId, surveyItemId);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @api {get} /api/surveys Get All Surveys
     * @apiName GetAllSurveys
     * @apiGroup Surveys
     * @apiDescription Retrieves all surveys
     * 
     * @apiSuccess {Object[]} surveys Array of survey objects
     * @apiSuccessExample Success-Response:
     *                    HTTP/1.1 200 OK
     *                    [
     *                    {
     *                    "id": "123e4567-e89b-12d3-a456-426614174000",
     *                    "title": "Math Quiz",
     *                    "surveyItemIds": ["item1", "item2"],
     *                    "state": "COMPLETED"
     *                    }
     *                    ]
     */
    // API 4: Get the set of all surveys
    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    /**
     * @api {get} /api/surveys/{id} Get Specific Survey
     * @apiName GetSurvey
     * @apiGroup Surveys
     * @apiDescription Retrieves a specific survey and its survey items
     * 
     * @apiParam {String} id Survey unique ID
     * 
     * @apiSuccess {Object} survey Survey object with embedded items
     * @apiSuccess {String} survey.id Survey ID
     * @apiSuccess {String} survey.title Survey title
     * @apiSuccess {Object[]} survey.surveyItems Array of survey item objects
     * @apiSuccess {String} survey.state Survey state
     */
    // API 5: Get a specific survey and the set of all survey items in that survey
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSurveyWithItems(@PathVariable String id) {
        try {
            Optional<Survey> surveyOpt = surveyService.getSurveyById(id);
            if (surveyOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Survey survey = surveyOpt.get();
            List<SurveyItem> surveyItems = surveyService.getSurveyItems(id);

            Map<String, Object> response = Map.of(
                    "id", survey.getId(),
                    "title", survey.getTitle(),
                    "state", survey.getState(),
                    "surveyItems", surveyItems);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @api {delete} /api/surveys/{id} Delete Survey
     * @apiName DeleteSurvey
     * @apiGroup Surveys
     * @apiDescription Marks a survey as deleted (soft delete)
     * 
     * @apiParam {String} id Survey unique ID
     * 
     * @apiSuccess 204 No Content - Survey successfully deleted
     * @apiError 404 Survey not found
     */
    // API 10: Delete a specific survey
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable String id) {
        try {
            surveyService.deleteSurvey(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper endpoint to update survey state
    @PutMapping("/{id}/state")
    public ResponseEntity<Survey> updateSurveyState(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        try {
            SurveyState state = SurveyState.valueOf(request.get("state"));
            Survey updated = surveyService.updateSurveyState(id, state);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}