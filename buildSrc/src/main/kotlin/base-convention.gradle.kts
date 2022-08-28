import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    compileSdkVersion(Constants.COMPILE_SDK)

    defaultConfig {
        minSdk = Constants.MIN_SDK
        targetSdk = Constants.COMPILE_SDK
    }
}