# GitHub Actions Setup Guide

## 🚀 Quick Start

### Local Development
```bash
# Lightweight build for development
./dev-build.sh
```

### Remote Building
```bash
# Push to trigger GitHub Actions builds
git add .
git commit -m "Your changes"
git push origin main
```

## 📱 How to Get Your APK

1. Go to your GitHub repository
2. Click "Actions" tab
3. Click on the latest "Android Build & Test" run
4. Scroll down to "Artifacts"
5. Download "android-debug-apk"

## 🔧 Troubleshooting

### If builds fail:
1. Check the Actions logs for specific errors
2. Verify your code compiles locally with `./dev-build.sh`
3. Check for dependency issues

### Local development tips:
- Use `./dev-build.sh` for quick testing
- Only push when you want full builds
- Monitor Actions tab for build status

## 📋 What Each Workflow Does

### Android Build & Test
- ✅ Compiles your KMP project
- ✅ Runs all tests
- ✅ Generates Android APK
- ✅ Uploads APK as artifact

### iOS Build & Test
- ✅ Compiles iOS framework
- ✅ Runs on macOS runner
- ✅ Uploads framework as artifact

## 🎯 Benefits

- **No local resource usage** for heavy builds
- **Automatic testing** on every push
- **APK generation** without Android Studio
- **iOS framework building** without macOS
- **Free** with GitHub's generous limits

## 🔄 Daily Workflow

```bash
# 1. Make code changes
# 2. Test locally (lightweight)
./dev-build.sh

# 3. Commit and push
git add .
git commit -m "Add new feature"
git push origin main

# 4. Check GitHub Actions for build results
# 5. Download APK from artifacts
```

## 📊 Build Status

- **Green checkmark** = Build successful
- **Red X** = Build failed (check logs)
- **Yellow dot** = Build in progress

## 🎉 Success Indicators

✅ **Android workflow** completes successfully  
✅ **APK artifact** is available for download  
✅ **All tests pass**  
✅ **No compilation errors**  

## 🆘 Getting Help

1. **Check Actions logs** for detailed error messages
2. **Run local tests** with `./dev-build.sh`
3. **Verify dependencies** are correctly configured
4. **Check GitHub status** for any service issues 