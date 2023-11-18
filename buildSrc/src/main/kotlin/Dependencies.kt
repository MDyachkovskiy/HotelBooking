import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.test.application.hotelbooking"
    const val compile_sdk = 34
    const val min_sdk = 26
    const val target_sdk = 34
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Versions {

    //Kotlin
    const val core_ktx = "1.9.0"

    //AndroidX
    const val appcompat = "1.6.1"

    //Design
    const val material = "1.10.0"
    const val constraint_layout = "2.1.4"

    //Test
    const val jUnit = "4.13.2"
    const val ext_junit = "1.1.5"
    const val espressoCore = "3.5.1"

}

object Modules  {
    const val app = ":app"
    const val core = ":core"
    const val home = ":home"
}

object Kotlin{
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
}

object Design {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}