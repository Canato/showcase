plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Apps.compileSdk)

    defaultConfig {
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))

//    implementation(project(Libs.showcase.dataSource.root)) todo canato check wtf
    implementation(project(Libs.showcase.dataSource.bad))
    implementation(project(Libs.showcase.dataSource.rank))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)
    implementation(Libs.android.constraintLayout)
}
