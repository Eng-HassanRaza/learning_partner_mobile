package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.*
import com.learningpartner.shared.domain.models.PaginatedResponse

class StudentApi(
    private val httpClient: HttpClient = ApiClient.client
) {
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun getDashboard(): Student {
        return httpClient.get("${baseUrl}student/dashboard/").body()
    }
    
    suspend fun getClasses(): List<Class> {
        val response: PaginatedResponse<Class> = httpClient.get("${baseUrl}student/classes/").body()
        return response.results
    }
    
    suspend fun getClassDetails(classId: Int): Class {
        return httpClient.get("${baseUrl}student/class/$classId/").body()
    }
    
    suspend fun getSessions(): List<SessionTranscription> {
        val response: PaginatedResponse<SessionTranscription> = httpClient.get("${baseUrl}student/sessions/").body()
        return response.results
    }
    
    suspend fun getSessionDetails(classId: Int, sessionId: Int): SessionTranscription {
        return httpClient.get("${baseUrl}student/class/$classId/session/$sessionId/").body()
    }
    
    suspend fun getVocabulary(contentId: Int): List<Vocabulary> {
        return httpClient.get("${baseUrl}student/content/$contentId/vocabulary/").body()
    }
    
    suspend fun getExercises(contentId: Int): List<Exercise> {
        return httpClient.get("${baseUrl}student/content/$contentId/exercises/").body()
    }
    
    suspend fun getGrammar(contentId: Int): List<GrammarTable> {
        return httpClient.get("${baseUrl}student/content/$contentId/grammar/").body()
    }
    
    suspend fun getFlashcards(contentId: Int): List<Flashcard> {
        return httpClient.get("${baseUrl}student/content/$contentId/flashcards/").body()
    }
    
    suspend fun getMeetings(): List<Any> {
        return httpClient.get("${baseUrl}student/meetings/").body()
    }
    
    suspend fun joinMeeting(meetingId: Int): String {
        return httpClient.post("${baseUrl}student/meetings/$meetingId/join/").body()
    }
    
    suspend fun getInvitations(): List<Any> {
        return httpClient.get("${baseUrl}student/invitations/").body()
    }
    
    suspend fun acceptInvitation(invitationId: Int): String {
        return httpClient.post("${baseUrl}student/invitations/$invitationId/accept/").body()
    }
    
    suspend fun declineInvitation(invitationId: Int): String {
        return httpClient.post("${baseUrl}student/invitations/$invitationId/decline/").body()
    }
    
    suspend fun getProgress(): List<Any> {
        return httpClient.get("${baseUrl}student/progress/").body()
    }

    // New endpoints for gated interactive session
    suspend fun getSessionProgress(sessionId: Int): SessionProgress {
        return httpClient.get("${baseUrl}student/sessions/$sessionId/progress/").body()
    }

    suspend fun updateSessionProgress(sessionId: Int, update: SessionProgressUpdate): SessionProgress {
        return httpClient.put("${baseUrl}student/sessions/$sessionId/progress/") {
            setBody(update)
        }.body()
    }

    suspend fun submitExercise(contentId: Int, index: Int, request: ExerciseSubmissionRequest): ExerciseSubmissionResponse {
        return httpClient.post("${baseUrl}student/content/$contentId/exercises/$index/submit/") {
            setBody(request)
        }.body()
    }

    suspend fun reviewFlashcard(contentId: Int, index: Int, request: FlashcardReviewRequest): FlashcardReviewResponse {
        return httpClient.post("${baseUrl}student/content/$contentId/flashcards/$index/review/") {
            setBody(request)
        }.body()
    }

    suspend fun logSpeakingAttempt(sessionId: Int, request: SpeakingAttemptRequest): SpeakingAttemptResponse {
        return httpClient.post("${baseUrl}student/sessions/$sessionId/speaking/attempt/") {
            setBody(request)
        }.body()
    }

    suspend fun getQuiz(contentId: Int): QuizResponse {
        return httpClient.get("${baseUrl}student/content/$contentId/quiz/").body()
    }

    suspend fun submitQuiz(contentId: Int, request: QuizSubmitRequest): QuizSubmitResponse {
        return httpClient.post("${baseUrl}student/content/$contentId/submit_quiz/") {
            setBody(request)
        }.body()
    }

    suspend fun completeSession(sessionId: Int, request: CompleteSessionRequest): CompleteSessionResponse {
        return httpClient.post("${baseUrl}student/sessions/$sessionId/complete/") {
            setBody(request)
        }.body()
    }
} 