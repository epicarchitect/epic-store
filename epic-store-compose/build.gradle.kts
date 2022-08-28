plugins {
    id("library-convention")
    id("compose-convention")
    id("maven-publish")
}

android {
    namespace = "epicarchitect.epicstore.compose"
}

dependencies {
    api(project(":epic-store"))
    api("androidx.compose.foundation:foundation:1.2.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.epicarchitect"
            artifactId = "epic-store-compose"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}