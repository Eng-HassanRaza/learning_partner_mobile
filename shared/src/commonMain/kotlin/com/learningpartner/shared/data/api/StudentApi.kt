package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.*
import io.ktor.client.request.*
import io.ktor.client.call.*

class StudentApi {
    private val client = ApiClient.client
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun getDashboard(): Student {
        return client.get("${baseUrl}student/dashboard/").body()
    }
    
    suspend fun getClasses(): List<Class> {
        return client.get("${baseUrl}student/classes/").body()
    }
    
    suspend fun getClassDetails(classId: Int): Class {
        return client.get("${baseUrl}student/class/$classId/").body()
    }
    
    suspend fun getSessions(): List<SessionTranscription> {
        return client.get("${baseUrl}student/sessions/").body()
    }
    
    suspend fun getSessionDetails(classId: Int, sessionId: Int): SessionTranscription {
        return client.get("${baseUrl}student/class/$classId/session/$sessionId/").body()
    }
    
    suspend fun getVocabulary(contentId: Int): List<Vocabulary> {
        return client.get("${baseUrl}student/content/$contentId/vocabulary/").body()
    }
    
    suspend fun getExercises(contentId: Int): List<Exercise> {
        return client.get("${baseUrl}student/content/$contentId/exercises/").body()
    }
    
    suspend fun getGrammar(contentId: Int): List<GrammarTable> {
        return client.get("${baseUrl}student/content/$contentId/grammar/").body()
    }
    
    suspend fun getFlashcards(contentId: Int): List<Flashcard> {
        return client.get("${baseUrl}student/content/$contentId/flashcards/").body()
    }
    
    suspend fun getMeetings(): List<Any> {
        return client.get("${baseUrl}student/meetings/").body()
    }
    
    suspend fun joinMeeting(meetingId: Int): String {
        return client.post("${baseUrl}student/meetings/$meetingId/join/").body()
    }
    
    suspend fun getInvitations(): List<Any> {
        return client.get("${baseUrl}student/invitations/").body()
    }
    
    suspend fun acceptInvitation(invitationId: Int): String {
        return client.post("${baseUrl}student/invitations/$invitationId/accept/").body()
    }
    
    suspend fun declineInvitation(invitationId: Int): String {
        return client.post("${baseUrl}student/invitations/$invitationId/decline/").body()
    }
    
    suspend fun getProgress(): List<Any> {
        return client.get("${baseUrl}student/progress/").body()
    }
} 