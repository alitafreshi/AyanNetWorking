package com.alitafreshi.ayan_networking

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

//TODO We Should Provide HttpClient Instance Directly In Side Hilt module
class KtorClient {

    operator fun invoke(): HttpClient = HttpClient(OkHttp) {

        //Install Logging Plugin
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }

        //TODO BeCareFull . In a case of a timeout, Ktor throws HttpRequestTimeoutException, ConnectTimeoutException, or SocketTimeoutException.
        install(HttpTimeout) {
            requestTimeoutMillis = 3000L
        }

        //For Handling json Serialization And DeSerialization in Ktor
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        engine {
            config {
                followRedirects(true)
                followSslRedirects(true)
                retryOnConnectionFailure(false)
            }
        }
    }
}