# Learning Partner Mobile App

A Kotlin Multiplatform (KMP) mobile application for language learning, built with Compose Multiplatform.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Android Studio (for Android development)
- Xcode (for iOS development, macOS only)

### Local Development

#### Android Development (Linux/Windows/macOS)
```bash
# Run lightweight development build (Android only)
./dev-build.sh

# Or manually
./gradlew androidApp:assembleDebug -Penable.ios.targets=false
```

#### iOS Development (macOS only)
```bash
# Run full build including iOS targets
./ios-build.sh

# Or manually
./gradlew linkReleaseFrameworkIosArm64 -Penable.ios.targets=true
```

### Build Configuration

The project uses conditional iOS target configuration to avoid Kotlin Native compiler issues on non-macOS platforms:

- **Android builds**: Use `-Penable.ios.targets=false` (default)
- **iOS builds**: Use `-Penable.ios.targets=true` (macOS only)

## 🏗️ Project Structure

```
learning_partner_mobile/
├── shared/                 # Shared Kotlin code
│   ├── domain/            # Domain models and business logic
│   ├── data/              # Data layer (API, repositories)
│   └── di/                # Dependency injection
├── androidApp/            # Android-specific code
│   ├── src/main/          # Android app source
│   └── build.gradle.kts   # Android build configuration
├── .github/workflows/     # CI/CD workflows
│   ├── android-build.yml  # Android builds (Linux)
│   └── ios-build.yml      # iOS builds (macOS)
└── gradle.properties      # Build configuration
```

## 🔧 Build Scripts

### `dev-build.sh`
Lightweight development build for Android development:
- Cleans the project
- Compiles shared module (Android target only)
- Compiles Android app
- Runs tests

### `ios-build.sh`
Full build including iOS targets (macOS only):
- Cleans the project
- Compiles shared module (all targets)
- Compiles Android app
- Builds iOS frameworks
- Runs tests

## 🚀 CI/CD

### GitHub Actions

The project uses GitHub Actions for automated builds:

1. **Android Build** (`android-build.yml`)
   - Runs on `ubuntu-latest`
   - Builds Android APK
   - Disables iOS targets to avoid Kotlin Native issues

2. **iOS Build** (`ios-build.yml`)
   - Runs on `macos-latest`
   - Builds iOS frameworks
   - Enables iOS targets

### Build Artifacts

- **Android**: APK file uploaded as artifact
- **iOS**: Framework files uploaded as artifact

## 🛠️ Development Workflow

1. **Local Development**:
   - Use `./dev-build.sh` for Android development
   - Use `./ios-build.sh` for iOS development (macOS only)

2. **Testing**:
   - Push to GitHub to trigger automated builds
   - Check Actions tab for build results
   - Download artifacts for testing

3. **Deployment**:
   - Android: Install APK from GitHub Actions artifacts
   - iOS: Use framework files in Xcode project

## 📱 Features

- **Authentication**: Login/logout functionality
- **Dashboard**: Student progress overview
- **Classes**: View enrolled classes
- **Sessions**: Learning session management
- **Progress**: Track learning progress

## 🏗️ Architecture

- **Kotlin Multiplatform**: Cross-platform shared code
- **Compose Multiplatform**: Modern UI framework
- **Ktor**: HTTP client for API communication
- **Koin**: Dependency injection
- **Realm**: Local database
- **Kotlinx Serialization**: JSON serialization

## 🔗 API Integration

The app connects to a Django backend with the following endpoints:
- Authentication: `/api/auth/`
- Student dashboard: `/api/students/dashboard/`
- Classes: `/api/students/classes/`
- Sessions: `/api/students/sessions/`
- Progress: `/api/students/progress/`

## 🐛 Troubleshooting

### Kotlin Native Compiler Issues
If you encounter Kotlin Native compiler errors:
1. Ensure you're using the correct build script for your platform
2. For Android development, use `-Penable.ios.targets=false`
3. For iOS development, use `-Penable.ios.targets=true` (macOS only)

### Build Failures
1. Check that Java 17+ is installed
2. Ensure all dependencies are resolved
3. Try cleaning the project: `./gradlew clean`

## 📄 License

This project is part of the Learning Partner platform.
