package com.azmetov.playlistmaker.features.search.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class ImageVHFactory : ViewHolderFactory<ImageVH> {
    override fun create(parent: ViewGroup): ImageVH {
        return ImageVH(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }
}

class ImageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.item_iv)
}

class ImageBinder : BaseVHBinder<ImageVH, Int?> {
    override fun onBindViewHolder(holder: ImageVH, data: Int?, position: Int) {
        val context = holder.imageView.context
        data?.let { resInt ->
            val drawable = context.getDrawable(resInt)
            holder.imageView.setImageDrawable(drawable)
        }
    }
}