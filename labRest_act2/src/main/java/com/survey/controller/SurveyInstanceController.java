// SurveyInstanceController.java
package com.survey.controller;

import com.survey.model.*;
import com.survey.service.SurveyInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @api {post} /api/survey-instances Create Survey Instance
 * @apiName CreateSurveyInstance
 * @apiGroup SurveyInstances
 * @apiDescription Creates a new survey instance for a user
 * 
 * @apiParam {String} surveyId Survey ID to create instance from
 * @apiParam {String} userName User name taking the survey
 * 
 * @apiParamExample {json} Request-Example:
 *                  {
 *                  "surveyId": "123e4567-e89b-12d3-a456-426614174000",
 *                  "userName": "john_doe"
 *                  }
 * 
 * @apiSuccess {Object} surveyInstance Created survey instance
 * @apiError 404 Survey not found
 * @apiError 400 Survey not in COMPLETED state
 */
@RestController
@RequestMapping("/api/survey-instances")
public class SurveyInstanceController {

    @Autowired
    private SurveyInstanceService surveyInstanceService;

    // API 6: Create a survey instance of a survey for a user
    @PostMapping
    public ResponseEntity<SurveyInstance> createSurveyInstance(@RequestBody Map<String, String> request) {
        try {
            String surveyId = request.get("surveyId");
            String userName = request.get("userName");

            SurveyInstance instance = surveyInstanceService.createSurveyInstance(surveyId, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(instance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @api {post} /api/survey-instances/{instanceId}/answers Submit Answer
     * @apiName SubmitAnswer
     * @apiGroup SurveyInstances
     * @apiDescription Submits an answer for a survey item instance
     * 
     * @apiParam {String} instanceId Survey instance ID
     * @apiParam {String} itemInstanceId Survey item instance ID
     * @apiParam {String} answer Selected answer
     * 
     * @apiParamExample {json} Request-Example:
     *                  {
     *                  "itemInstanceId": "item-instance-123",
     *                  "answer": "Paris"
     *                  }
     * 
     * @apiSuccess {Object} surveyInstance Updated survey instance
     * @apiError 404 Survey instance or item instance not found
     * @apiError 400 Survey instance already completed
     */
    // API 7: Accept an answer for a survey item instance
    @PostMapping("/{instanceId}/answers")
    public ResponseEntity<SurveyInstance> submitAnswer(
            @PathVariable String instanceId,
            @RequestBody Map<String, String> request) {
        try {
            String itemInstanceId = request.get("itemInstanceId");
            String answer = request.get("answer");

            SurveyInstance updated = surveyInstanceService.answerSurveyItemInstance(
                    instanceId, itemInstanceId, answer);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @api {get} /api/survey-instances Get Survey Instances
     * @apiName GetSurveyInstances
     * @apiGroup SurveyInstances
     * @apiDescription Retrieves survey instances, optionally filtered by state
     * 
     * @apiParam {String} [state] Filter by state (CREATED, IN_PROGRESS, COMPLETED)
     * 
     * @apiSuccess {Object[]} surveyInstances Array of survey instances
     * @apiSuccessExample Success-Response:
     *                    HTTP/1.1 200 OK
     *                    [
     *                    {
     *                    "id": "instance-123",
     *                    "surveyId": "survey-456",
     *                    "userName": "john_doe",
     *                    "state": "IN_PROGRESS",
     *                    "surveyItemInstances": [...]
     *                    }
     *                    ]
     */
    // API 8: Retrieve survey instances by state (or all if no state given)
    @GetMapping
    public ResponseEntity<List<SurveyInstance>> getSurveyInstances(
            @RequestParam(required = false) String state) {
        try {
            List<SurveyInstance> instances;
            if (state != null) {
                SurveyInstanceState instanceState = SurveyInstanceState.valueOf(state.toUpperCase());
                instances = surveyInstanceService.getSurveyInstancesByState(instanceState);
            } else {
                instances = surveyInstanceService.getAllSurveyInstances();
            }
            return ResponseEntity.ok(instances);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @api {get} /api/survey-instances/{id} Get Specific Survey Instance
     * @apiName GetSurveyInstance
     * @apiGroup SurveyInstances
     * @apiDescription Retrieves a specific survey instance with all survey item
     *                 instances
     * 
     * @apiParam {String} id Survey instance unique ID
     * 
     * @apiSuccess {Object} surveyInstance Survey instance with all item instances
     * @apiError 404 Survey instance not found
     */
    // API 9: Retrieve a specific survey instance with all survey item instances
    @GetMapping("/{id}")
    public ResponseEntity<SurveyInstance> getSurveyInstance(@PathVariable String id) {
        Optional<SurveyInstance> instance = surveyInstanceService.getSurveyInstanceById(id);
        return instance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}