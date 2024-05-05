import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.simple_movie_app"
    compileSdk = 34

    // Load config file
    val configProperties = Properties()
    val configPropertiesFile = rootProject.file("properties/config.properties")
    if (configPropertiesFile.exists()) {
        configProperties.load(configPropertiesFile.inputStream())
    }

    val serverEndPoint = configProperties.getProperty("SERVER_ENDPOINT") ?: ""
    val serverApiKey = configProperties.getProperty("SERVER_API_KEY") ?: ""


    defaultConfig {
        applicationId = "com.example.simple_movie_app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Add properties to your build config
        buildConfigField("String", "SERVER_ENDPOINT", "\"$serverEndPoint\"")
        buildConfigField("String", "SERVER_API_KEY", "\"$serverApiKey\"")


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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val androidLifecycleVersion = "2.7.0"
val androidNavigationVersion = "2.7.7"
val retrofitVersion = "2.11.0"
val recyclerviewVersion = "1.3.2"
val roomVersion = "2.6.1"
val pagingVersion = "3.2.1"
val coroutinesVersion = "1.7.3"
val okhttp3LoggingVersion = "4.9.1"
val coilComposeVersion = "2.1.0"

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion") // Verify the version is up to date.


    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidLifecycleVersion")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")

    implementation("androidx.navigation:navigation-fragment-ktx:$androidNavigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$androidNavigationVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("androidx.recyclerview:recyclerview:$recyclerviewVersion")

    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    implementation("io.coil-kt:coil-compose:$coilComposeVersion")

    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.paging:paging-compose:3.3.0-rc01")

    // Optional: Add Logging Interceptor for Retrofit (useful for debugging)

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("org.mockito:mockito-android:4.3.1")

    // Debug dependencies
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3LoggingVersion")
}