plugins {
    alias(libs.plugins.android.library)
}

// 지시서 4.1: domain은 Android UI, Room, Retrofit에 직접 의존하지 않는다.
android {
    namespace = "com.booka.domain"
    compileSdk = 37
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.core)
    // JSR-330 @Inject 어노테이션만 사용. Android/Hilt 자체에는 의존하지 않는다(지시서 4.2 domain 격리 규칙).
    implementation("javax.inject:javax.inject:1")
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
