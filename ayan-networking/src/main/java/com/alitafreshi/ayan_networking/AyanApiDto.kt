package com.alitafreshi.ayan_networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Identity(
    @SerialName("Token")
    var token: String?
)

@Serializable
data class Status(
    @SerialName("Code")
    var code: String,

    @SerialName("Description")
    var description: String
)

@Serializable
data class AyanRequest<T>(
    @SerialName("Identity")
    var identity: Identity?,

    @SerialName("Parameters")
    var parameters: T?
)

@Serializable
data class AyanResponse<T>(
    @SerialName("Parameters")
    var parameters: T?,

    @SerialName("Status")
    var status: Status
)
