plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.sportshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sportshop"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {


    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-database")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation("com.squareup.picasso:picasso:2.71828")


    implementation(libs.appcompat)

    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
//    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.squareup.picasso:picasso:2.71828")
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.lifecycle:livedata-ktx")
    implementation("androidx.lifecycle:viewmodel-ktx")
    implementation("androidx.navigation:fragment")
    implementation("androidx.navigation:ui")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-core")
    implementation("info.hoang8f:fbutton")
    implementation("com.rengwuxian.materialedittext:library")
    implementation("com.jaredrummler:material-spinner")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.firebaseui:firebase-ui-database")
    implementation("com.github.bumptech.glide:glide")
    annotationProcessor("com.github.bumptech.glide:compiler")
    implementation("com.google.code.gson:gson")
    implementation("me.relex:circleindicator")
    implementation("de.hdodenhof:circleimageview")
    implementation("com.google.android.gms:play-services-auth")
    implementation("com.github.rey5137:material")
    implementation("io.github.pilgr:paperdb")
}
