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
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))
    implementation(project(Libs.showcase.dataSource.rank))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)

    // test
//    testImplementation(Libs.test.junit)
//    testImplementation(Libs.test.mockK)
//    testImplementation(Libs.kotlin.coroutineCore)
}
