# Digiboost Android SDK - Complete Integration Guide

Welcome to the **Digiboost Android SDK Sample App**! This guide will walk you through a simple 3-step journey to integrate the Digiboost Android SDK V2 into your application.

## 🚀 Quick Overview

The Digiboost SDK enables secure document verification and digital identity services in your Android app. Follow this guide to get up and running in minutes.


Please find the *[Documentation Link](https://console.surepass.app/product/console/api-lists?active=16301914&leafId=16301914&path=%2Fdocs%2Fkyc%2Finitialize-16301914e0&expanded=3588860%2C3588870)*.

Visit our Website *[Surepass.io](https://surepass.io)*.

---

## 📋 Prerequisites

- **Android Studio** (latest version recommended)
- **Minimum SDK**: 26
- **Target SDK**: 33+
- **Java/Kotlin** support
- **GitHub account** (for accessing SDK repository)

---

## 🎯 3-Step Integration Journey

### Step 1: Generating Your SDK Token

Before integrating the SDK, you need to obtain an authentication token from the Digiboost API.

#### 1.1 Get API Details from Your Sales Manager
Contact your sales manager to receive:
- Digilocker initialize endpoint URL
- **Authorization Bearer Token** (required for API access)
- Access permissions

*[Watch Video Tutorial For Generating SDK Token ](https://github.com/surepassio/digiboost-sample-app/releases/download/1.0.0/API.Tutorial.mov)*.

#### 1.2 Environment Configuration

We provide two environments for different stages of development:

| Environment | Base URL | Usage |
|-------------|----------|-------|
| **UAT (Testing)** | `https://sandbox.surepass.app` | For development and testing |
| **Production** | `https://kyc-api.surepass.app` | For live applications |

#### 1.3 Digilocker Initialize API

**For UAT Environment:**
```bash
curl --location 'https://sandbox.surepass.app/api/v1/digilocker/initialize' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_GOT_FROM_SALES_MANAGER' \
--data '{
    "data": {
        "signup_flow": true,
        "logo_url": "YOUR BRAND LOGO URL",
        "voice_assistant_lang": "en",
        "voice_assistant": false,
        "retry_count": 2,
        "skip_main_screen": false
    }
}'
```

**For Production Environment:**
```bash
curl --location 'https://kyc-api.surepass.app/api/v1/digilocker/initialize' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_GOT_FROM_SALES_MANAGER' \
--data '{
    "data": {
        "signup_flow": true,
        "logo_url": "YOUR BRAND LOGO URL",
        "voice_assistant_lang": "en",
        "voice_assistant": false,
        "retry_count": 2,
        "skip_main_screen": false
    }
}'
```

#### 1.4 API Parameters Explanation

| Parameter | Type | Required | Description | Default Value |
|-----------|------|----------|-------------|---------------|
| `signup_flow` | boolean | ✅ **Required** | This parameter should always be `true` for SDK initialization | `true` |
| `logo_url` | string | ❌ Optional | Your branding logo URL - customize with your own logo | None |
| `voice_assistant_lang` | string | ❌ Optional | Voice assistant language. Possible options: `"en"` (English), `"hi"` (Hindi) | `"en"` |
| `voice_assistant` | boolean | ❌ Optional | Enable/disable voice assistant functionality | `false` |
| `retry_count` | integer | ❌ Optional | Number of allowed retries during dropout prevention | `2` |
| `skip_main_screen` | boolean | ❌ Optional | Whether to show the first intro screen or skip it | `true` |

#### 1.5 Customization Examples

**Basic Configuration (Minimal):**
```json
{
    "data": {
        "signup_flow": true
    }
}
```

**Custom Branding with Voice Assistant:**
```json
{
    "data": {
        "signup_flow": true,
        "logo_url": "https://yourcompany.com/logo.png",
        "voice_assistant_lang": "hi",
        "voice_assistant": true,
        "retry_count": 3,
        "skip_main_screen": false
    }
}
```

#### 1.6 API Response
You'll receive a response like this:

```json
{
  "data": {
    "client_id": "digilocker_cntWpMxWHbcvgghtyvxw",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiry_seconds": 600.0
  },
  "status_code": 200,
  "message_code": "success",
  "message": "Success",
  "success": true
}
```

**Important**: 
- Copy the `token` value - you'll need this for Step 3!
- The token expires in 600 seconds (10 minutes) by default
- Store the `client_id` if needed for tracking purposes

---

### Step 2: Creating GitHub Personal Access Token (PAT)

The Digiboost SDK is hosted on GitHub Packages. You need a Personal Access Token to access it.

*[Watch Video Tutorial For Generating PAT Token](https://github.com/surepassio/digiboost-sample-app/releases/download/1.0.0/Generating.PAT.Token.mp4)*

#### 2.1 Navigate to GitHub Settings
1. Go to [GitHub.com](https://github.com) and log in
2. Click your **profile picture** (top-right corner)
3. Select **Settings** from the dropdown menu

#### 2.2 Access Developer Settings
1. Scroll down to the bottom of the left sidebar
2. Click **Developer settings**

#### 2.3 Create Personal Access Token
1. Click **Personal access tokens** in the left sidebar
2. Click **Tokens (classic)**
3. Click the **Generate new token** button
4. Select **Generate new token (classic)**

#### 2.4 Configure Token Settings
1. **Note**: Enter a descriptive name like "Digiboost SDK Access"
2. **Expiration**: Choose your preferred expiration (30 days, 60 days, etc.)
3. **Scopes**: Check the following permissions:
   - ✅ `read:packages` (Download packages from GitHub Package Registry)
   - ✅ `repo` (Full control of private repositories) - if needed

#### 2.5 Generate and Save Token
1. Click **Generate token** at the bottom
2. **IMPORTANT**: Copy the token immediately - GitHub won't show it again!
3. Store it securely (you'll need it for project configuration)

#### 2.6 Configure Your Project

**Update `settings.gradle`:**
```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        mavenCentral()
        
        // GitHub Packages repository for Digiboost SDK
        maven {
            url = "https://maven.pkg.github.com/surepassio/digiboost-sample-app"
            credentials {
                username = "YOUR_GITHUB_USERNAME"  // Replace with your GitHub username
                password = "YOUR_PAT_TOKEN"        // Replace with your PAT token from Step 2.5
            }
        }
    }
}

rootProject.name = "Digiboost Sample App"
include ':app'
```

**Update `build.gradle` (app level):**
```groovy
android {
    compileSdk 33
    
    defaultConfig {
        minSdk 26  // Required minimum SDK
        targetSdk 33
        // ... other config
    }
    // ... other configuration
}

dependencies {
    // Digiboost SDK dependency
    implementation 'io.surepass.sdk:digiboost-android-sdk:1.2.1-dev'
    
    // ... your other dependencies
}
```

**Sync your project** after making these changes.

---

### Step 3: SDK Integration & Implementation

Now let's integrate the SDK into your application with proper initialization and response handling.

#### 3.1 Initialize the SDK in Your Activity

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var digiboostActivityResultLauncher: ActivityResultLauncher<Intent>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Register for activity result before onCreate completes
        registerActivityForResult()
        
        // Set up button click listener
        setupSdkLaunch()
    }
    
    private fun setupSdkLaunch() {
        binding.btnGetStarted.setOnClickListener {
            // Use the token from Step 1
            val token = "YOUR_TOKEN_FROM_STEP_1"  // Replace with actual token
            val env = "PREPROD"  // or "PROD" for production
            
            openDigiboostActivity(env, token)
        }
    }
}
```

#### 3.2 Launch Digiboost SDK Activity

```kotlin
private fun openDigiboostActivity(env: String, token: String) {
    try {
        val intent = Intent(this, InitSdk::class.java).apply {
            putExtra("token", token)
            putExtra("env", env)
        }
        digiboostActivityResultLauncher.launch(intent)
    } catch (e: Exception) {
        Log.e("MainActivity", "Error launching Digiboost SDK: ${e.message}")
        // Handle error appropriately
    }
}
```

#### 3.3 Handle SDK Response

```kotlin
private fun registerActivityForResult() {
    digiboostActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                val data = result.data
                if (data != null) {
                    val digiboostResponse = data.getStringExtra("signedResponse")
                    Log.d("MainActivity", "Digiboost Response: $digiboostResponse")
                    handleSuccessResponse(digiboostResponse)
                } else {
                    Log.w("MainActivity", "No data received from Digiboost SDK")
                }
            }
            RESULT_CANCELED -> {
                Log.i("MainActivity", "User cancelled Digiboost SDK")
                handleCancelledResponse()
            }
            else -> {
                Log.e("MainActivity", "Unexpected result code: ${result.resultCode}")
                handleErrorResponse("Unexpected result")
            }
        }
    }
}

private fun handleSuccessResponse(response: String?) {
    response?.let {
        // Process the signed response
        showResponse(it)
        // Parse and handle the response according to your app's needs
    }
}

private fun handleCancelledResponse() {
    // Handle user cancellation
    Toast.makeText(this, "Document verification cancelled", Toast.LENGTH_SHORT).show()
}

private fun handleErrorResponse(error: String) {
    // Handle error cases
    Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
}

private fun showResponse(response: String?) {
    // Display response in your UI
    // You can show it in a dialog, text view, or navigate to a results screen
    AlertDialog.Builder(this)
        .setTitle("Verification Complete")
        .setMessage("Response: $response")
        .setPositiveButton("OK", null)
        .show()
}
```

---

## 🎨 Customizing SDK Theme

You can customize the SDK's appearance by modifying your app's `colors.xml`:

**File: `res/values/colors.xml`**
```xml
<resources>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
    <color name="background_color">#B82C5A</color>
    
    <!-- Add this line to customize Digiboost SDK theme -->
    <color name="surepass_color">#FF9800</color>  <!-- Replace with your brand color -->
</resources>
```

After making changes:
1. **Clean and rebuild** your project
2. The SDK will automatically use your custom theme color

---

## 🔧 Troubleshooting

### Common Issues and Solutions

#### Issue: "Unable to resolve dependency"
**Solution**: 
- Verify your GitHub username and PAT token in `settings.gradle`
- Ensure your PAT token has `read:packages` permission
- Check your internet connection

#### Issue: "Token expired" error
**Solution**:
- Generate a new token using Step 1
- Tokens expire after the specified time (usually 10 minutes)

#### Issue: "Minimum SDK version" error
**Solution**:
- Ensure your `minSdk` is set to 26 or higher
- Update your `build.gradle` accordingly

#### Issue: SDK not launching
**Solution**:
- Verify the token format is correct
- Check that all dependencies are properly synced
- Ensure you've registered the activity result launcher before `onCreate` completes

---

## 📱 Testing Your Integration

1. **Build and run** your application
2. **Tap the "Get Started" button** (or your trigger button)
3. **Verify the SDK launches** successfully
4. **Complete a test verification** to ensure the full flow works
5. **Check the logs** for the response data

---

## 📞 Support

If you encounter any issues:
- **Check the troubleshooting section** above
- **Contact your sales manager** for API-related issues
- **Contact Tech Support at** <techsupport@surepass.app> 
- **Review the logs** for detailed error messages

---

## 📄 License

This sample application is provided as-is for integration testing purposes.

---

**✨ You're all set! Your Digiboost SDK integration is complete.** 
