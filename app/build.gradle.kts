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

//    implementation(":data_source")
//    implementation(":data_source:bad_char_data_source")
//    implementation(":data_source:rank_data_source")

//    implementation(":data_source")
//    implementation(":data_source:bad_char_data_source")
//    implementation(":data_source:rank_data_source")

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)
    implementation(Libs.android.constraintLayout)
}
