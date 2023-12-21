package com.azmetov.playlistmaker.features.settings.domain

import com.azmetov.playlistmaker.core.domain.models.ThemeSettings

class SettingsInteractorImpl(
    private val repository: SettingsRepository
) : SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return repository.getSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        repository.updateSettings(settings)
    }

}