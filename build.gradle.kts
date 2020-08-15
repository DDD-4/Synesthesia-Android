// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:4.0.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS}")

    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
