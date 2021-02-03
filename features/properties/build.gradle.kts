plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
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
    }
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))
    implementation(project(Libs.showcase.dataSource.properties))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)

    implementation(Libs.kotlin.coroutineCore)
    implementation(Libs.kotlin.coroutineAndroid)
    implementation(Libs.android.recyclerView)
    implementation(Libs.android.constraintLayout)

    // retrofit
    implementation(Libs.retrofit.retrofit)
    implementation(Libs.retrofit.gson)

    implementation(Libs.navigation.fragment)
    implementation(Libs.navigation.ui)

    // test
    testImplementation(Libs.test.junit)
    testImplementation(Libs.test.mockK)
    testImplementation(Libs.test.mockWebServer)
}
