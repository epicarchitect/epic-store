plugins {
    id("library-convention")
    id("maven-publish")
}

android {
    publishing {
        singleVariant("release")
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = Constants.EPICARCHITECT_GROUP_NAME
            artifactId = project.name
            version = Constants.EPIC_STORE_VERSION
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}