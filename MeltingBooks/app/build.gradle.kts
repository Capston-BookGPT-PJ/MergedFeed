import java.util.Properties

// ✅ local.properties에서 API 키를 가져오는 함수
fun getApiKeyFromLocalProperties(): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    return properties.getProperty("OPENAI_API_KEY") ?: ""
}

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id("com.google.protobuf") version "0.9.1"
}

android {
    namespace = "com.example.meltingbooks"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.meltingbooks"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ✅ API 키를 BuildConfig에 추가
        buildConfigField("String", "OPENAI_API_KEY", "\"${getApiKeyFromLocalProperties()}\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/INDEX.LIST",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/DEPENDENCIES",
                "META-INF/versions/9/module-info.class"
            )
        }
    }
}

dependencies {
    // 기본 UI 및 테스트 관련 라이브러리
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ConstraintLayout (명시적 버전)
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // Material 디자인 명시적 버전 (필요 시 제거 가능)
    implementation("com.google.android.material:material:1.8.0")

    // Glide 이미지 로딩
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Emoji2
    implementation("androidx.emoji2:emoji2:1.4.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage:20.2.1")

    // Google 인증
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // 네트워킹 및 STT
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.cloud:google-cloud-speech:4.50.0") {
        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }
    implementation("com.google.protobuf:protobuf-javalite:3.25.5")
}

// Firebase 설정 적용
apply(plugin = "com.google.gms.google-services")
