plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

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

    implementation (project(":core"))
    implementation (project(":common"))

    //Kotlin
    implementation ("androidx.core:core-ktx:1.12.0")

    //AndroidX
    implementation ("androidx.appcompat:appcompat:1.6.1")

    //Design
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.github.vacxe:phonemask:1.0.5")
    implementation ("com.redmadrobot:input-mask-android:7.2.4")

    //Navigation
    implementation (Navigation.fragment_ktx)
    implementation (Navigation.ui_ktx)

    //Koin
    implementation ("io.insert-koin:koin-android:3.4.2")

}