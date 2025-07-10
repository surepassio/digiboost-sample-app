# Digiboost Android SDK- Sample App

Sample application for Digiboost Android SDK - V2.
### Step to use the SDK below as well:
#### 1. settings.gradle :
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
            //enter github user name and github token
            maven {
                url = "https://maven.pkg.github.com/surepassio/digiboost-sample-app"
                credentials {
                    username = "USER_NAME"
                    password = "PAT_TOKEN"//https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
                    // (Allow Package Read Permission in token)
                }
            }
    
    
        }
    }
rootProject.name = "Digiboost Sample App"
include ':app'
```

#### 2. build.grade (app):
```groovy
   minSdk 26 //min sdk should be 26
   
   dependencies {
         implementation 'io.surepass.sdk:digiboost-android-sdk:1.0.0-dev'
   }
```
Make sure to sync your project after adding the dependency.
#### 3. Generating Your SDK Token :
You will be getting the API collection from your sales manager
1. **Obtain API Details**  
   Get the Digilocker initialize endpoint and any credentials from your sales manager.
2. **Request a Token**  
   ```bash
   curl https://sandbox.surepass.io/api/v1/digilocker/initialize```
2. After Initialize You’ll receive a response like:
```json
    {
    "data": {
        "client_id": "digilocker_cntWpMxWHbcvgghtyvxw",
        "token": "TOKEN FOR INTITALIZE SDK",
        "expiry_seconds": 600.0
    },
    "status_code": 200,
    "message_code": "success",
    "message": "Success",
    "success": true
}
```

3. Copy token from response paste it below at placeholder YOUR TOKEN and intialize SDK.

#### 3. Inside Application:
```kotlin
   binding.btnGetStarted.setOnClickListener {
//            val token = binding.etApiToken.text.toString()
        val token = "YOUR TOKEN"
        val env = "PREPROD"
        openActivity(env, token)
    }
```
SDK will be started from openActivity function
```kotlin 
    private fun openActivity(env: String, token: String) {
        val intent = Intent(this, InitSdk::class.java)
        intent.putExtra("token", token)
        intent.putExtra("env", env)
        digiboostActivityResultLauncher.launch(intent)
    }
```
Response can be obtained in 
```kotlin 
   private fun registerActivityForResult() {
        digiboostActivityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result ->
                    val resultCode = result.resultCode
                    val data = result.data
                    if (resultCode == RESULT_OK && data != null) {
                        val digibootResponse = data.getStringExtra("signedResponse")
                        Log.e("MainActivity", "digiboost Response $digibootResponse")
                        showResponse(digibootResponse)
                    }
                })
    }
```
For better clarification you can check the code details inside the project

### Change Theme Of SDK

To customize the theme of the SDK, you can modify the `colors.xml` file. :

1. **Locate the `colors.xml` file**:
   You can usually find this file in the `res/values` directory of your Android project.
   Inside the `colors.xml` file, you'll see a style defined for your application theme. It typically looks like this:

      ```xml
    <resources>
        <color name="black">#FF000000</color>
        <color name="white">#FFFFFFFF</color>
        <color name="background_color">#B82C5A</color>
    </resources>
    ```


3. **Implement the Surepass color**:
   Add the `<color name="surepass_color">#FF9800</color>` line. Replace HEX code with the color resource you want to use.
   For example, if you want to change the theme color to red, you can use:

   ```xml
   <color name="surepass_color">@color/red</color>
   ```

4. **Save your changes**.

5. **Rebuild your project**:
   After making changes to the theme, rebuild your project to apply the updated theme throughout your application.
