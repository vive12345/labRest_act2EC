# Curl Commands for Testing All APIs

## API 1: Create Survey Item

```bash
curl -X POST http://localhost:8080/api/survey-items \
  -H "Content-Type: application/json" \
  -d '{
    "questionStem": "What is 5 + 5?",
    "candidateAnswers": ["8", "9", "10", "11"],
    "correctAnswer": "10"
  }'
```

## API 2: Create Survey

```bash
curl -X POST http://localhost:8080/api/surveys \
  -H "Content-Type: application/json" \
  -d '{"title": "Test Quiz"}'
```

## API 3: Add Survey Item to Survey

```bash
curl -X POST http://localhost:8080/api/surveys/id_here/items \
  -H "Content-Type: application/json" \
  -d '{"surveyItemId": "id_here"}'
```

## API 4: Get All Surveys

```bash
curl http://localhost:8080/api/surveys
```

## API 5: Get Specific Survey

```bash
curl http://localhost:8080/api/surveys/id_here
```

## API 6: Create Survey Instance

```bash
curl -X POST http://localhost:8080/api/survey-instances \
  -H "Content-Type: application/json" \
  -d '{
    "surveyId": "id_here",
    "userName": "testuser123"
  }'
```

## API 7: Submit Answer

```bash
curl -X POST http://localhost:8080/api/survey-instances/id_here/answers \
  -H "Content-Type: application/json" \
  -d '{
    "itemInstanceId": "id_here",
    "answer": "4"
  }'
```

## API 8: Get Survey Instances by State

```bash
# All instances
curl http://localhost:8080/api/survey-instances

# Filter by state
curl "http://localhost:8080/api/survey-instances?state=IN_PROGRESS"
```

## API 9: Get Specific Survey Instance

```bash
curl http://localhost:8080/api/survey-instances/id_here
```

## API 10: Delete Survey

```bash
curl -X DELETE http://localhost:8080/api/surveys/id_here
```

## Verify Delete

```bash
curl http://localhost:8080/api/surveys
```
