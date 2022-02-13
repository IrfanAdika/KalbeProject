plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinAnnotationProcessor)
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {

        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    //Coil
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildTypes {
        getByName("release"){

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        create(Flavors.SIT) {
            dimension = "env"

            buildConfigField("String", "BASE_URL", Environments.URL_SIT)
        }

        create(Flavors.STAGING) {
            dimension = "env"

            buildConfigField("String", "BASE_URL", Environments.URL_STAGING)
        }

        create(Flavors.PROD) {
            dimension = "env"

            buildConfigField("String", "BASE_URL", Environments.URL_PROD)
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(Dependencies.jarLibs))

    // Kotlin
    api(Dependencies.kotlin)

    // Anko
    api(Dependencies.anko)
    api(Dependencies.ankoCommon)

    // Coil
    api(Dependencies.coil)
    api(Dependencies.coilSvg)

    // Sneaker
    api(Dependencies.sneakerToast)

    // Realm Extension
    api(Dependencies.realmExt)

    // Aramdilo
    api(Dependencies.armadilo)

    // Lottie
    api(Dependencies.lottie)

    // AndroidX
    api(Dependencies.appCompat)
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerview)
    api(Dependencies.materialDesign)

    // KTX
    api(Dependencies.ktxFragment)
    api(Dependencies.ktxLifecycleViewModel)

    // Legacy
    api(Dependencies.legacy)

    // Navigation
    api(Dependencies.navigationFragmentKtx)
    api(Dependencies.navigationUiKtx)

    // Architecture Components
    api(Dependencies.arch_ViewModelLiveData)

    // Gson
    api(Dependencies.gson)

    // Stetho
    implementation(Dependencies.stetho)
    implementation(Dependencies.stethoInterceptor)

    // Gson
    api(Dependencies.converterGson)

    // Koin
    api(Dependencies.koinAndroid)
    api(Dependencies.koinCore)
    api(Dependencies.koinViewModel)

    // Testing
    testImplementation(Dependencies.jUnit)

    // Android Testing
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espresso)

    // App Center Sdk
    api(Dependencies.appCenterSdk)
}

kapt {
    generateStubs = true
//    correctErrorTypes = true
}