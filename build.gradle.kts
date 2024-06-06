// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
}
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("com.google.gms:google-services:4.4.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}


// Không cần repositories và dependencies tại đây vì chúng đã được cấu hình trong settings.gradle.kts
