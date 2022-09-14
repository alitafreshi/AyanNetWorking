package com.alitafreshi.ayan_networking

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.logging.*

interface AyanRepository {

    suspend fun <Input, Output> ayanCall(
        baseUrl: String? = null,
        endpoint: String,
        input: Input,
        identity: Identity? = null,
        requestHeaders: HashMap<String, String>? = null
    ): AyanResponse<Output>

    suspend fun <Input, Output> simpleCall(
        endpoint: String,
        input: Input,
        identity: Identity? = null,
        requestHeaders: HashMap<String, String>? = null
    ): AyanResponse<Output>


    companion object {
        fun createKtorClient(): HttpClient = HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

}