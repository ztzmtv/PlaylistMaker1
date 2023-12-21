package com.azmetov.playlistmaker.features.media.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azmetov.playlistmaker.features.media.ui.fragments.FavoritesFragment
import com.azmetov.playlistmaker.features.media.ui.fragments.PlaylistsFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoritesFragment.newInstance()
            else -> PlaylistsFragment.newInstance()
        }
    }
}