plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application.room_list"
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

    //Modules
    implementation (project(":core"))

    //Kotlin
    implementation("androidx.core:core-ktx:1.12.0")

    //AndroidX
    implementation("androidx.appcompat:appcompat:1.6.1")

    //Koin
    implementation ("io.insert-koin:koin-android:3.4.2")

    //Coil
    implementation ("io.coil-kt:coil:2.4.0")

    //Design
    implementation("com.google.android.material:material:1.10.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("me.relex:circleindicator:2.1.6")

}