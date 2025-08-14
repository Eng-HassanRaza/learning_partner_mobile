### Mobile API: Implemented Endpoints and Contracts

- **Base Host (dev):** `dashboard.localhost:8000`
- **Base Path:** `/api/v1/`
- **Auth:** Session-based via `POST /api/v1/auth/` (Token auth also enabled at DRF level if you add tokens)
- **Permissions:** All student endpoints require the user to be an authenticated `Student` and enrolled in the target class/session. Non-accessible resources respond with 404/403.

### Authentication
- POST `/api/v1/auth/`
  - Body:
    ```json
    { "username": "...", "password": "..." }
    ```
  - Response:
    ```json
    { "id": 1, "username": "...", "email": "...", "first_name": "...", "last_name": "...", "date_joined": "...", "user_type": "student" }
    ```
- DELETE `/api/v1/auth/` (logout)

### Student dashboard and profile
- GET `/api/v1/student/dashboard/`
- GET `/api/v1/student/profile/`
- PUT `/api/v1/student/profile/` (update `user.first_name/last_name/email` and `student.phone`)

### Classes and sessions
- GET `/api/v1/student/classes/`
- GET `/api/v1/student/class/{class_id}/` (detailed class)
- GET `/api/v1/student/sessions/` (processed only)
- GET `/api/v1/student/class/{class_id}/session/{session_id}/` (session detail)
  - Response contains: `class_session`, `session`, `content`, `gating` (see Gating section)

### Session content by type
All content IDs refer to `SessionContent.id`.
- GET `/api/v1/student/content/` (ViewSet root)
- GET `/api/v1/student/content/{content_id}/vocabulary/`
- GET `/api/v1/student/content/{content_id}/exercises/`
- GET `/api/v1/student/content/{content_id}/grammar/`
- GET `/api/v1/student/content/{content_id}/flashcards/`

### Per-session progress (gated flow)
- GET `/api/v1/student/sessions/{session_id}/progress/`
  - Response:
    ```json
    { "id": 10, "session_id": 123, "steps": [], "overall_status": "not_started", "total_score": 0, "total_duration_sec": 0, "updated_at": "..." }
    ```
- PUT `/api/v1/student/sessions/{session_id}/progress/`
  - Body:
    ```json
    {
      "steps": [
        { "step": "summary", "status": "completed", "score": 2, "attempts": 1, "duration_sec": 120 },
        { "step": "vocabulary", "status": "in_progress", "correct": 5, "target": 5 }
      ],
      "overall_status": "in_progress",
      "total_score": 2,
      "total_duration_sec": 120
    }
    ```

### Exercise submission (server-graded)
- POST `/api/v1/student/content/{content_id}/exercises/{index}/submit/`
  - Body:
    ```json
    { "answer": "..." }
    ```
  - Response:
    ```json
    { "correct": true, "explanation": "...", "submission_id": 45 }
    ```
  - Notes:
    - `index` is zero-based into `content.exercises`
    - Recognized answer keys in content: `correctAnswer`, `correct_answer`, `answer`, `correct`, `expected`

### Flashcard review logging (spaced repetition)
- POST `/api/v1/student/content/{content_id}/flashcards/{index}/review/`
  - Body:
    ```json
    { "quality": 0, "time_taken_ms": 3500 }
    ```
  - Response:
    ```json
    { "next_due": "2025-08-14T12:00:00Z", "review_id": 12 }
    ```
  - `index` is zero-based into `content.flashcards`. `next_due` is a simple heuristic (lower quality → sooner).

### Speaking practice attempts
- POST `/api/v1/student/sessions/{session_id}/speaking/attempt/`
  - Body:
    ```json
    { "line": "Ich bin krank.", "self_rating": 3, "duration_ms": 4200 }
    ```
  - Response:
    ```json
    { "attempt_id": 77, "count_for_session": 4, "last_attempt_at": "2025-08-14T12:00:00Z" }
    ```

### Session completion and rewards
- POST `/api/v1/student/sessions/{session_id}/complete/`
  - Body (optional):
    ```json
    { "final_score": 86 }
    ```
  - Response:
    ```json
    { "progress_id": 10, "overall_status": "completed", "total_score": 86, "xp_awarded": 10, "streak_after": 2 }
    ```
  - Notes: Rewards are basic (flat XP, naive streak increment). Can be evolved.

### Final quiz (derived from content)
- GET `/api/v1/student/content/{content_id}/quiz/`
  - Response:
    ```json
    { "quiz": [ /* items from exercises + practice_exercises (capped to 10) */ ], "count": 10 }
    ```
- POST `/api/v1/student/content/{content_id}/submit_quiz/`
  - Body:
    ```json
    { "answers": { "0": "...", "1": "..." } }
    ```
  - Response:
    ```json
    { "submission_id": 21, "score": 90, "correct": 9, "total": 10 }
    ```
  - Notes: Index keys map into `[exercises + practice_exercises]` in order.

### Gating configuration (server-driven)
- Returned in: `GET /api/v1/student/class/{class_id}/session/{session_id}/`
- Shape (defaults, merged with server `gating_config` if set on `SessionContent`):
  ```json
  {
    "summary": { "min_tasks": 1 },
    "vocabulary": { "target_correct": 5 },
    "grammar": { "min_tasks": 1 },
    "flashcards": { "min_reviews": 8 },
    "exercises": { "min_correct": 3 },
    "speaking": { "min_attempts": 3 }
  }
  ```

### Data models (high-level)
- `SessionContent`: `summary`, `vocabulary`, `examples`, `exercises`, `grammar_points`, `key_concepts`, `grammar_tables`, `vocabulary_with_definitions`, `speaking_practice`, `flashcards`, `practice_exercises`, `difficulty_level`, `duration_minutes`, `language`, `gating_config`
- `SessionProgress`: `student`, `session`, `steps` (array), `overall_status`, `total_score`, `total_duration_sec`
- Logs: `ExerciseSubmission`, `FlashcardReview`, `SpeakingAttempt`, `QuizSubmission`, `RewardEvent`

### Conventions and notes
- Indices are zero-based into arrays in `SessionContent`.
- Ownership enforced via enrollment checks; inaccessible resources return 404 or 403.
- For non-idempotent endpoints from mobile, use appropriate auth (session cookie via login or set up tokens) and include CSRF if using session auth in a browser context.
- Host routing uses `django-hosts`; use `Host: dashboard.localhost` (already covered if you hit `http://dashboard.localhost:8000`).

### Quick checklist (what’s implemented)
- Per-session progress (GET/PUT) ✅
- Exercise submission ✅
- Flashcard review logging ✅
- Speaking attempt logging ✅
- Session completion with rewards ✅
- Final quiz generation and submission ✅
- Server-driven gating config in session detail ✅
- Sessions, classes, content-by-type retrieval ✅ 