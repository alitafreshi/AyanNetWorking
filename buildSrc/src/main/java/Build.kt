object Build {
    private const val gradleBuildTools = "7.2.2"
    const val buildTools = "com.android.tools.build:gradle:${gradleBuildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kotlinxSerializationGradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    const val kaptGradlePlugin = "org.jetbrains.kotlin.kapt:${Kotlin.version}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.version}"
    private const val jitpackGradleBuildVersion = "0.16.4"

    const val jitpackKotlinGradlePlugin =
        "com.github.gundy:semver4j:${jitpackGradleBuildVersion}"


}
