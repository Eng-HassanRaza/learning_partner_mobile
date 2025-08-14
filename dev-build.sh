#!/bin/bash

echo "🚀 Starting lightweight development build..."

# Run only essential tasks for local development
./gradlew clean
./gradlew shared:compileKotlinMetadata
./gradlew androidApp:compileDebugKotlin
./gradlew test

echo "✅ Development build completed!"
echo "📱 For full builds, check GitHub Actions"
echo "🔗 Push to GitHub to trigger remote builds" 