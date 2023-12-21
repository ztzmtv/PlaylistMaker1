package com.azmetov.playlistmaker.features.settings.data

import com.azmetov.playlistmaker.core.data.frameworks.storage.Storage
import com.azmetov.playlistmaker.core.domain.models.ThemeSettings
import com.azmetov.playlistmaker.features.settings.domain.SettingsRepository

class SettingsRepositoryImpl(
    private val storage: Storage
) : SettingsRepository {

    override fun getSettings(): ThemeSettings {
        val theme = storage.getTheme()
        return ThemeSettings(theme)
    }

    override fun updateSettings(settings: ThemeSettings) {
        val theme = settings.theme
        storage.setTheme(theme)
    }
}