plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application.home"
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

    packaging {
        resources.excludes.add("META-INF/NOTICE.md")
        resources.excludes.add("META-INF/LICENSE.md")
    }
}

dependencies {
    //Modules
    implementation (project(":core"))
    implementation (project(":common"))

    //Kotlin
    implementation (Kotlin.core)

    //AndroidX
    implementation (AndroidX.appcompat)

    //Design
    implementation (Design.material)
    implementation (Design.constraint_layout)
    implementation (Design.view_pager2)
    implementation (Design.circle_indicator)

    //Koin
    implementation (Koin.android)

    //Coil
    implementation (Coil.coil_kt)
}