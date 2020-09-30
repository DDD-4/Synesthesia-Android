import PropertiesExt.BASE_URL
import PropertiesExt.KAKAO
import PropertiesExt.getBaseUrl
import PropertiesExt.getKakaoKey

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        resValue("string", KAKAO, getKakaoKey())
        resValue("string", BASE_URL, getBaseUrl())
        manifestPlaceholders = mapOf<String,String>(KAKAO to getKakaoKey())
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        getByName("debug") {

        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }
    androidExtensions {
        isExperimental = true
    }

}

dependencies {
    // Kotlin
    implementation(Libs.KOTLIN)
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Android
    implementation(Libs.CORE_KTX)
    implementation(Libs.APP_COMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.COORDINATOR_LAYOUT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.MATERIAL)
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI_KTX)

    // Libraries
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)
    implementation(Libs.OK_HTTP)
    implementation(Libs.LOGGING_INTERCEPTOR)
    implementation(Libs.GSON)
    implementation(Libs.GLIDE)
    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)
    implementation(Libs.KAKAO)
    implementation(Libs.RANGE_SEEK_BAR)

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.ANDROID_X_JUNIT)
    androidTestImplementation(TestLibs.ESPRESSO_CORE)
}