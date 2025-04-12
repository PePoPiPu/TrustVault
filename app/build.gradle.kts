plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.trustvault"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.trustvault"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore.preferences)
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("androidx.compose.ui:ui:1.7.8") // Compose UI most recent version
    implementation("androidx.compose.material:material:1.7.8") // Material Design with compose
    implementation("androidx.compose.animation:animation:1.7.8") // Animations
    implementation("androidx.compose.material:material-icons-extended-android:1.7.8")
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.1.2")
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation("com.lambdapioneer.argon2kt:argon2kt:1.6.0") // Argon2 binding
    implementation("androidx.compose.material:material:1.6.0")
    implementation("com.github.arpitkatiyar1999:Country-Picker:2.1.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}



// Allow references to generated code
kapt {
    correctErrorTypes = true
}