package com.azmetov.playlistmaker.core.domain.sharing

import com.azmetov.playlistmaker.core.domain.sharing.model.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(link: String)
    fun openEmail(emailData: EmailData)
}