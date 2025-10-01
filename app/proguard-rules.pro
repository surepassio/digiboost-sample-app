# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.


# Retrofit specific rules
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# Gson specific rules
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep ALL Digiboost SDK classes - DO NOT OBFUSCATE
-keep class io.surepass.digiboost.** { *; }
-keep interface io.surepass.digiboost.** { *; }
-keep enum io.surepass.digiboost.** { *; }
-keep class io.surepass.sdk.** { *; }
-keep interface io.surepass.sdk.** { *; }

# Keep all model classes (data classes) - adjust package names as needed
-keep class * extends java.lang.Object {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Specifically keep the repository and API interfaces
-keep class io.surepass.digiboost.repository.** { *; }
-keep interface io.surepass.digiboost.api.** { *; }
-keep interface io.surepass.digiboost.network.** { *; }

# Keep all methods in interfaces (Retrofit service methods)
-keepclassmembers,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Don't obfuscate anything with Retrofit annotations
-keep,allowobfuscation @interface retrofit2.http.*
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit service interfaces
-keep interface * {
    @retrofit2.http.* <methods>;
}

# Keep classes with @SerializedName annotation
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep ParameterizedType for Retrofit
-keep class sun.misc.Unsafe { *; }
-keep class * implements java.lang.reflect.ParameterizedType { *; }

# Additional rules for reflection issues
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

# Keep annotation default values
-keepattributes AnnotationDefault

# Fragment and Activity rules
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity

# ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** inflate(android.view.LayoutInflater);
    public static *** inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
    public static *** bind(android.view.View);
}
