object Build {
    private const val gradleBuildTools = "7.2.2"
    const val buildTools = "com.android.tools.build:gradle:${gradleBuildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kotlinxSerializationGradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    const val kaptGradlePlugin = "org.jetbrains.kotlin.kapt:${Kotlin.version}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.version}"
    private const val jitpackGradleBuildVersion = "2.1"

    const val jitpackGradlePlugin =
        "com.github.dcendents:android-maven-gradle-plugin:${jitpackGradleBuildVersion}"


}
