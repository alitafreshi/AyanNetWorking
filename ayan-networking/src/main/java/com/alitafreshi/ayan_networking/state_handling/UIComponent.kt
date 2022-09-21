package com.alitafreshi.ayan_networking.state_handling

sealed class UIComponent {


    data class Loading(val loadingTitle: String = "لطفا صبر کنید...") : UIComponent()

    /**
     * one of the benefit of defining ui app / base ui components is that you can trail / track specific data that you need
     * here for retrying different failed requests we need to  trail / track related event that cause the app to show [ErrorBottomSheet]
     * so we use click on [errorButton] / try button the event relaunch so the request send again to the server
     * @param errorTitle
     * @param errorButton
     * @param errorDescription*/
    data class Error(
        var errorTitle: String = "خطا",
        var errorDescription: UiText,
        var errorButton: String = "تلاش مجدد",
    ) : UIComponent()


    data class Info(
        var infoTitle: String = "توجه",
        var infoDescription: UiText,
        var infoButton: String = "باشه"
    ) : UIComponent()

    data class None(
        var message: UiText,
    ) : UIComponent()
}