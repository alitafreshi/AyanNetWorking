package com.alitafreshi.ayan_networking.interactors.local_message_manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.alitafreshi.ayan_networking.constants.Constants.ENGLISH_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.constants.Constants.PERSIAN_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.constants.Constants.SELECTED_APP_LANGUAGE_KEY
import com.alitafreshi.ayan_networking.data_store.AppDataStore
import com.alitafreshi.ayan_networking.data_store.readValue

class LocalMessageHandler(
    private val appDataState: AppDataStore,
    private val dataStore: DataStore<Preferences>
) {

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
    suspend operator fun invoke(throwable: Throwable): String {

        return when (appDataState.readValue(
            key = SELECTED_APP_LANGUAGE_KEY,
            defaultValue = PERSIAN_APP_LANGUAGE_KEY,
            dataStore = dataStore
        )) {

            ENGLISH_APP_LANGUAGE_KEY -> { "" }

        }
    }
}