plugins {
    id(Plugins.javaLibrary)
    kotlin(KotlinPlugins.jvm)
    kotlin(KotlinPlugins.serialization) version "1.7.10"
    id(KotlinPlugins.maven)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    //Ktor
    implementation(Ktor.ktor_client_core)
    implementation(Ktor.ktor_client_okhttp)
    implementation(Ktor.ktor_client_logging)
    implementation(Ktor.ktor_client_content_negotiation)
    implementation(Ktor.ktor_ktor_serialization_kotlinx_json)
    //Data store
    implementation(DataStore.data_store)
}