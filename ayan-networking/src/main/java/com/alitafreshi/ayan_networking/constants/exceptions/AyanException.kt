package com.alitafreshi.ayan_networking.constants.exceptions


//TODO We Should Have Some Thing Like This To Be Able To Pass The Error Codes UpStream
sealed class AyanServerException(message: String) :
    Throwable(message = message) {

    data class LoginRequiredException(
        override val message: String,
        val causeCoroutineName: String? = null,
        val errorCode: String
    ) : AyanServerException(message = message)

    data class ServerErrorException(
        override val message: String,
        val errorCode: String,
        val causeCoroutineName: String? = null
    ) : AyanServerException(message = message)

    data class SuccessCompletionException(override val message: String) :
        AyanServerException(message = message)

    data class TimeOutException(override val message: String) :
        AyanServerException(message = message)

}


sealed class AyanLocalException(message: String) : Throwable(message = message) {

     class UserCancellationException(override val message: String) :
        AyanLocalException(message = message)

     class DataStoreUnknownException(override val message: String) :
        AyanLocalException(message = message)
}


/*class LoginRequiredException(message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)

class ServerErrorException(errorCode: String, message: String, causeCoroutineName: String? = null) :
    CancellationException(message = message)

class UserCancellationException(message: String) : CancellationException(message = message)

class DataStoreUnknownException(message: String) : Exception(message = message)

class SuccessCompletionException(message: String) : Exception(message = message)

class TimeOutException(message: String) : Exception(message = message)*/

