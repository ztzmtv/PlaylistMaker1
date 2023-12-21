package com.azmetov.playlistmaker.features.settings.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azmetov.playlistmaker.core.ui.fragment.BindingFragment
import com.azmetov.playlistmaker.databinding.FragmentSettingsBinding
import com.azmetov.playlistmaker.features.settings.ui.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BindingFragment<FragmentSettingsBinding>() {
    private val viewModel: SettingsViewModel by viewModel()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.themeSwitcher.isChecked = viewModel.isDarkMode

        binding.themeSwitcher.setOnCheckedChangeListener { switcher, isDarkMode ->
            viewModel.setTheme(isDarkMode)
        }

        binding.shareTextView.setOnClickListener {
            viewModel.shareApp()
        }

        binding.supportTextView.setOnClickListener {
            viewModel.openSupport()
        }

        binding.agreementTextView.setOnClickListener {
            viewModel.openTerms()
        }
    }
}