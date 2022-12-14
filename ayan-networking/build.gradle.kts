plugins {
    id(Plugins.javaLibrary)
    kotlin(KotlinPlugins.jvm)
    id(KotlinPlugins.kotlin)
    kotlin(KotlinPlugins.serialization) version "1.7.10"
    id(KotlinPlugins.maven)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    //Ktor
    api(Ktor.ktor_client_core)
    api(Ktor.ktor_client_okhttp)
    api(Ktor.ktor_client_logging)
    api(Ktor.ktor_client_content_negotiation)
    api(Ktor.ktor_ktor_serialization_kotlinx_json)
    //Data store
    api(DataStore.data_store)

    //javax
    implementation(Hilt.javaInject)
}

publishing {
    publications {
        // Creates a Maven publication called "release".
        register("release", MavenPublication::class) {
            // Applies the component for the release build variant.
                // NOTE : Delete this line code if you publish Native Java / Kotlin Library
//            from(components["release"])
           // from(components.getByName("java"))
            from(components["java"])


            // Library Package Name (Example : "com.frogobox.androidfirstlib")
            // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
            groupId = "com.github.alitafreshi"

            // Library Name / Module Name (Example : "androidfirstlib")
            // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
            artifactId = "ayan-networking"

            // Version Library Name (Example : "1.0.0")
            version = "0.0.21"
        }
    }
}