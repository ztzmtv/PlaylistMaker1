package com.azmetov.playlistmaker.features.player.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class AttributeVHFactory : ViewHolderFactory<AttributeVH> {

    override fun create(parent: ViewGroup): AttributeVH {
        return AttributeVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.player_attribute_item, parent, false)
        )
    }
}

class AttributeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.nameView)
    val valueView: TextView = itemView.findViewById(R.id.valueView)
}

class AttributeBinder : BaseVHBinder<AttributeVH, Pair<String, String>> {

    override fun onBindViewHolder(holder: AttributeVH, data: Pair<String, String>, position: Int) {
        holder.nameView.text = data.first
        holder.valueView.text = data.second
    }
}