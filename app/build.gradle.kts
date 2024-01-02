plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.googleassistantcloning"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.googleassistantcloning"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
    }

    @Suppress("DEPRECATION")
    dexOptions {
        preDexLibraries = false
        javaMaxHeapSize =  "4g"
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
    //noinspection LifecycleAnnotationProcessorWithJava8
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("androidx.lifecycle:lifecycle-service:2.6.2")
    implementation("androidx.lifecycle:lifecycle-process:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.room:room-runtime:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
    implementation("androidx.room:room-rxjava2:2.6.1")
    implementation("androidx.room:room-rxjava3:2.6.1")
    implementation("androidx.room:room-guava:2.6.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-vision:20.1.3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation("com.android.support:multidex:1.0.3")
    implementation("the.bot.box:horoscope-api:1.0.2")
    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.github.shubham0204:Text2Summary-Android:alpha-05")
    implementation("com.github.KwabenBerko:OpenWeatherMap-Android-Library:2.1.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.room:room-runtime:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}