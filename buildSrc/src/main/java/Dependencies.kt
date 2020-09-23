object Apps {

    // Build Config
    const val compileSdk = 30
    const val minSdk = 24
    const val targetSdk = 30

    // App Version
    const val versionCode = 1
    const val versionName = "0.1"
}

object Versions {

    const val room = "2.2.5"
    const val core = "1.2.0"
}

object Libs {

    val androidx = AndroidX
    val chuckerTeam = ChuckerTeam
    val navigation = Navigation
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
    val features = ShowcaseFeatures
    val dataSource = ShowcaseDataSource
}

object ShowcaseFeatures {

    const val home = ":features:home_list"
    const val rank = ":features:rank_board"
    const val chat = ":features:chat"
}

object ShowcaseDataSource {

    const val bad = ":data_source:bad_char_data_source"
    const val rank = ":data_source:rank_data_source"
    const val msg = ":data_source:message_data_source"
}

object AndroidX {

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}

object ChuckerTeam {

    const val chucker = "com.github.chuckerteam.chucker:library:_"
    const val noOp = "com.github.chuckerteam.chucker:library-no-op:_"
}

object Navigation {

    const val ui = "androidx.navigation:navigation-ui-ktx:_"
    const val fragment = "androidx.navigation:navigation-fragment-ktx:_"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:_"
}

object RetrofitLibs {

    const val retrofit = "com.squareup.retrofit2:retrofit:_"
    const val gson = "com.squareup.retrofit2:converter-gson:_"
}

object AndroidLibs {

    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val gradle = "com.android.tools.build:gradle:_"
    const val appCompat = "androidx.appcompat:appcompat:_"
    const val recyclerView = "androidx.recyclerview:recyclerview:_"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:_"
}

object KotlinLibs {

    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:_"
    const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:_"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:_"
    const val coroutinesPlayService = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:_"
}

object GoogleLibs {

    val firebase = FirebaseLibs
    const val gson = "com.google.code.gson:gson:_"
    const val googleServices = "com.google.gms:google-services:_"
    const val materialDesign = "com.google.android.material:material:_"
    const val ossLicenses = "com.google.android.gms:oss-licenses-plugin:_"
    const val playServiceOssLicenses = "com.google.android.gms:play-services-oss-licenses:_"
}

object FirebaseLibs {

    const val auth = "com.google.firebase:firebase-auth:_"
    const val analytics = "com.google.firebase:firebase-analytics:_"
}

object ThirdPartyLibs {

    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:_"
}

object TestLibs {

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:_"
    const val junit = "junit:junit:_"
    const val mockK = "io.mockk:mockk:_"
    const val junitAndroid = "androidx.test.ext:junit:_"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:_"
    const val espressoCore = "androidx.test.espresso:espresso-core:_"
}