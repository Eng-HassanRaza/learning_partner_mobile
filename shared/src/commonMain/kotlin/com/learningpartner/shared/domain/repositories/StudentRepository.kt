package com.learningpartner.shared.domain.repositories

import com.learningpartner.shared.domain.models.*

interface StudentRepository {
    suspend fun getDashboard(): Result<Student>
    suspend fun getClasses(): Result<List<Class>>
    suspend fun getClassDetails(classId: Int): Result<Class>
    suspend fun getSessions(): Result<List<SessionTranscription>>
    suspend fun getSessionDetails(classId: Int, sessionId: Int): Result<SessionTranscription>
    suspend fun getVocabulary(contentId: Int): Result<List<Vocabulary>>
    suspend fun getExercises(contentId: Int): Result<List<Exercise>>
    suspend fun getGrammar(contentId: Int): Result<List<GrammarTable>>
    suspend fun getFlashcards(contentId: Int): Result<List<Flashcard>>
    suspend fun getMeetings(): Result<List<Any>>
    suspend fun joinMeeting(meetingId: Int): Result<String>
    suspend fun getInvitations(): Result<List<Any>>
    suspend fun acceptInvitation(invitationId: Int): Result<String>
    suspend fun declineInvitation(invitationId: Int): Result<String>
    suspend fun getProgress(): Result<List<Any>>

    // New for gated interactive session
    suspend fun getSessionProgress(sessionId: Int): Result<SessionProgress>
    suspend fun updateSessionProgress(sessionId: Int, update: SessionProgressUpdate): Result<SessionProgress>
    suspend fun submitExercise(contentId: Int, index: Int, request: ExerciseSubmissionRequest): Result<ExerciseSubmissionResponse>
    suspend fun reviewFlashcard(contentId: Int, index: Int, request: FlashcardReviewRequest): Result<FlashcardReviewResponse>
    suspend fun logSpeakingAttempt(sessionId: Int, request: SpeakingAttemptRequest): Result<SpeakingAttemptResponse>
    suspend fun getQuiz(contentId: Int): Result<QuizResponse>
    suspend fun submitQuiz(contentId: Int, request: QuizSubmitRequest): Result<QuizSubmitResponse>
    suspend fun completeSession(sessionId: Int, request: CompleteSessionRequest): Result<CompleteSessionResponse>
} 