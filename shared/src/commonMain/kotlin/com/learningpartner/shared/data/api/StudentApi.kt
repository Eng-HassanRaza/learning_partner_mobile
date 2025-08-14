package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.*
import io.ktor.client.request.*
import io.ktor.http.*

class StudentApi {
    private val client = ApiClient.client
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun getDashboard(): Student {
        return client.get("${baseUrl}student/dashboard/")
    }
    
    suspend fun getClasses(): List<Class> {
        return client.get("${baseUrl}student/classes/")
    }
    
    suspend fun getClassDetails(classId: Int): Class {
        return client.get("${baseUrl}student/class/$classId/")
    }
    
    suspend fun getSessions(): List<SessionTranscription> {
        return client.get("${baseUrl}student/sessions/")
    }
    
    suspend fun getSessionDetails(classId: Int, sessionId: Int): SessionTranscription {
        return client.get("${baseUrl}student/class/$classId/session/$sessionId/")
    }
    
    suspend fun getVocabulary(contentId: Int): List<Vocabulary> {
        return client.get("${baseUrl}student/content/$contentId/vocabulary/")
    }
    
    suspend fun getExercises(contentId: Int): List<Exercise> {
        return client.get("${baseUrl}student/content/$contentId/exercises/")
    }
    
    suspend fun getGrammar(contentId: Int): List<GrammarTable> {
        return client.get("${baseUrl}student/content/$contentId/grammar/")
    }
    
    suspend fun getFlashcards(contentId: Int): List<Flashcard> {
        return client.get("${baseUrl}student/content/$contentId/flashcards/")
    }
    
    suspend fun getMeetings(): List<Any> {
        return client.get("${baseUrl}student/meetings/")
    }
    
    suspend fun joinMeeting(meetingId: Int): String {
        val response = client.post("${baseUrl}student/meetings/$meetingId/join/")
        return response.bodyAsText()
    }
    
    suspend fun getInvitations(): List<Any> {
        return client.get("${baseUrl}student/invitations/")
    }
    
    suspend fun acceptInvitation(invitationId: Int): String {
        val response = client.post("${baseUrl}student/invitations/$invitationId/accept/")
        return response.bodyAsText()
    }
    
    suspend fun declineInvitation(invitationId: Int): String {
        val response = client.post("${baseUrl}student/invitations/$invitationId/decline/")
        return response.bodyAsText()
    }
    
    suspend fun getProgress(): List<Any> {
        return client.get("${baseUrl}student/progress/")
    }
} 