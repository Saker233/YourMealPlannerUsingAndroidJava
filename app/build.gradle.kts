plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.yourmealplanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.yourmealplanner"
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

//    def nav_version = "2.3+"

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Lottie dependency
    implementation ("com.airbnb.android:lottie:4.2.2")

    // Include GIF drawable if needed
    implementation ("com.github.bumptech.glide:okhttp3-integration:4.15.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")

    // Firebase Firestore
    implementation ("com.google.firebase:firebase-firestore:24.3.0")

    // Navigation components
//    implementation ("androidx.navigation:navigation-fragment:2.8.1")
//    implementation ("androidx.navigation:navigation-ui:2.8.1")

    implementation ("androidx.navigation:navigation-fragment:2.3+")
    implementation ("androidx.navigation:navigation-ui:2.3+")

    implementation (libs.appcompat)
    implementation (libs.activity)
    implementation (libs.constraintlayout)
    testImplementation (libs.junit)
    androidTestImplementation (libs.ext.junit)
    androidTestImplementation (libs.espresso.core)
}