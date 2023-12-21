package com.azmetov.playlistmaker.features.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azmetov.playlistmaker.core.ui.fragment.BindingFragment
import com.azmetov.playlistmaker.databinding.FragmentMediaBinding
import com.azmetov.playlistmaker.features.media.ui.pager.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment : BindingFragment<FragmentMediaBinding>() {
    private lateinit var tabMediator: TabLayoutMediator

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMediaBinding {
        return FragmentMediaBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PagerAdapter(fragment = this)
        binding.mediaPager.adapter = adapter

        tabMediator =
            TabLayoutMediator(binding.mediaTablayout, binding.mediaPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Избранные треки"
                    1 -> tab.text = "Плейлисты"
                }
            }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
}