plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
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
    val composeUIVersion = "1.6.0-beta03"
    val composeVersion = "1.5.4"
    val navVersion = "2.7.6"
    val hiltVersion = "2.47"
    val androidxHiltVersion = "1.1.0"
    val gsonVersion = "2.10"
    val roomVersion = "2.6.1"
    val okhttpVersion = "4.10.0"
    val coreVersion = "1.12.0"
    val material3Version = "1.2.0-beta01"
    val materialIconsVersion = "1.5.4"
    val lifecycleVersion = "2.7.0-rc02"
    val activityVersion = "1.8.2"
    val firebaseVersion = "32.5.0"
    val jsoupVersion = "1.17.1"
    val coilVersion = "2.5.0"
    val constraintLayoutVersion = "1.0.1"

    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.compose.ui:ui:$composeUIVersion")
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation("androidx.compose.material:material-icons-core:$materialIconsVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")

    implementation("androidx.compose.ui:ui-tooling-preview:$composeUIVersion")

    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$androidxHiltVersion")

    // Gson
    implementation("com.google.code.gson:gson:$gsonVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttpVersion"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebaseVersion"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")

    // HTML parser
    implementation("org.jsoup:jsoup:$jsoupVersion")

    // Image loading library
    implementation("io.coil-kt:coil-compose:$coilVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUIVersion")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
