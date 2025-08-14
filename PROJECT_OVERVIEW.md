# Learning Partner Platform - Project Overview

## 🎯 **Project Vision**
A comprehensive language learning platform where teachers conduct online classes via Zoom or upload recorded sessions, and students practice using AI-generated content and chatbots.

## 🏗️ **Architecture Overview**

### **Backend (Django)**
- **Location**: `learning_partner/` (current project)
- **Purpose**: Teacher interface, session management, AI processing
- **Users**: Teachers (primary), Students (web interface)

### **Mobile Apps (Kotlin Multiplatform)**
- **Location**: `learning_partner_mobile/` (new project)
- **Purpose**: Student interface for learning and practice
- **Users**: Students (primary)

## 📊 **Data Flow**

```
Teacher Uploads Video → Django Backend → AI Processing → API → Mobile Apps
                                                      ↓
Student Practices ← Mobile Apps ← API ← Session Content ← AI Generated Content
```

## 🔗 **Key Integration Points**

### **1. API Endpoints (Already Built)**
```
Base URL: http://dashboard.localhost:8000/api/v1/

Authentication:
- POST /auth/ - Login
- DELETE /auth/ - Logout

Student Data:
- GET /student/dashboard/ - Student overview
- GET /student/classes/ - Enrolled classes
- GET /student/class/{id}/ - Class details
- GET /student/class/{id}/session/{id}/ - Session with AI content
- GET /student/content/{id}/vocabulary/ - Vocabulary practice
- GET /student/content/{id}/exercises/ - Practice exercises
- GET /student/content/{id}/grammar/ - Grammar content
- GET /student/content/{id}/flashcards/ - Flashcards
```

### **2. Data Models (Shared Between Backend & Mobile)**
```kotlin
// These models exist in Django and will be replicated in KMP
data class Session(
    val id: String,
    val title: String,
    val content: SessionContent,
    val vocabulary: List<Vocabulary>,
    val exercises: List<Exercise>,
    val flashcards: List<Flashcard>
)

data class SessionContent(
    val summary: String,
    val vocabulary: List<Vocabulary>,
    val grammarTables: List<GrammarTable>,
    val practiceExercises: List<Exercise>,
    val flashcards: List<Flashcard>,
    val speakingPractice: List<String>
)
```

## 🎯 **Mobile App Features (Student Interface)**

### **Core Features**
1. **Authentication** - Login with existing student credentials
2. **Dashboard** - Overview of classes, progress, upcoming sessions
3. **Class Browser** - View enrolled classes and sessions
4. **Session Practice** - Access AI-generated learning content
5. **Vocabulary Practice** - Flashcards and definitions
6. **Grammar Practice** - Interactive grammar tables
7. **Exercise Practice** - Fill-in-blank, translation, writing
8. **Progress Tracking** - Monitor learning progress
9. **Offline Support** - Practice without internet

### **Learning Flow**
```
Login → Dashboard → Select Class → Select Session → Practice Content
  ↓
Vocabulary → Grammar → Exercises → Flashcards → Progress Update
```

## 📱 **Mobile App Architecture**

### **Shared Module (Kotlin Multiplatform)**
```kotlin
shared/
├── domain/           # Business logic (same as Django models)
│   ├── models/       # Data models (Session, User, Class, etc.)
│   ├── repositories/ # Data access interfaces
│   └── usecases/     # Business rules
├── data/             # Data layer
│   ├── api/          # API client (connects to Django)
│   ├── cache/        # Local storage
│   └── repositories/ # Repository implementations
└── presentation/     # Shared UI logic
    ├── viewmodels/   # State management
    └── components/   # UI components
```

### **Platform-Specific UI**
```kotlin
android/
└── ui/
    ├── components/    # Android-specific component implementations
    └── screens/       # Android screens using shared components

ios/
└── ui/
    ├── components/    # iOS-specific component implementations
    └── screens/       # iOS screens using shared components
```

## 🔄 **Data Synchronization**

### **Real-time Updates**
- **New Sessions** - Pull from API when available
- **Progress Updates** - Push to API when completed
- **Content Updates** - Cache locally, sync when online

### **Offline Capabilities**
- **Session Content** - Download and cache for offline practice
- **Vocabulary** - Store locally for offline review
- **Progress** - Track locally, sync when online

## 🧪 **Testing Strategy**

### **Backend Testing (Already Complete)**
- ✅ API endpoints tested
- ✅ Authentication working
- ✅ Data models validated
- ✅ AI content generation working

### **Mobile Testing**
- **Unit Tests** - Shared business logic
- **Integration Tests** - API integration
- **UI Tests** - Platform-specific UI
- **End-to-End Tests** - Complete user flows

## 🚀 **Development Phases**

### **Phase 1: Foundation (Week 1-2)**
- [ ] Set up KMP project structure
- [ ] Create shared data models (matching Django)
- [ ] Build API client (connecting to Django)
- [ ] Implement authentication

### **Phase 2: Core Features (Week 3-4)**
- [ ] Build dashboard screen
- [ ] Create class browser
- [ ] Implement session viewer
- [ ] Add basic practice features

### **Phase 3: Learning Features (Week 5-6)**
- [ ] Vocabulary practice components
- [ ] Grammar practice components
- [ ] Exercise practice components
- [ ] Progress tracking

### **Phase 4: Polish (Week 7+)**
- [ ] Offline support
- [ ] Performance optimization
- [ ] UI/UX improvements
- [ ] Testing and bug fixes

## 📁 **File References**

### **Backend Files (Current Project)**
- `home/models.py` - Django models (reference for KMP models)
- `home/api_serializers.py` - API serializers (reference for KMP models)
- `home/api_views.py` - API endpoints (reference for API client)
- `API_DOCUMENTATION.md` - Complete API documentation
- `API_TEST_REPORT.md` - API testing results

### **Mobile Files (New Project)**
- `shared/domain/models/` - KMP data models (matching Django)
- `shared/data/api/` - API client (connecting to Django)
- `shared/presentation/components/` - UI components
- `android/ui/` - Android-specific UI
- `ios/ui/` - iOS-specific UI

## 🔗 **Key Integration Points**

### **1. Authentication**
- Use existing Django user accounts
- JWT tokens for mobile authentication
- Session management

### **2. Data Models**
- KMP models must match Django models exactly
- API responses must be compatible
- Local caching must preserve data structure

### **3. API Integration**
- All mobile features use Django API
- Real-time updates via API polling
- Offline sync when online

### **4. Content Delivery**
- AI-generated content from Django
- Rich learning materials (vocabulary, grammar, exercises)
- Multimedia support (audio, video)

## 🎯 **Success Metrics**

### **Technical Metrics**
- [ ] 100% API integration working
- [ ] Offline functionality working
- [ ] Cross-platform code sharing >80%
- [ ] Performance <2s load times

### **User Experience Metrics**
- [ ] Students can access all learning content
- [ ] Practice features work smoothly
- [ ] Progress tracking accurate
- [ ] Offline practice seamless

---

**This document serves as our reference guide for mobile development.**
**All mobile development will be based on this Django backend and API.** 