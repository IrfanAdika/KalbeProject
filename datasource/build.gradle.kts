plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinAnnotationProcessor)
    id(BuildPlugins.realmAndroid)
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("env")
    productFlavors {
        create(Flavors.SIT) {
            missingDimensionStrategy("env", Flavors.SIT)
        }

        create(Flavors.STAGING) {
            missingDimensionStrategy("env", Flavors.STAGING)
        }

        create(Flavors.PROD) {
            missingDimensionStrategy("env", Flavors.PROD)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(Dependencies.jarLibs))
    implementation(project(Modules.core))

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitCoroutinesAdapter)
    implementation(Dependencies.loggingInterceptor) {
        exclude(group = "org.json", module = "json")
    }
}