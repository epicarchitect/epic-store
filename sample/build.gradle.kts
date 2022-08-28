plugins {
    id("app-convention")
    id("compose-convention")
}

android {
    namespace = "epicarchitect.epicstore.sample"

    defaultConfig {
        applicationId = "epicarchitect.epicstore.sample"
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file("signing/release.jks")
            storePassword = "epicdebug"
            keyAlias = "epicdebug"
            keyPassword = "epicdebug"
        }

        getByName("debug") {
            storeFile = file("signing/debug.jks")
            storePassword = "epicdebug"
            keyAlias = "epicdebug"
            keyPassword = "epicdebug"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }

        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
//    implementation(project(":epic-store-navigation-compose"))
//    implementation("com.github.jitpack.gradle-modular:server:1.3")
    implementation("com.github.epicarchitect.epic-store:core:1.0.2")
    implementation("com.github.epicarchitect.epic-store:compose:1.0.2")
    implementation("com.github.epicarchitect.epic-store:google-navigation-compose:1.0.2")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.material:material:1.2.1")
}