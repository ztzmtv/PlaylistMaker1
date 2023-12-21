package com.azmetov.playlistmaker.features.settings.domain

import com.azmetov.playlistmaker.core.domain.models.ThemeSettings

interface SettingsRepository {
    fun getSettings(): ThemeSettings
    fun updateSettings(settings: ThemeSettings)
}