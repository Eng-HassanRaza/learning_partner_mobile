# Mobile Development Quick Reference

## 🔗 **API Connection Details**

### **Base URL**
```
Development: http://dashboard.localhost:8000/api/v1/
Production: https://dashboard.your-domain.com/api/v1/
```

### **Test Credentials**
```json
{
  "username": "hassanrazade@gmail.com",
  "password": "Hassan0331"
}
```

### **Test Data**
- **Class ID**: 66
- **Session ID**: 84 (has rich AI content)
- **User Type**: student

## 📊 **Key API Endpoints**

### **Authentication**
```http
POST /auth/
DELETE /auth/
```

### **Student Data**
```http
GET /student/dashboard/
GET /student/classes/
GET /student/class/{id}/
GET /student/class/{id}/session/{id}/
GET /student/content/{id}/vocabulary/
GET /student/content/{id}/exercises/
GET /student/content/{id}/grammar/
GET /student/content/{id}/flashcards/
```

## 🏗️ **Data Models (Django → KMP)**

### **Core Models to Replicate**
```kotlin
// From Django models.py - replicate in KMP
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)

data class Student(
    val id: Int,
    val user: User,
    val currentLevel: LanguageLevel,
    val progressPercentage: Int,
    val enrollmentDate: String
)

data class Class(
    val id: Int,
    val name: String,
    val teacher: LanguageTeacher,
    val level: LanguageLevel,
    val classType: String,
    val maxStudents: Int,
    val currentStudents: Int
)

data class SessionTranscription(
    val id: Int,
    val classSession: Class,
    val processingStatus: String,
    val videoDuration: Int,
    val languageDetected: String,
    val content: SessionContent?
)

data class SessionContent(
    val id: Int,
    val summary: String,
    val vocabulary: List<Vocabulary>,
    val grammarTables: List<GrammarTable>,
    val practiceExercises: List<Exercise>,
    val flashcards: List<Flashcard>,
    val speakingPractice: List<String>
)
```

## 🎯 **Mobile App Features**

### **Screens to Build**
1. **Login Screen** - Authentication
2. **Dashboard Screen** - Student overview
3. **Classes Screen** - List enrolled classes
4. **Class Detail Screen** - Class information and sessions
5. **Session Detail Screen** - Session content and practice
6. **Vocabulary Practice Screen** - Flashcards and definitions
7. **Grammar Practice Screen** - Interactive grammar tables
8. **Exercise Practice Screen** - Fill-in-blank, translation, writing
9. **Profile Screen** - Student profile and settings

### **Components to Build**
1. **Button Component** - Primary, secondary, disabled states
2. **Card Component** - Session cards, class cards
3. **Input Component** - Text input, search input
4. **Loading Component** - Loading spinners, skeletons
5. **Error Component** - Error messages, retry buttons
6. **Vocabulary Card** - Word, definition, example
7. **Flashcard Component** - Flip animation, progress
8. **Grammar Table** - Interactive grammar explanations
9. **Exercise Card** - Question, answer, hints
10. **Progress Bar** - Learning progress visualization

## 🔄 **Development Workflow**

### **1. Set Up Project**
```bash
# Create new directory
mkdir learning_partner_mobile
cd learning_partner_mobile

# Set up KMP project structure
# (I'll guide you through this)
```

### **2. Create Shared Models**
```kotlin
// shared/domain/models/
// Replicate Django models exactly
```

### **3. Build API Client**
```kotlin
// shared/data/api/
// Connect to Django API endpoints
```

### **4. Create UI Components**
```kotlin
// shared/presentation/components/
// Platform-agnostic component interfaces
```

### **5. Implement Platform UI**
```kotlin
// android/ui/ and ios/ui/
// Platform-specific component implementations
```

## 🧪 **Testing Strategy**

### **Unit Tests (Shared)**
```kotlin
// Test all business logic
// Test API client
// Test data models
```

### **Integration Tests**
```kotlin
// Test API integration
// Test data flow
// Test authentication
```

### **UI Tests (Platform-Specific)**
```kotlin
// Test Android UI
// Test iOS UI (when available)
```

## 📱 **Platform Considerations**

### **Android (Linux Development)**
- ✅ Full development capability
- ✅ Testing on emulator and devices
- ✅ Complete feature implementation

### **iOS (Linux Development)**
- ✅ Code writing capability
- ✅ Compilation and syntax checking
- ❌ No testing (need Mac or cloud services)
- ✅ Can prepare for testing later

## 🚀 **Next Steps**

1. **Environment Setup** - Install Android Studio, KMP tools
2. **Project Creation** - Set up KMP project structure
3. **Shared Models** - Replicate Django models in KMP
4. **API Client** - Build connection to Django API
5. **Basic UI** - Create core UI components
6. **Authentication** - Implement login flow
7. **Dashboard** - Build student dashboard
8. **Content Display** - Show session content
9. **Practice Features** - Implement learning features
10. **Testing** - Test everything thoroughly

---

**This reference will guide us through the mobile development process.**
**All development will be based on the existing Django backend and API.** 