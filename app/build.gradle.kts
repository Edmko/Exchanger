plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "test.privat.exchanger"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-debug.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-debug.pro"
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

    flavorDimensions("version")
    productFlavors {
        create("dev") {
            dimension = "version"
            buildConfigField("String", "PRIVAT_BASE_URL", "\"https://api.privatbank.ua\"")
        }
        create("prod") {
            dimension = "version"
            buildConfigField("String", "PRIVAT_BASE_URL", "\"https://api.privatbank.ua\"")
        }
    }
}

dependencies {

    //KTX
    implementation("androidx.core:core-ktx:1.3.2")

    //AppCompat
    implementation("androidx.appcompat:appcompat:1.2.0")

    //Material Design
    implementation("com.google.android.material:material:1.3.0")

    //Constraint
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.31.2-alpha")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.30")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    kapt("com.google.dagger:hilt-android-compiler:2.31.2-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha03")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //Test
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    //RxJava
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    implementation("com.jakewharton.rxrelay3:rxrelay:3.0.0")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    //Navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.3")

    //BindingHelper
    implementation("com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:1.4.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
}