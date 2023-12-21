package com.azmetov.playlistmaker.core.domain.sharing

import com.azmetov.playlistmaker.core.domain.sharing.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return "https://practicum.yandex.ru/android-developer/"
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(
            email = "praktikum@support.yandex.ru",
            subject = "Сообщение разработчикам и разработчицам приложения Playlist Make",
            text = "Спасибо разработчикам и разработчицам за крутое приложение!"
        )
    }

    private fun getTermsLink(): String {
        return "https://yandex.ru/legal/practicum_offer/"
    }
}