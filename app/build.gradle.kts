plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.angel_inc.todoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.angel_inc.todoapp"
        minSdk = 27
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val navVersion = "2.8.0"

    //viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    //liveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //DagerHilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //retofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0") //para ver la respyesta

    implementation ("com.google.android.material:material:1.9.0")  // o una versión más reciente
    //DATSA ESTORE
    implementation ("androidx.datastore:datastore:1.0.0")




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}