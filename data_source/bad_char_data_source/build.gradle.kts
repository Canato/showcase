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
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
        jvmTarget = JavaVersion.VERSION_11.toString()
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

    // retrofit
    implementation(Libs.retrofit.retrofit)
    implementation(Libs.retrofit.gson)

    // test
    testImplementation(Libs.test.junit)
    testImplementation(Libs.test.mockK)
    testImplementation(Libs.kotlin.coroutineCore)
}
