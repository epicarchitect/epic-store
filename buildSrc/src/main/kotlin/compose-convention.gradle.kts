import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Constants.COMPOSE_COMPILER_VERSION
}