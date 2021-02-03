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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))
    implementation(project(Libs.showcase.dataSource.rank))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)

    implementation(Libs.kotlin.coroutineCore)
    implementation(Libs.kotlin.coroutineAndroid)
    implementation(Libs.android.recyclerView)
    implementation(Libs.android.constraintLayout)

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(Libs.navigation.fragment)
    implementation(Libs.navigation.ui)

    // test
    testImplementation(Libs.test.junit)
    testImplementation(Libs.test.mockK)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
}
