plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application.home"
    compileSdk = Config.compile_sdk

    defaultConfig {
        minSdk = Config.min_sdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //Modules
    implementation (project(Modules.core))
    implementation (project(":app"))

    //Kotlin
    implementation (Kotlin.core_ktx)

    //AndroidX
    implementation (AndroidX.appcompat)

    //Design
    implementation (Design.material)
}