#!/bin/bash

echo "🚀 Starting lightweight development build..."

# Run only essential tasks for local development
./gradlew clean --stacktrace
./gradlew shared:compileKotlinMetadata --stacktrace
./gradlew androidApp:compileDebugKotlin --stacktrace
./gradlew test --stacktrace

echo "✅ Development build completed!"
echo "📱 For full builds, check GitHub Actions"
echo "🔗 Push to GitHub to trigger remote builds" 