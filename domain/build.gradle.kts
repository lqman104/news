plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.luqman.news.domain"
    compileSdk = AppConfig.Android.compileSdk

    defaultConfig {
        minSdk = AppConfig.Android.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.Java.version
        targetCompatibility = AppConfig.Java.version
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(AndroidDependencies.AndroidX.CoreKtx.plugin)
    implementation(AndroidDependencies.Logger.Timber.plugin)


    // DI
    implementation(AndroidDependencies.Di.Hilt.plugin)
    kapt(AndroidDependencies.Di.Hilt.compiler)

    testImplementation(AndroidDependencies.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.Espresso.plugin)
}