# Digilocker Android SDK - Integration Guide

This repository is the public distribution channel for the **Digilocker Android SDK** (formerly Digiboost): the SDK artifact is published to this repository's GitHub Packages registry, and the sample app here shows a complete working integration.

Please find the *[Documentation Link](https://console.surepass.app/product/console/api-lists?active=16301914&leafId=16301914&path=%2Fdocs%2Fkyc%2Finitialize-16301914e0&expanded=3588860%2C3588870)*.

Visit our Website *[Surepass.io](https://surepass.io)*.

---

## Prerequisites

- **Android Studio** (latest version recommended)
- **Minimum SDK**: 28
- **Compile SDK**: 36 (Target SDK 34+)
- **Java/Kotlin** support
- **GitHub account** (for accessing the SDK package)

---

## 3-Step Integration Journey

### Step 1: Generating Your SDK Token

Before integrating the SDK, you need to obtain an authentication token from the API.

#### 1.1 Get API Details from Your Sales Manager

Contact your sales manager to receive:
- Digilocker initialize endpoint URL
- **Authorization Bearer Token** (required for API access)
- Access permissions

*[Watch Video Tutorial For Generating SDK Token](https://github.com/surepassio/digiboost-sample-app/releases/download/1.0.0/API.Tutorial.mov)*

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
        "auth_type": "app",
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
        "auth_type": "app",
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
| `signup_flow` | boolean | Yes | This parameter should always be `true` for SDK initialization | `true` |
| `auth_type` | string | Yes | Authentication type - must be set to `"app"` for SDK integration | `"app"` |
| `logo_url` | string | No | Your branding logo URL - customize with your own logo | None |
| `voice_assistant_lang` | string | No | Voice assistant language. Possible options: `"en"` (English), `"hi"` (Hindi) | `"en"` |
| `voice_assistant` | boolean | No | Enable/disable voice assistant functionality | `false` |
| `retry_count` | integer | No | Number of allowed retries during dropout prevention | `2` |
| `skip_main_screen` | boolean | No | Whether to show the first intro screen or skip it | `true` |

#### 1.5 Customization Examples

**Basic Configuration (Minimal):**
```json
{
    "data": {
        "signup_flow": true,
        "auth_type": "app"
    }
}
```

**Custom Branding with Voice Assistant:**
```json
{
    "data": {
        "signup_flow": true,
        "auth_type": "app",
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

The Digilocker SDK is hosted on GitHub Packages. You need a Personal Access Token to download it, even though the package is public - this is a GitHub Packages requirement.

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
1. **Note**: Enter a descriptive name like "Digilocker SDK Access"
2. **Expiration**: Choose your preferred expiration (30 days, 60 days, etc.)
3. **Scopes**: Check the following permission:
   - `read:packages` (Download packages from GitHub Package Registry)

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
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // GitHub Packages repository for the Digilocker SDK
        maven {
            url = "https://maven.pkg.github.com/surepassio/digiboost-sample-app"
            credentials {
                username = "YOUR_GITHUB_USERNAME"  // Replace with your GitHub username
                password = "YOUR_PAT_TOKEN"        // Replace with your PAT token from Step 2.5
            }
        }
    }
}
```

**Update `build.gradle` (app level):**
```groovy
android {
    compileSdk 36

    defaultConfig {
        minSdk 28  // Required minimum SDK
        targetSdk 34
        // ... other config
    }
    // ... other configuration
}

dependencies {
    // Digilocker SDK dependency
    implementation 'io.surepass.sdk:digilocker-sdk:1.2.2'

    // ... your other dependencies
}
```

**Sync your project** after making these changes.

Note on ProGuard/R8: the SDK ships its own consumer rules inside the AAR, so apps that build with `minifyEnabled true` do not need any manual keep rules for the SDK.

---

### Step 3: SDK Integration & Implementation

Now let's integrate the SDK into your application with proper initialization and response handling.

#### 3.1 Initialize the SDK in Your Activity

```kotlin
import io.surepass.digilocker.ui.activity.Environment
import io.surepass.digilocker.ui.activity.InitSdk

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sdkLauncher: ActivityResultLauncher<Intent>

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
            val env = Environment.SANDBOX.value   // "PREPROD"; use Environment.PROD.value for production

            openDigilockerActivity(env, token)
        }
    }
}
```

#### 3.2 Launch the Digilocker SDK Activity

```kotlin
private fun openDigilockerActivity(env: String, token: String) {
    val intent = Intent(this, InitSdk::class.java).apply {
        putExtra(InitSdk.EXTRA_TOKEN, token)  // JWT, without the "Bearer " prefix
        putExtra(InitSdk.EXTRA_ENV, env)
    }
    sdkLauncher.launch(intent)
}
```

#### 3.3 Handle the SDK Response

```kotlin
private fun registerActivityForResult() {
    sdkLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                val signedResponse = result.data?.getStringExtra(InitSdk.EXTRA_SIGNED_RESPONSE)
                if (signedResponse != null) {
                    Log.d("MainActivity", "Digilocker response: $signedResponse")
                    handleSuccessResponse(signedResponse)
                } else {
                    Log.w("MainActivity", "No data received from the Digilocker SDK")
                }
            }
            RESULT_CANCELED -> {
                Log.i("MainActivity", "User cancelled the Digilocker SDK")
            }
            else -> {
                Log.e("MainActivity", "Unexpected result code: ${result.resultCode}")
            }
        }
    }
}

private fun handleSuccessResponse(response: String) {
    // The response is a JSON string with status_code, message and data fields.
    // Parse and handle it according to your app's needs. A status_code of 200
    // means the user completed the flow and shared the requested documents.
}
```

---

## Step 4: Download Aadhaar

After successful verification through the SDK, you can download the Aadhaar document using the Download Aadhaar API.

### 4.1 Download Aadhaar API

Use the `client_id` received from the SDK success response to download the Aadhaar document.

**For UAT Environment:**

```bash
curl --location 'https://sandbox.surepass.app/api/v1/digilocker/download-aadhaar' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_GOT_FROM_SALES_MANAGER' \
--data '{
    "client_id": "CLIENT_ID_FROM_SDK_SUCCESS_RESPONSE"
}'
```

**For Production Environment:**

```bash
curl --location 'https://kyc-api.surepass.app/api/v1/digilocker/download-aadhaar' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_GOT_FROM_SALES_MANAGER' \
--data '{
    "client_id": "CLIENT_ID_FROM_SDK_SUCCESS_RESPONSE"
}'
```

### 4.2 API Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `client_id` | string | Yes | The client ID received from the SDK success response after verification |

### 4.3 API Response

**Success Response:**

```json
{
  "data": {
    "aadhaar_pdf": "base64_encoded_pdf_data",
    "aadhaar_xml": "base64_encoded_xml_data",
    "name": "John Doe",
    "aadhaar_number": "XXXX-XXXX-1234",
    "date_of_birth": "01-01-1990",
    "gender": "M",
    "address": {
      "house": "123",
      "street": "Main Street",
      "landmark": "Near Park",
      "locality": "Central Area",
      "vtc": "City Name",
      "district": "District Name",
      "state": "State Name",
      "pincode": "123456"
    }
  },
  "status_code": 200,
  "message_code": "success",
  "message": "Aadhaar downloaded successfully",
  "success": true
}
```

**Error Response:**

```json
{
  "data": null,
  "status_code": 400,
  "message_code": "error",
  "message": "Invalid client_id or verification not completed",
  "success": false
}
```

### 4.4 Important Notes

- **Client ID Source**: The `client_id` must be obtained from the SDK success response after verification
- **Data Format**: PDF and XML data are base64 encoded and need to be decoded before use
- **Security**: Store the downloaded data securely according to your compliance requirements
- **Error Handling**: Always implement proper error handling for network requests
- **Permissions**: Ensure your app has appropriate storage permissions if saving files locally

---

## Customizing the SDK Theme

The SDK's primary color comes from its `sure_pass_color` resource. Override it by defining the same color name in your app's `colors.xml` - app resources take precedence over library resources:

**File: `res/values/colors.xml`**
```xml
<resources>
    <!-- Add this line to customize the Digilocker SDK theme -->
    <color name="sure_pass_color">#FF9800</color>  <!-- Replace with your brand color -->
</resources>
```

After making changes:
1. **Clean and rebuild** your project
2. The SDK will automatically use your custom theme color

---

## Upgrading from digiboost-android-sdk (1.x)

Version 1.2.2 renames the library. To upgrade:

1. **Change the dependency coordinates:**
   - Old: `implementation 'io.surepass.sdk:digiboost-android-sdk:<version>'`
   - New: `implementation 'io.surepass.sdk:digilocker-sdk:1.2.2'`
2. **Change the imports** - the SDK package moved from `io.surepass.digiboost` to `io.surepass.digilocker`:
   - `import io.surepass.digilocker.ui.activity.InitSdk`
   - `import io.surepass.digilocker.ui.activity.Environment`
3. **Raise `minSdk` to 28** if your app is below it.
4. **Remove any manual ProGuard/R8 keep rules** for `io.surepass.digiboost.**` - the SDK now ships consumer rules inside the AAR.
5. Behavior changes to be aware of:
   - The SDK now survives Android killing the app process in the background: instead of crashing, it restarts its flow automatically.
   - Network and parsing failures are reported with `status_code` 450 (previously these were reported as 401; 401 now indicates an actual authorization failure).
   - Telemetry failures no longer abort the verification flow.
   - The SDK screens are portrait-only.
   - Workarounds for the earlier process-death crash (for example `android:stateNotNeeded="true"` on the SDK activity, or clearing its saved state via lifecycle callbacks) are no longer needed and should be removed.

---

## Troubleshooting

### Common Issues and Solutions

#### Issue: "Unable to resolve dependency"
**Solution**:
- Verify your GitHub username and PAT token in `settings.gradle`
- Ensure your PAT token has `read:packages` permission
- Check that the dependency uses the new artifact name: `digilocker-sdk`
- Check your internet connection

#### Issue: "Token expired" error
**Solution**:
- Generate a new token using Step 1
- Tokens expire after the specified time (usually 10 minutes)

#### Issue: "Minimum SDK version" error
**Solution**:
- Ensure your `minSdk` is set to 28 or higher
- Update your `build.gradle` accordingly

#### Issue: SDK not launching
**Solution**:
- Verify the token format is correct (pass the raw JWT, without the "Bearer " prefix)
- Check that all dependencies are properly synced
- Ensure you've registered the activity result launcher before `onCreate` completes

Try Our *[Sample App](https://github.com/surepassio/digiboost-sample-app/releases/download/1.0.0/digiboost-sample-app.apk)*

---

## Testing Your Integration

1. **Build and run** your application
2. **Tap the "Get Started" button** (or your trigger button)
3. **Verify the SDK launches** successfully
4. **Complete a test verification** to ensure the full flow works
5. **Check the logs** for the response data

---

## Support

If you encounter any issues:
- **Check the troubleshooting section** above
- **Contact your sales manager** for API-related issues
- **Contact Tech Support at** <techsupport@surepass.app>
- **Review the logs** for detailed error messages

---

## License

This sample application is provided as-is for integration testing purposes.
