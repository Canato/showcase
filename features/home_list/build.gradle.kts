plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
}

android {
    compileSdkVersion(Apps.compileSdk)

    defaultConfig {
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-XXLanguage:+InlineClasses"
        )
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))

    implementation(project(Libs.showcase.features.rank))
    implementation(project(Libs.showcase.features.chat))
    implementation(project(Libs.showcase.features.properties))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)

    implementation(Libs.kotlin.coroutineCore)
    implementation(Libs.kotlin.coroutineAndroid)
    implementation(Libs.android.recyclerView)
    implementation(Libs.android.constraintLayout)

    implementation(Libs.navigation.fragment)
    implementation(Libs.navigation.ui)

    implementation(Libs.google.playServiceOssLicenses)

    // test
    testImplementation(Libs.test.junit)
    testImplementation(Libs.test.mockK)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
}
