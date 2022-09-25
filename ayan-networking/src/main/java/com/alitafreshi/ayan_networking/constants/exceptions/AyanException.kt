package com.alitafreshi.ayan_networking.constants.exceptions

import kotlinx.coroutines.CancellationException


//TODO We Should Have Some Thing Like This To Be Able To Pass The Error Codes UpStream
sealed class AyanException(message: String) : Throwable(message = message) {
    class LoginRequiredException(message: String, causeCoroutineName: String? = null) :
        AyanException(message = message)
}


class LoginRequiredException(message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)

class ServerErrorException(errorCode: String, message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)

class UserCancellationException(message: String) : CancellationException(message = message)

class DataStoreUnknownException(message: String) : Exception(message = message)

class SuccessCompletionException(message: String) : Exception(message = message)

class TimeOutException(message: String) : Exception(message = message)

