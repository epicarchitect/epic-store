import org.gradle.jvm.tasks.Jar
import java.util.Properties

plugins {
    id("library-convention")
    id("maven-publish")
    id("signing")
}

fun extString(name: String) = ext[name]!!.toString()

project.rootProject.file("local.properties").reader().use {
    Properties().apply { load(it) }
}.onEach { (name, value) ->
    ext[name.toString()] = value
}

android {
    publishing {
        singleVariant("release")
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = extString("ossrhUsername")
                password = extString("ossrhPassword")
            }
        }
    }

    publications.create<MavenPublication>("release") {
        groupId = Constants.EPICARCHITECT_GROUP_NAME
        artifactId = "epicstore-${project.name}"
        version = Constants.EPIC_STORE_VERSION

        artifact(javadocJar.get())

        pom {
            name.set("Epic Store")
            description.set("Android library for storing objects")
            url.set("https://github.com/epicarchitect/epic-store")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("epicarchitect")
                    name.set("Alexander Kolmachikhin")
                    email.set("alexanderkolmachikhin@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/epicarchitect/epic-store")
            }
        }

        afterEvaluate {
            from(components["release"])
        }
    }
}

signing {
    sign(publishing.publications)
}