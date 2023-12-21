package com.azmetov.playlistmaker.core.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.azmetov.playlistmaker.core.domain.sharing.ExternalNavigator
import com.azmetov.playlistmaker.core.domain.sharing.model.EmailData

class ExternalNavigatorImpl(
    private val context: Context,
) : ExternalNavigator {

    override fun shareLink(link: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            if (this.resolveActivity(context.packageManager) != null) {
                context.startActivity(this)
            }
        }
    }

    override fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun openEmail(emailData: EmailData) {
        Intent().apply {
            action = Intent.ACTION_SENDTO
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("mailto:")
            val emailArray = arrayOf(emailData.email)
            putExtra(Intent.EXTRA_EMAIL, emailArray)
            putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
            putExtra(Intent.EXTRA_TEXT, emailData.text)
            if (this.resolveActivity(context.packageManager) != null) {
                context.startActivity(this)
            }
        }
    }
}