# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# The Digilocker SDK (1.2.2+) ships its own consumer R8 rules inside the AAR
# (Gson models, the WebView JavaScript bridge, Retrofit services), and modern
# Retrofit/OkHttp/Gson releases bundle their own rules as well, so this app
# needs no manual keep rules for the SDK.
