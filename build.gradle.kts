// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
//    id("com.jfrog.artifactory")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("com.android.tools.build:gradle:8.7.3")
    }
}
