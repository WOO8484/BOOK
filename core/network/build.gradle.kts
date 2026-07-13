plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.booka.core.network"
    compileSdk = 37
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    testImplementation(libs.junit)
}
