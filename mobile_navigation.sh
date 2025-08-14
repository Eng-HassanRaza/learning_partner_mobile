#!/bin/bash

# Learning Partner Mobile Development - Navigation Script

echo "📱 Learning Partner Mobile Development"
echo "======================================"

# Get current directory
CURRENT_DIR=$(pwd)
PROJECT_NAME=$(basename "$CURRENT_DIR")

echo "📍 Current location: $CURRENT_DIR"
echo "📁 Current project: $PROJECT_NAME"
echo ""

# Function to show project status
show_status() {
    echo "📊 Mobile Project Status:"
    echo "========================"
    
    if [[ "$PROJECT_NAME" == "learning_partner_mobile" ]]; then
        echo "✅ Mobile Project Directory"
        echo "   - Context files: Ready"
        echo "   - API documentation: Available"
        echo "   - Django backend: Connected"
        echo "   - KMP project: Ready to start"
        echo ""
        echo "📁 Context Files:"
        echo "   - CONTEXT_TRANSFER.md (Complete context)"
        echo "   - PROJECT_SUMMARY.md (Project overview)"
        echo "   - API_DOCUMENTATION.md (API endpoints)"
        echo "   - MOBILE_DEVELOPMENT_REFERENCE.md (Quick reference)"
        echo "   - QUICK_START.md (Quick start guide)"
        echo ""
        echo "🔗 Django Backend: ../learning_partner/"
        echo "📱 API Base URL: http://dashboard.localhost:8000/api/v1/"
        echo "👤 Test User: hassanrazade@gmail.com"
        echo "📊 Test Data: Class 66, Session 84"
        
    else
        echo "❓ Not in mobile project directory"
        echo "   Expected: learning_partner_mobile"
    fi
}

# Function to show available commands
show_commands() {
    echo ""
    echo "🛠️  Available Commands:"
    echo "======================"
    
    if [[ "$PROJECT_NAME" == "learning_partner_mobile" ]]; then
        echo "  context      - Show complete project context"
        echo "  api          - Show API documentation"
        echo "  quick-start  - Show quick start guide"
        echo "  setup        - Start KMP project setup"
        echo "  help         - Show this help"
        
    else
        echo "  help         - Show this help"
    fi
}

# Function to show context
show_context() {
    echo "📋 Project Context Summary"
    echo "========================="
    echo ""
    echo "🎯 Current Status:"
    echo "  - Django Backend: ✅ COMPLETE & FUNCTIONAL"
    echo "  - API Endpoints: ✅ 100% TESTED & WORKING"
    echo "  - Mobile Development: 🚧 READY TO START"
    echo ""
    echo "🔗 API Connection:"
    echo "  - Base URL: http://dashboard.localhost:8000/api/v1/"
    echo "  - Test User: hassanrazade@gmail.com / Hassan0331"
    echo "  - Test Data: Class ID 66, Session ID 84"
    echo ""
    echo "📱 Mobile App Features:"
    echo "  - Authentication (Django credentials)"
    echo "  - Dashboard (classes, progress)"
    echo "  - Session Practice (AI content)"
    echo "  - Vocabulary Practice (flashcards)"
    echo "  - Grammar Practice (interactive tables)"
    echo "  - Exercise Practice (fill-in-blank, etc.)"
    echo "  - Progress Tracking"
    echo "  - Offline Support"
    echo ""
    echo "🏗️ Architecture:"
    echo "  - Framework: Kotlin Multiplatform"
    echo "  - UI: Jetpack Compose (Android) + SwiftUI (iOS)"
    echo "  - Approach: Modular, component-based"
    echo "  - Data Source: Django API"
    echo ""
    echo "📁 Reference Files:"
    echo "  - CONTEXT_TRANSFER.md (Complete context)"
    echo "  - PROJECT_SUMMARY.md (Project overview)"
    echo "  - API_DOCUMENTATION.md (API details)"
    echo "  - MOBILE_DEVELOPMENT_REFERENCE.md (Quick reference)"
    echo "  - QUICK_START.md (Quick start guide)"
}

# Function to show API documentation
show_api() {
    echo "🔗 API Documentation"
    echo "==================="
    echo ""
    echo "Base URL: http://dashboard.localhost:8000/api/v1/"
    echo ""
    echo "Working Endpoints:"
    echo "  ✅ POST /auth/ - Login"
    echo "  ✅ GET /student/dashboard/ - Student overview"
    echo "  ✅ GET /student/classes/ - Enrolled classes"
    echo "  ✅ GET /student/class/{id}/ - Class details"
    echo "  ✅ GET /student/class/{id}/session/{id}/ - Session content"
    echo "  ✅ GET /student/content/{id}/vocabulary/ - Vocabulary"
    echo "  ✅ GET /student/content/{id}/exercises/ - Exercises"
    echo "  ✅ GET /student/content/{id}/grammar/ - Grammar"
    echo "  ✅ GET /student/content/{id}/flashcards/ - Flashcards"
    echo ""
    echo "Test Credentials:"
    echo "  Username: hassanrazade@gmail.com"
    echo "  Password: Hassan0331"
    echo ""
    echo "Test Data:"
    echo "  Class ID: 66"
    echo "  Session ID: 84 (has rich AI content)"
    echo "  User Type: student"
}

# Function to show quick start
show_quick_start() {
    echo "🚀 Quick Start Guide"
    echo "==================="
    echo ""
    echo "Next Steps:"
    echo "  1. Set up KMP project structure"
    echo "  2. Create shared data models (matching Django)"
    echo "  3. Build API client (connecting to Django)"
    echo "  4. Implement authentication"
    echo "  5. Create basic UI components"
    echo ""
    echo "Development Approach:"
    echo "  - Modular Development: Single shared model"
    echo "  - Component-Based UI: Reusable components"
    echo "  - Linux Development: Android-first"
    echo "  - Scalable: Future LiveKit integration"
    echo ""
    echo "Reference Files:"
    echo "  - CONTEXT_TRANSFER.md (Complete context)"
    echo "  - API_DOCUMENTATION.md (API details)"
    echo "  - PROJECT_OVERVIEW.md (Architecture)"
    echo "  - MOBILE_DEVELOPMENT_REFERENCE.md (Quick reference)"
}

# Function to start setup
start_setup() {
    echo "🚀 Starting KMP Project Setup"
    echo "============================="
    echo ""
    echo "This will guide you through:"
    echo "  1. Environment setup (Android Studio, KMP tools)"
    echo "  2. Project structure creation"
    echo "  3. Shared module setup"
    echo "  4. Data models creation"
    echo "  5. API client implementation"
    echo ""
    echo "Ready to start? (y/n)"
    read -r response
    if [[ "$response" =~ ^[Yy]$ ]]; then
        echo "✅ Starting setup process..."
        echo "📋 Reference CONTEXT_TRANSFER.md for complete context"
        echo "🔗 Use API_DOCUMENTATION.md for endpoint details"
        echo "📱 Follow MOBILE_DEVELOPMENT_REFERENCE.md for guidance"
    else
        echo "⏸️  Setup cancelled"
    fi
}

# Main script logic
case "$1" in
    "context")
        show_context
        ;;
    "api")
        show_api
        ;;
    "quick-start")
        show_quick_start
        ;;
    "setup")
        start_setup
        ;;
    "help"|"")
        show_status
        show_commands
        ;;
    *)
        echo "❌ Unknown command: $1"
        echo ""
        show_commands
        ;;
esac
