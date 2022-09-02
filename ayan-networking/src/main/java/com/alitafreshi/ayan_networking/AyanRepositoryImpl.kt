package com.alitafreshi.ayan_networking

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class AyanRepositoryImpl(private val client: HttpClient) : AyanRepository {

    override suspend fun <Input, Output> ayanCall(
        baseUrl: String?,
        endpoint: String,
        input: Input,
        identity: Identity?,
        requestHeaders: HashMap<String, String>?
    ): AyanResponse<Output> = client.post {

            url {
                if (!baseUrl.isNullOrEmpty()) {
                    protocol = URLProtocol.HTTPS
                    host = baseUrl
                }
                path(endpoint)
            }

            if (!requestHeaders.isNullOrEmpty())
                headers {
                    requestHeaders.forEach { (key, value) ->
                        appendIfNameAndValueAbsent(
                            name = key,
                            value = value
                        )
                    }
                }
            setBody(AyanRequest(identity = identity, parameters = input))
        }.body()



    override suspend fun <Input, Output> simpleCall(
        endpoint: String,
        input: Input,
        identity: Identity?,
        requestHeaders: HashMap<String, String>?
    ): AyanResponse<Output> = client.post {
        url {
            path(endpoint)
        }

        if (!requestHeaders.isNullOrEmpty())
            headers {
                requestHeaders.forEach { (key, value) ->
                    appendIfNameAndValueAbsent(
                        name = key,
                        value = value
                    )
                }
            }

        setBody(AyanRequest(identity = identity, parameters = input))
    }.body()


}


