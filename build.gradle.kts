plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.sportshop"
    compileSdk = 35

    dataBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.example.sportshop"
        minSdk = 26
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:21.1.0")
    implementation("com.google.firebase:firebase-storage:20.1.0")
    implementation(platform("com.google.firebase:firebase-bom:31.2.3"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.6.1")
    implementation("androidx.annotation:annotation:1.3.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("info.hoang8f:fbutton:1.0.5")
    implementation("com.rengwuxian.materialedittext:library:2.1.4")

    implementation("com.jaredrummler:material-spinner:1.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.firebaseui:firebase-ui-database:1.2.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")

    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.material:material:1.4.0-rc01")
    implementation("com.google.code.gson:gson:2.8.7")

    implementation("me.relex:circleindicator:2.1.6")

    implementation("com.google.android.material:material:1.3.0-alpha03")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.gms:play-services-auth:19.2.0")

    implementation("com.github.rey5137:material:1.3.1")
    implementation("io.github.pilgr:paperdb:2.7.2")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("androidx.appcompat:appcompat:1.4.0")
}