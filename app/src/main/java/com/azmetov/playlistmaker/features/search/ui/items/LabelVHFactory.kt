package com.azmetov.playlistmaker.features.search.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class LabelVHFactory : ViewHolderFactory<LabelVH> {
    override fun create(parent: ViewGroup): LabelVH {
        return LabelVH(
            LayoutInflater.from(parent.context).inflate(R.layout.label_item, parent, false)
        )
    }
}

class LabelVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView as TextView
}

class LabelBinder : BaseVHBinder<LabelVH, String?> {
    override fun onBindViewHolder(holder: LabelVH, data: String?, position: Int) {
        holder.textView.text = data
    }
}