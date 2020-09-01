object Apps {

    // Build Config
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29

    // App Version
    const val versionCode = 1
    const val versionName = "0.1"
}

object Versions {

    // Gradle
    const val gradle = "4.0.0"

    // Kotlin
    const val ktLint = "9.2.1"
    const val kotlin = "1.3.72"
    const val coroutine = "1.3.7"
    const val coroutineServices = "1.1.1"
    const val kotlinGradlePlugin = "4.3.3"

    // Android
    const val appCompact = "1.1.0"
    const val core = "1.2.0"

    // Layout
    const val recyclerview = "1.1.0"
    const val materialDesign = "1.1.0"
    const val constraintLayout = "1.1.3"

    // Google
    const val firebaseAuth = "19.3.0"
    const val firebaseAnalytics = "17.3.0"
    const val gson = "2.8.6"

    // Retrofit
    const val retrofit = "2.6.4"

    // Tests
    const val junit = "4.12"
    const val mockk = "1.9.3"
    const val junitAndroid = "1.1.1"
    const val espressoAndroid = "3.2.0"
}

object Libs {
    val showcase = Showcase
    val retrofit = RetrofitLibs
    val android = AndroidLibs
    val kotlin = KotlinLibs
    val google = GoogleLibs
    val thirdParty = ThirdPartyLibs
    val test = TestLibs
}

object Showcase {
    const val common = ":common"
    val dataSource = ShowcaseDataSource
}

object ShowcaseDataSource {
    const val root = ":data_source"
    const val bad = ":data_source:bad_char_data_source"
    const val rank = ":data_source:rank_data_source"
}

object Showcase {
    const val common = ":common"
}

object RetrofitLibs {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object AndroidLibs {
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompact}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object KotlinLibs {
    const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val coroutinesPlayService =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutineServices}"
}

object GoogleLibs {
    val firebase = FirebaseLibs
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val googleServices =
        "com.google.gms:google-services:${Versions.kotlinGradlePlugin}"
    const val materialDesign =
        "com.google.android.material:material:${Versions.materialDesign}"
}

object FirebaseLibs {
    const val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val analytics =
        "com.google.firebase:firebase-analytics:${Versions.firebaseAnalytics}"
}

object ThirdPartyLibs {
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktLint}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockK = "io.mockk:mockk:${Versions.mockk}"
    const val junitAndroid = "androidx.test.ext:junit:${Versions.junitAndroid}"
    const val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espressoAndroid}"
}