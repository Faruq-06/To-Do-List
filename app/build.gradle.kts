plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.todolistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolistapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.junit.junit)

    // Testing dependencies
    testImplementation(libs.junit)  // Use version catalog reference
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // AndroidX Test dependencies
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
}