plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)

}

android {
    namespace = "com.example.testeffectivemobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testeffectivemobile"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

}


dependencies {
    implementation(project(":shared:user:auth"))
    implementation(project(":shared:courses"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:courses"))
    implementation(project(":feature:favorites"))
    implementation(project(":util:validation"))
    implementation(project(":common:resources"))

    //MockWebServer
    implementation(libs.okhttp)
    implementation(libs.mockwebserver.v4110)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.mockwebserver)

// Работа с сетью
    implementation(libs.retrofit);
    implementation(libs.retrofit2.kotlinx.serialization.converter);
    implementation(libs.logging.interceptor)

    // Сериализация JSON
    implementation(libs.kotlinx.serialization.json);

    // Асинхронное программирование
    implementation(libs.kotlinx.coroutines.android)

    //DI
    implementation(libs.koin.android)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}