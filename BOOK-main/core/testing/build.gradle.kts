plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.booka.core.testing"
    compileSdk = 37
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // core:testing은 fake repository·sample data를 제공하며 domain 인터페이스를 구현한다(지시서 4.1).
    api(project(":domain"))
    api(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.core)
    implementation("javax.inject:javax.inject:1")
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
}
