plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "vn.nguyenquocbao.apponlineexams_63133497"
    compileSdk = 35

    defaultConfig {
        applicationId = "vn.nguyenquocbao.apponlineexams_63133497"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Các dependencies mặc định của dự án
    implementation(platform("androidx.compose:compose-bom:2023.08.00")) // Nếu bạn dùng Compose, nếu không thì bỏ qua
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // <<< SỬ DỤNG PHIÊN BẢN MỚI NHẤT


    implementation("com.google.firebase:firebase-auth")
    implementation(libs.firebase.database)
    implementation(libs.activity)

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}