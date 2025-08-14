#!/bin/bash

echo "🔍 Verifying GitHub Actions setup..."

# Make development script executable
chmod +x dev-build.sh

# Check if all required files exist
echo "📁 Checking file structure..."

if [ -f ".github/workflows/android-build.yml" ]; then
    echo "✅ Android workflow created"
else
    echo "❌ Android workflow missing"
fi

if [ -f ".github/workflows/ios-build.yml" ]; then
    echo "✅ iOS workflow created"
else
    echo "❌ iOS workflow missing"
fi

if [ -f "dev-build.sh" ]; then
    echo "✅ Development script created"
else
    echo "❌ Development script missing"
fi

if [ -f "GITHUB_ACTIONS_GUIDE.md" ]; then
    echo "✅ Setup guide created"
else
    echo "❌ Setup guide missing"
fi

echo ""
echo "🎉 Setup verification complete!"
echo ""
echo "📋 Next steps:"
echo "1. git add ."
echo "2. git commit -m 'Add GitHub Actions workflows'"
echo "3. git push origin main"
echo "4. Check GitHub Actions tab for builds"
echo ""
echo "🚀 Test local build: ./dev-build.sh" 