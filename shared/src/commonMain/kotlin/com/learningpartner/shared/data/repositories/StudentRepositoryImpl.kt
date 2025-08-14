package com.learningpartner.shared.data.repositories

import com.learningpartner.shared.data.api.StudentApi
import com.learningpartner.shared.domain.models.*
import com.learningpartner.shared.domain.repositories.StudentRepository

class StudentRepositoryImpl(
    private val studentApi: StudentApi
) : StudentRepository {
    
    override suspend fun getDashboard(): Result<Student> {
        return try {
            val dashboard = studentApi.getDashboard()
            Result.success(dashboard)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getClasses(): Result<List<Class>> {
        return try {
            val classes = studentApi.getClasses()
            Result.success(classes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getClassDetails(classId: Int): Result<Class> {
        return try {
            val classDetails = studentApi.getClassDetails(classId)
            Result.success(classDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getSessions(): Result<List<SessionTranscription>> {
        return try {
            val sessions = studentApi.getSessions()
            Result.success(sessions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getSessionDetails(classId: Int, sessionId: Int): Result<SessionTranscription> {
        return try {
            val sessionDetails = studentApi.getSessionDetails(classId, sessionId)
            Result.success(sessionDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getVocabulary(contentId: Int): Result<List<Vocabulary>> {
        return try {
            val vocabulary = studentApi.getVocabulary(contentId)
            Result.success(vocabulary)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getExercises(contentId: Int): Result<List<Exercise>> {
        return try {
            val exercises = studentApi.getExercises(contentId)
            Result.success(exercises)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getGrammar(contentId: Int): Result<List<GrammarTable>> {
        return try {
            val grammar = studentApi.getGrammar(contentId)
            Result.success(grammar)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getFlashcards(contentId: Int): Result<List<Flashcard>> {
        return try {
            val flashcards = studentApi.getFlashcards(contentId)
            Result.success(flashcards)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getMeetings(): Result<List<Any>> {
        return try {
            val meetings = studentApi.getMeetings()
            Result.success(meetings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun joinMeeting(meetingId: Int): Result<String> {
        return try {
            val response = studentApi.joinMeeting(meetingId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getInvitations(): Result<List<Any>> {
        return try {
            val invitations = studentApi.getInvitations()
            Result.success(invitations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun acceptInvitation(invitationId: Int): Result<String> {
        return try {
            val response = studentApi.acceptInvitation(invitationId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun declineInvitation(invitationId: Int): Result<String> {
        return try {
            val response = studentApi.declineInvitation(invitationId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getProgress(): Result<List<Any>> {
        return try {
            val progress = studentApi.getProgress()
            Result.success(progress)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 