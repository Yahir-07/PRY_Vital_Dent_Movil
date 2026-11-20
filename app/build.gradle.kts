plugins {
    alias(libs.plugins.android.application)
    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.example.pry_vital_dent_movil"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.pry_vital_dent_movil"
        minSdk = 28
        targetSdk = 37
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    //1. Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // 2. Converter Gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // 3. OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // 4. Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // 5. Corrutinas de Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // 6. Biometría (huella o rostro en el login)
    implementation("androidx.biometric:biometric:1.1.0")

    // 7. Multimedia: reproductor de video ExoPlayer (Media3) con controles y pantalla completa
    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")

    // Pruebas unitarias (src/test)
    testImplementation(libs.junit)

}