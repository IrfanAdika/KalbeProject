plugins {
    id("com.android.application")
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinAnnotationProcessor)
    id(BuildPlugins.androidxNavSafeArgs)
    id(BuildPlugins.realmAndroid)
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {
        applicationId = ApplicationId.id
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Releases.versionCode
        versionName = Releases.versionName
        multiDexEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        create("sit") {
            missingDimensionStrategy("env", "sit")
            applicationIdSuffix = ".sit"

            resValue("string", "app_name", "Kalbe Proj SIT")
        }

        create("staging") {
            missingDimensionStrategy("env", "staging")
            applicationIdSuffix = ".staging"

            resValue("string", "app_name", "Kalbe Proj STAGING")
        }

        create("prod") {
            missingDimensionStrategy("env", "prod")

            resValue("string", "app_name", "Kalbe Proj")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.datasource))
}