package com.azmetov.playlistmaker.core.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ViewHolderFactory<out VH : RecyclerView.ViewHolder> {
    fun create(parent: ViewGroup): VH
}