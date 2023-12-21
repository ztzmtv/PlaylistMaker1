package com.azmetov.playlistmaker.features.player.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class TitleVHFactory : ViewHolderFactory<TitleVH> {

    override fun create(parent: ViewGroup): TitleVH {
        return TitleVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.player_title_item, parent, false)
        )
    }
}

class TitleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text1: TextView = itemView.findViewById(R.id.title_text1)
    val text2: TextView = itemView.findViewById(R.id.title_text2)
}

class TitleBinder : BaseVHBinder<TitleVH, Pair<String, String>> {

    override fun onBindViewHolder(holder: TitleVH, data: Pair<String, String>, position: Int) {
        holder.text1.text = data.first
        holder.text2.text = data.second
    }

}