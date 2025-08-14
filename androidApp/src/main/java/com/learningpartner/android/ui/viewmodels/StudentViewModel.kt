package com.learningpartner.android.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningpartner.shared.domain.models.*
import com.learningpartner.shared.domain.repositories.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel(
    private val studentRepository: StudentRepository
) : ViewModel() {
    
    private val _dashboard = MutableStateFlow<Student?>(null)
    val dashboard: StateFlow<Student?> = _dashboard.asStateFlow()
    
    private val _classes = MutableStateFlow<List<Class>>(emptyList())
    val classes: StateFlow<List<Class>> = _classes.asStateFlow()
    
    private val _sessions = MutableStateFlow<List<SessionTranscription>>(emptyList())
    val sessions: StateFlow<List<SessionTranscription>> = _sessions.asStateFlow()
    
    private val _selectedClass = MutableStateFlow<Class?>(null)
    val selectedClass: StateFlow<Class?> = _selectedClass.asStateFlow()
    
    private val _selectedSession = MutableStateFlow<SessionTranscription?>(null)
    val selectedSession: StateFlow<SessionTranscription?> = _selectedSession.asStateFlow()
    
    private val _vocabulary = MutableStateFlow<List<Vocabulary>>(emptyList())
    val vocabulary: StateFlow<List<Vocabulary>> = _vocabulary.asStateFlow()
    
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()
    
    private val _grammar = MutableStateFlow<List<GrammarTable>>(emptyList())
    val grammar: StateFlow<List<GrammarTable>> = _grammar.asStateFlow()
    
    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>> = _flashcards.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Session progress state
    private val _sessionProgress = MutableStateFlow<SessionProgress?>(null)
    val sessionProgress: StateFlow<SessionProgress?> = _sessionProgress.asStateFlow()
    
    fun loadDashboard() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getDashboard()
                .onSuccess { student ->
                    _dashboard.value = student
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load dashboard"
                }
            
            _isLoading.value = false
        }
    }
    
    fun loadClasses() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getClasses()
                .onSuccess { classes ->
                    _classes.value = classes
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load classes"
                }
            
            _isLoading.value = false
        }
    }
    
    fun loadSessions() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getSessions()
                .onSuccess { sessions ->
                    _sessions.value = sessions
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load sessions"
                }
            
            _isLoading.value = false
        }
    }
    
    fun selectClass(classId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getClassDetails(classId)
                .onSuccess { classDetails ->
                    _selectedClass.value = classDetails
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load class details"
                }
            
            _isLoading.value = false
        }
    }
    
    fun selectSession(classId: Int, sessionId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getSessionDetails(classId, sessionId)
                .onSuccess { session ->
                    _selectedSession.value = session
                    // load server progress if available
                    loadSessionProgress(session.id)
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load session details"
                }
            
            _isLoading.value = false
        }
    }
    
    fun clearSelectedSession() {
        _selectedSession.value = null
        _sessionProgress.value = null
    }
    
    fun loadVocabulary(contentId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getVocabulary(contentId)
                .onSuccess { vocabulary ->
                    _vocabulary.value = vocabulary
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load vocabulary"
                }
            
            _isLoading.value = false
        }
    }
    
    fun loadExercises(contentId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getExercises(contentId)
                .onSuccess { exercises ->
                    _exercises.value = exercises
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load exercises"
                }
            
            _isLoading.value = false
        }
    }
    
    fun loadGrammar(contentId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getGrammar(contentId)
                .onSuccess { grammar ->
                    _grammar.value = grammar
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load grammar"
                }
            
            _isLoading.value = false
        }
    }
    
    fun loadFlashcards(contentId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            studentRepository.getFlashcards(contentId)
                .onSuccess { flashcards ->
                    _flashcards.value = flashcards
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load flashcards"
                }
            
            _isLoading.value = false
        }
    }

    // Progress APIs
    fun loadSessionProgress(sessionId: Int) {
        viewModelScope.launch {
            studentRepository.getSessionProgress(sessionId)
                .onSuccess { progress -> _sessionProgress.value = progress }
                .onFailure { /* keep null; UI can start at step 0 */ }
        }
    }

    fun updateSessionProgress(sessionId: Int, update: SessionProgressUpdate, onDone: ((SessionProgress) -> Unit)? = null) {
        viewModelScope.launch {
            studentRepository.updateSessionProgress(sessionId, update)
                .onSuccess { progress ->
                    _sessionProgress.value = progress
                    onDone?.invoke(progress)
                }
                .onFailure { e -> _error.value = e.message ?: "Failed to update progress" }
        }
    }

    fun submitExercise(contentId: Int, index: Int, answer: String, onResult: (ExerciseSubmissionResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.submitExercise(contentId, index, ExerciseSubmissionRequest(answer))
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to submit exercise" }
        }
    }

    fun reviewFlashcard(contentId: Int, index: Int, quality: Int, timeTakenMs: Long, onResult: (FlashcardReviewResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.reviewFlashcard(contentId, index, FlashcardReviewRequest(quality, timeTakenMs))
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to review flashcard" }
        }
    }

    fun logSpeakingAttempt(sessionId: Int, line: String, selfRating: Int, durationMs: Long?, onResult: (SpeakingAttemptResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.logSpeakingAttempt(sessionId, SpeakingAttemptRequest(line, selfRating, durationMs))
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to log speaking attempt" }
        }
    }

    fun getQuiz(contentId: Int, onResult: (QuizResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.getQuiz(contentId)
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to load quiz" }
        }
    }

    fun submitQuiz(contentId: Int, answers: Map<String, String>, onResult: (QuizSubmitResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.submitQuiz(contentId, QuizSubmitRequest(answers))
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to submit quiz" }
        }
    }

    fun completeSession(sessionId: Int, finalScore: Int?, onResult: (CompleteSessionResponse) -> Unit) {
        viewModelScope.launch {
            studentRepository.completeSession(sessionId, CompleteSessionRequest(finalScore))
                .onSuccess(onResult)
                .onFailure { e -> _error.value = e.message ?: "Failed to complete session" }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
} 