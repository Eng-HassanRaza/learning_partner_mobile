#!/bin/bash

# iOS development build script
# This script enables iOS targets for development on macOS

echo "🍎 Starting iOS development build..."

echo "🧹 Cleaning project..."
./gradlew clean -Penable.ios.targets=true --stacktrace

echo "📦 Compiling shared module (all targets)..."
./gradlew shared:compileKotlinMetadata -Penable.ios.targets=true --stacktrace

echo "🤖 Compiling Android app..."
./gradlew androidApp:compileDebugKotlin -Penable.ios.targets=true --stacktrace

echo "🍎 Building iOS frameworks..."
./gradlew linkReleaseFrameworkIosArm64 -Penable.ios.targets=true --stacktrace
./gradlew linkReleaseFrameworkIosSimulatorArm64 -Penable.ios.targets=true --stacktrace

echo "🧪 Running tests..."
./gradlew test -Penable.ios.targets=true --stacktrace

echo "✅ iOS development build completed!"
echo "📱 iOS frameworks are available in shared/build/xcode-frameworks/" 