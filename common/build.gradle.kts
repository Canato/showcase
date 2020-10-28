plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Apps.compileSdk)

    defaultConfig {
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kotlin {
    explicitApi()
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)

    implementation(Libs.google.materialDesign)

    implementation(Libs.retrofit.retrofit)
    implementation(Libs.retrofit.gson)

    implementation(Libs.kotlin.coroutineCore)

    implementation(Libs.navigation.fragment)
    implementation(Libs.navigation.ui)

    debugImplementation(Libs.chuckerTeam.chucker)
    releaseImplementation(Libs.chuckerTeam.noOp)
}
