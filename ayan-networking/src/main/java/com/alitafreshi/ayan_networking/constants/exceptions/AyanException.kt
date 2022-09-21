package com.alitafreshi.ayan_networking.constants.exceptions

import kotlinx.coroutines.CancellationException

class LoginRequiredException(message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)

class ServerErrorException(errorCode: String, message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)


class UserCancellationException(message: String) : CancellationException(message = message)

class DataStoreUnknownException(message: String) : Exception(message = message)

class SuccessCompletionException(message: String) : Exception(message = message)

