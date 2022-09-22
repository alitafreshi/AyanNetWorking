package com.alitafreshi.ayan_networking.interactors.local_message_manager

import com.alitafreshi.ayan_networking.constants.Constants.ENGLISH_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.constants.Constants.PERSIAN_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.constants.exceptions.TimeOutException
import com.alitafreshi.ayan_networking.state_handling.UiText

class LocalMessageHandlerUseCase {

    companion object {
        const val NO_CODE_SERVER_ERROR_CODE = "NOCODEFROMSERVER"
        const val APP_INTERNAL_ERROR_CODE = "INTERNAL"

        const val NO_INTERNET_CONNECTION_FA =
            "ارتباط با سرور برقرار نشد. لطفا اتصال دستگاه خود به اینترنت را بررسی نمایید."
        const val TIMEOUT_FA = "پاسخی از سرور دریافت نشد. لطفا دوباره تلاش نمایید."
        const val CANCELED_FA = "ارتباط با سرور توسط کاربر لغو شد."
        const val LOGIN_REQUIRED_FA = "خطای احراز هویت، لطفا دوباره وارد شوید."
        const val NOT_200_FA = "خطای داخلی، پاسخ دریافت شده نامعتبر است."
        const val UNKNOWN_FA = "خطای داخلی، لطفا در صورت تکرار با پشتیبانی تماس بگیرید."

        const val NO_INTERNET_CONNECTION_EN =
            "No internet connection. Please check your internet connectivity."
        const val TIMEOUT_EN = "Connection timeout. Please try again."
        const val CANCELED_EN = "Operation canceled by user."
        const val LOGIN_REQUIRED_EN = "Session expired. Please login again."
        const val NOT_200_EN = "Internal error."
        const val UNKNOWN_EN = "Unknown error. Please contact support."

        const val NO_INTERNET_CONNECTION_AR =
            "لا يوجد اتصال بالإنترنت. يرجى التحقق من اتصالك بالإنترنت."
        const val TIMEOUT_AR = "انتهى وقت محاولة الاتصال. حاول مرة اخرى."
        const val CANCELED_AR = "ألغى المستخدم العملية."
        const val LOGIN_REQUIRED_AR = "انتهت الجلسة. الرجاد الدخول على الحساب من جديد."
        const val NOT_200_AR = "خطأ داخلي."
        const val UNKNOWN_AR = "خطأ غير معروف. يرجى الاتصال بالدعم."
    }

    //TODO We Should Define That How We Provide Local Messages
    operator fun invoke(appLanguage: String, throwable: Throwable): UiText =
        throwable.message?.let { errorMessage ->
            UiText.DynamicString(value = errorMessage)
        } ?: when (appLanguage) {
            PERSIAN_APP_LANGUAGE_KEY -> {
                persianLocalMessage(throwable = throwable)
            }

            ENGLISH_APP_LANGUAGE_KEY -> {
                englishLocalMessage(throwable = throwable)
            }
            else -> {
                UiText.DynamicString(value = TIMEOUT_FA)
            }
        }


    //TODO This Part Should Be Completed
    private fun persianLocalMessage(throwable: Throwable): UiText = when (throwable) {
        is TimeOutException -> {
            UiText.DynamicString(value = TIMEOUT_FA)
        }
        else -> {
            UiText.DynamicString(value = UNKNOWN_FA)
        }
    }

    //TODO This Part Should Be Completed
    private fun englishLocalMessage(throwable: Throwable): UiText = when (throwable) {
        is TimeOutException -> {
            UiText.DynamicString(value = TIMEOUT_EN)
        }
        else -> {
            UiText.DynamicString(value = UNKNOWN_EN)
        }
    }
}
