// build.gradle.kts (Đây là file cấp Project - nằm ở thư mục gốc của project)

buildscript {
    repositories {
        google() // Dành cho Google's Maven repository
        mavenCentral() // Dành cho Maven Central repository
    }
    dependencies {
        // Đảm bảo phiên bản AGP khớp với phiên bản Android Studio và project của bạn
        classpath("com.android.tools.build:gradle:8.0.0") // Giữ phiên bản bạn đã dùng
        classpath("com.google.gms:google-services:4.4.1") // Giữ phiên bản bạn đã dùng
    }
}

plugins {

}
tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}