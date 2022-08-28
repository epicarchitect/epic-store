plugins {
    id("library-convention")
    id("compose-convention")
    id("maven-publish")
}

android {
    namespace = "epicarchitect.epicstore.navigation.compose"
}

dependencies {
    api(project(":epic-store-compose"))
    api("androidx.navigation:navigation-compose:2.5.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.epicarchitect"
            artifactId = "epic-store-navigation-compose"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}