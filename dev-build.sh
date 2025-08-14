#!/bin/bash

# Lightweight development build script
# This script runs only essential tasks for local development

echo "🧹 Cleaning project..."
./gradlew clean -Penable.ios.targets=false --stacktrace

echo "📦 Compiling shared module (Android target only)..."
./gradlew shared:compileDebugKotlinAndroid -Penable.ios.targets=false --stacktrace

echo "🤖 Compiling Android app..."
./gradlew androidApp:compileDebugKotlin -Penable.ios.targets=false --stacktrace

echo "🧪 Running tests..."
./gradlew test -Penable.ios.targets=false --stacktrace

echo "✅ Development build completed!" 