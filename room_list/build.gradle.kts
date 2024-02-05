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
    implementation(Kotlin.core)

    //AndroidX
    implementation(AndroidX.appcompat)

    //Koin
    implementation (Koin.android)

    //Coil
    implementation (Coil.coil_kt)

    //Design
    implementation(Design.material)
    implementation (Design.view_pager2)
    implementation (Design.circle_indicator)
    
    //Navigation
    implementation (Navigation.fragment_ktx)
    implementation (Navigation.ui_ktx)


}