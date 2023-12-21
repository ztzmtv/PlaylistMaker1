package com.azmetov.playlistmaker.features.search.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class ProgressbarVHFactory : ViewHolderFactory<ProgressbarVH> {
    override fun create(parent: ViewGroup): ProgressbarVH {
        return ProgressbarVH(
            LayoutInflater.from(parent.context).inflate(R.layout.progressbar_item, parent, false)
        )
    }
}

class ProgressbarVH(itemView: View) : RecyclerView.ViewHolder(itemView)

class ProgressbarBinder : BaseVHBinder<ProgressbarVH, Any> {
    override fun onBindViewHolder(holder: ProgressbarVH, data: Any, position: Int) {
        /*NO-OP*/
    }
}