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
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load session details"
                }
            
            _isLoading.value = false
        }
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
    
    fun clearError() {
        _error.value = null
    }
} 