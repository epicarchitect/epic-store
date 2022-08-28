# Epic Store
Alternative to ViewModelStore

### Add the JitPack repository to your root build file

```Kotlin
allprojects {
    repositories {
        maven("https://jitpack.io")
    }
}
```

### Add the dependency

```Kotlin
dependencies {
    implementation("com.github.epicarchitect.epic-store:core:1.0.3")
    implementation("com.github.epicarchitect.epic-store:compose:1.0.3") // contains core api
    implementation("com.github.epicarchitect.epic-store:google-navigation-compose:1.0.3") // contains core, compose and google navigation api
}
```
