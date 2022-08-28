plugins {
    id("library-convention")
    id("maven-publish")
}

android {
    namespace = "epicarchitect.epicstore"
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.epicarchitect"
            artifactId = "epic-store"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}