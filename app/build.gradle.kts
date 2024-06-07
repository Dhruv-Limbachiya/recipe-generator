import java.util.Properties

plugins {
    alias(libs.plugins.kspDevTools)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "com.dhruvv.recipegenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhruvv.recipegenerator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        buildFeatures {
            buildConfig = true
        }
        debug {
            buildConfigField("String", "GEMINI_API_KEY", project.getLocalProperty("gemini_api_key"))
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

fun Project.getLocalProperty(key: String, file: String = "local.properties"): String {
    val properties = Properties()
    val localPropertiesFile = rootDir.resolve(file)
    if (localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    } else {
        throw IllegalStateException("File $file not found.")
    }
    return properties.getProperty(key) ?: ""
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

     // navigation
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    // dagger hilt
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing) // For instrumented tests.

    // room
    implementation(libs.room.android)
    // To use Kotlin annotation processing tool (ksp)
    ksp(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)
    // optional - Test helpers
    testImplementation(libs.room.testing)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)

    // test flow
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)

    // navigation
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // gson
    implementation(libs.gson)

    // moshi
    implementation(libs.moshi)
}