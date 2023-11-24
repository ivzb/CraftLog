plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ivzb.craftlog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ivzb.craftlog"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    val compose_ui_version = "1.6.0-beta01"
    val compose_version = "1.5.4"
    val nav_version = "2.7.5"
    val hilt_version = "2.47"
    val androidx_hilt_version = "1.1.0"
    val gson_version = "2.10"
    val room_version = "2.6.0"
    val okhttp_version = "4.10.0"
    val core_version = "1.12.0"
    val material3_version = "1.2.0-alpha11"
    val lifecycle_version = "2.7.0-rc01"
    val activity_version = "1.8.1"
    val firebase_version = "32.5.0"

    implementation("androidx.core:core-ktx:$core_version")
    implementation("androidx.compose.ui:ui:$compose_ui_version")
    implementation("androidx.compose.material3:material3:$material3_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")

    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")

    implementation("androidx.activity:activity-compose:$activity_version")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:$androidx_hilt_version")

    // Gson
    implementation("com.google.code.gson:gson:$gson_version")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttp_version"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebase_version"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}