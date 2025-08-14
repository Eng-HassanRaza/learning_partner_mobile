# Learning Partner Mobile App

A Kotlin Multiplatform (KMP) mobile application for language learning, built with Compose Multiplatform and connected to a Django backend.

## 🚀 Project Overview

This mobile app provides students with access to AI-generated language learning content, including:
- **Vocabulary Practice** - Interactive flashcards and word definitions
- **Grammar Exercises** - Structured grammar tables and rules
- **Practice Exercises** - Fill-in-blank, translation, and writing exercises
- **Progress Tracking** - Monitor learning progress and achievements
- **Session Management** - Access to recorded class sessions with AI-generated content

## 🏗️ Architecture

### Tech Stack
- **Kotlin Multiplatform** - Cross-platform development
- **Compose Multiplatform** - Modern UI framework
- **Ktor** - HTTP client for API communication
- **Koin** - Dependency injection
- **Realm** - Local database for offline support
- **Material 3** - Modern design system

### Project Structure
```
learning_partner_mobile/
├── shared/                          # Shared Kotlin code
│   ├── domain/
│   │   ├── models/                  # Data models (matching Django)
│   │   └── repositories/            # Repository interfaces
│   ├── data/
│   │   ├── api/                     # API client (Django connection)
│   │   └── repositories/            # Repository implementations
│   └── di/                          # Dependency injection
├── androidApp/                      # Android-specific code
│   ├── ui/
│   │   ├── screens/                 # Android screens
│   │   └── viewmodels/              # Android ViewModels
│   └── src/main/
└── gradle/                          # Gradle configuration
```

## 🔗 Backend Integration

The app connects to a Django backend with the following features:
- **Base URL**: `http://dashboard.localhost:8000/api/v1/`
- **Authentication**: Session-based (existing Django users)
- **Test Credentials**: 
  - Email: `hassanrazade@gmail.com`
  - Password: `Hassan0331`

### API Endpoints
- `POST /auth/` - Login
- `GET /student/dashboard/` - Student overview
- `GET /student/classes/` - Enrolled classes
- `GET /student/class/{id}/` - Class details
- `GET /student/class/{id}/session/{id}/` - Session content
- `GET /student/content/{id}/vocabulary/` - Vocabulary
- `GET /student/content/{id}/exercises/` - Exercises
- `GET /student/content/{id}/grammar/` - Grammar
- `GET /student/content/{id}/flashcards/` - Flashcards

## 📱 Features

### Authentication
- Login with existing Django credentials
- Session management
- Automatic logout

### Dashboard
- Student overview with progress
- Current level display
- Recent activity
- Quick access to classes and sessions

### Classes
- View enrolled classes
- Class details and teacher information
- Student capacity and level information

### Sessions
- Access to recorded class sessions
- AI-generated learning content
- Processing status indicators
- Content type previews (vocabulary, exercises, grammar)

### Progress Tracking
- Overall progress percentage
- Learning statistics
- Recent activity timeline
- Level progression

## 🛠️ Development Setup

### Prerequisites
- **JDK 17** or higher
- **Android Studio** (for Android development)
- **Xcode** (for iOS development, macOS only)
- **Django Backend** running on `http://dashboard.localhost:8000/`

### Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd learning_partner_mobile
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run on Android**
   ```bash
   ./gradlew androidApp:installDebug
   ```

4. **Run on iOS** (macOS only)
   ```bash
   ./gradlew iosSimulatorArm64Test
   ```

### Development Workflow

1. **Start Django Backend**
   - Ensure the Django backend is running on `http://dashboard.localhost:8000/`
   - Verify API endpoints are accessible

2. **Test Authentication**
   - Use test credentials: `hassanrazade@gmail.com` / `Hassan0331`
   - Verify login flow works correctly

3. **Development Features**
   - Hot reload with Compose Multiplatform
   - Shared code between Android and iOS
   - Real-time API integration

## 📊 Data Models

The app uses data models that exactly match the Django backend:

```kotlin
// User authentication
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)

// Student information
data class Student(
    val id: Int,
    val user: User,
    val currentLevel: LanguageLevel,
    val progressPercentage: Int
)

// Class information
data class Class(
    val id: Int,
    val name: String,
    val teacher: LanguageTeacher,
    val level: LanguageLevel,
    val classType: String,
    val maxStudents: Int,
    val currentStudents: Int
)

// Session content
data class SessionTranscription(
    val id: Int,
    val classSession: Class,
    val processingStatus: String,
    val videoDuration: Int,
    val languageDetected: String,
    val content: SessionContent?
)
```

## 🎨 UI Components

The app uses Material 3 design with:
- **Modern Cards** - Information display
- **Bottom Navigation** - Easy navigation between sections
- **Progress Indicators** - Visual progress tracking
- **Status Chips** - Processing status indicators
- **Responsive Layout** - Adapts to different screen sizes

## 🔧 Configuration

### API Configuration
- Base URL: `http://dashboard.localhost:8000/api/v1/`
- Network timeout: 10 seconds
- JSON serialization with lenient parsing

### Build Configuration
- **Android**: minSdk 24, targetSdk 34
- **iOS**: iOS 13.0+
- **Kotlin**: 1.9.20
- **Compose**: 1.5.10

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Android Tests
```bash
./gradlew androidApp:testDebugUnitTest
```

### Integration Tests
- API integration tests
- Repository tests
- ViewModel tests

## 📦 Building for Production

### Android APK
```bash
./gradlew androidApp:assembleRelease
```

### Android Bundle
```bash
./gradlew androidApp:bundleRelease
```

### iOS Framework
```bash
./gradlew linkReleaseFrameworkIosArm64
```

## 🚀 Deployment

### Android
1. Generate signed APK/Bundle
2. Upload to Google Play Console
3. Configure release notes and screenshots

### iOS
1. Build iOS framework
2. Integrate with Xcode project
3. Submit to App Store Connect

## 🔄 Future Enhancements

### Planned Features
- **Offline Support** - Practice without internet
- **Audio Playback** - Pronunciation practice
- **Push Notifications** - Class reminders
- **Live Chat** - Real-time communication
- **Video Streaming** - Session playback
- **Gamification** - Points and achievements

### Technical Improvements
- **Caching Strategy** - Improved offline support
- **Performance Optimization** - Faster loading times
- **Accessibility** - Screen reader support
- **Internationalization** - Multi-language support

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Check the API documentation
- Review the Django backend logs
- Contact the development team

---

**Note**: This mobile app is designed to work with the Learning Partner Django backend. Ensure the backend is running and accessible before testing the mobile app.
