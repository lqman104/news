plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.luqman.news.core"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KotlinCompiler.version
    }
}

dependencies {
    implementation(platform (AndroidDependencies.AndroidX.Compose.Bom.plugin))
    implementation(AndroidDependencies.AndroidX.CoreKtx.plugin)
    implementation(AndroidDependencies.Logger.Timber.plugin)
    implementation(AndroidDependencies.AndroidX.Compose.ui)

    // NETWORK
    implementation(AndroidDependencies.Network.Retrofit.plugin)
    implementation(AndroidDependencies.Network.Retrofit.converterMoshi)
    implementation(AndroidDependencies.Json.Moshi.plugin)
    kapt(AndroidDependencies.Json.Moshi.codegen)
    implementation(AndroidDependencies.Network.OkHttp.logging)


    // DI
    implementation(AndroidDependencies.Di.Hilt.plugin)
    kapt(AndroidDependencies.Di.Hilt.compiler)

    testImplementation(AndroidDependencies.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.Espresso.plugin)
}