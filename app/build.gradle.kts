plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.android.gms.oss-licenses-plugin") // todo canato https://developers.google.com/android/guides/opensource
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

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libs.showcase.common))

    implementation(project(Libs.showcase.features.home))
    implementation(project(Libs.showcase.features.rank))

    implementation(project(Libs.showcase.dataSource.bad))
    implementation(project(Libs.showcase.dataSource.rank))

    implementation(project(Libs.showcase.common))

    implementation(Libs.kotlin.stdLib)

    implementation(Libs.android.coreKtx)
    implementation(Libs.android.appCompat)
    implementation(Libs.google.materialDesign)

    implementation(Libs.google.playServiceOssLicenses)
    // Navigation
    implementation(Libs.navigation.fragment)
    implementation(Libs.navigation.ui)
}
