plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.jwtapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jwtapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildFeatures {

            viewBinding = true
            dataBinding = true

        }
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


    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}