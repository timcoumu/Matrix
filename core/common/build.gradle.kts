plugins {
    alias(libs.plugins.android.library)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.timcoumu.core.common"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation("androidx.datastore:datastore-preferences:1.2.1")
    implementation("androidx.datastore:datastore:1.2.1")
    implementation("com.google.dagger:hilt-android:2.60.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}