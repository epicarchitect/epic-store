plugins {
    id("library-publishing-convention")
    id("compose-convention")
}

android {
    namespace = "epicarchitect.epicstore.compose"
}

dependencies {
    api(project(":epic-store"))
    api("androidx.compose.foundation:foundation:1.2.1")
}