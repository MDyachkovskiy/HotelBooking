plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application.hotelbooking"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.application.hotelbooking"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    implementation (project(":remote_data"))
    implementation (project(":home"))
    implementation (project(":core"))
    implementation (project(":room_list"))
    implementation (project(":payment_success"))
    implementation (project(":booking"))

    //Kotlin
    implementation (Kotlin.core)

    //AndroidX
    implementation (AndroidX.appcompat)

    //Design
    implementation (Design.material)
    implementation (Design.constraint_layout)

    //Koin
    implementation (Koin.android)
    implementation (Koin.core)
    implementation (Koin.navigation)
    implementation (Koin.androidx)

    //Retrofit
    implementation (Retrofit.main)
    implementation (Retrofit.gson_convertor)

    //Navigation
    implementation (Navigation.fragment_ktx)
    implementation (Navigation.ui_ktx)
}