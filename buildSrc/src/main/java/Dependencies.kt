object Environments {

    // SERVER SERVICE
    const val URL_SIT = "\"http://demo8122690.mockable.io/\""
    const val URL_STAGING = "\"http://demo8122690.mockable.io/\""
    const val URL_PROD = "\"http://demo8122690.mockable.io/\""
}

object Flavors {
    const val SIT = "sit"
    const val STAGING = "staging"
    const val PROD = "prod"
}

object ApplicationId {
    const val id = "com.yummy.kalbeproject"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val datasource = ":datasource"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "7.0.4"
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 31
    const val kotlin = "1.5.20"
    const val appCompat = "1.4.1"
    const val constraintLayout = "2.1.3"
    const val recyclerview = "1.0.0"
    const val materialDesign = "1.5.0"
    const val retrofit = "2.9.0"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val junit = "4.12"
    const val legacySupport = "1.0.0"
    const val androidxJunit = "1.1.3"
    const val espresso = "3.4.0"
    const val realmExt = "2.5.0"
    const val realmVersion = "6.1.0"
    const val koin = "2.2.0-rc-3"
    const val loggingInterceptor = "3.1.0"
    const val navigation = "2.3.0"
    const val stetho = "1.5.1"
    const val archViewModelLiveData = "2.2.0"
    const val ktxFragment = "1.2.5"
    const val ktxLifecycleViewModel = "2.3.0"
    const val coil = "1.0.0-rc3"
    const val gson = "2.8.5"
    const val anko = "0.10.8"
    const val sneaker = "2.0.0"
    const val armadilo = "1.0.0"
    const val gradleCrashlytic = "2.8.0"
    const val appCenter = "4.4.2"
    const val coroutines = "1.3.9"
    const val lottie = "3.4.0"
}

object Dependencies {
    const val buildGradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    //Encrypt shared preference
    const val armadilo = "at.favre.lib:armadillo:${Versions.armadilo}"

    //Custom toast
    const val sneakerToast = "com.irozon.sneaker:sneaker:${Versions.sneaker}"

    //Image cache loader
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coil}"

    //Kotlin Extensions
    const val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    const val ankoCommon = "org.jetbrains.anko:anko-commons:${Versions.anko}"

    //Dependency Injection
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    //Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"

    //Logging Networking
    const val loggingInterceptor = "com.github.ihsanbal:LoggingInterceptor:${Versions.loggingInterceptor}"

    //Json Converter
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    //Local storage
    const val realmExt = "com.github.vicpinm:krealmextensions:${Versions.realmExt}"

    //Navigation (Routing)
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    //Debugging/Logging
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoInterceptor = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    // Jars
    val jarLibs = mapOf("dir" to "libs", "include" to listOf("*.jar"))
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign  = "com.google.android.material:material:${Versions.materialDesign}"
    const val ktxFragment = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"
    const val ktxLifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktxLifecycleViewModel}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunit}"
    const val jUnit = "junit:junit:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    // ViewModel + Lifecycle
    const val arch_ViewModelLiveData =
        "androidx.lifecycle:lifecycle-extensions:${Versions.archViewModelLiveData}"

    // App Center Sdk
    const val appCenterSdk = "com.microsoft.appcenter:appcenter-distribute:${Versions.appCenter}"

    // Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Lottie
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}

object BuildPlugins {
    const val safeArgsNavigation =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val androidLibrary = "com.android.library"
    const val androidxNavSafeArgs = "androidx.navigation.safeargs.kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinAnnotationProcessor = "kotlin-kapt"
    const val gradleCrashlytic = "com.google.firebase:firebase-crashlytics-gradle:${Versions.gradleCrashlytic}"
    const val realmPlugin = "io.realm:realm-gradle-plugin:${Versions.realmVersion}"
    const val realmAndroid = "realm-android"
}