plugins {
    id("publishing-library-convention")
    id("compose-convention")
}

android {
    namespace = "epicarchitect.epicstore.navigation.compose"
}

dependencies {
    api(project(":compose"))
    api("androidx.navigation:navigation-compose:2.5.1")
}