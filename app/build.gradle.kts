plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.timcoumu.matrix"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.timcoumu.matrix"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.2.10"
    }
    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://100.81.90.18:8448/\"")
            buildConfigField("Boolean", "IS_DEBUG", "true")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"http://100.81.90.18:8448/\"")
            buildConfigField("Boolean", "IS_DEBUG", "true")
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:matrix"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:main"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:room"))
    implementation(project(":feature:user"))
    implementation("com.google.dagger:hilt-android:2.60.1")
    implementation("androidx.lifecycle:lifecycle-process:2.8.7")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.8.7")
    implementation(libs.androidx.work.runtime.ktx)
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation("androidx.compose.material:material-icons-core:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}