package com.azmetov.playlistmaker.features.player.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ImageVHFactory : ViewHolderFactory<ImageVH> {

    override fun create(parent: ViewGroup): ImageVH {
        return ImageVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.player_image_item, parent, false)
        )
    }
}

class ImageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView = itemView as ImageView
}

class ImageBinder : BaseVHBinder<ImageVH, String?> {

    override fun onBindViewHolder(holder: ImageVH, data: String?, position: Int) {
        Glide.with(holder.itemView.context)
            .load(data)
            .centerCrop()
            .transform(RoundedCorners(8))
            .placeholder(R.drawable.album_placeholder_with_padding)
            .into(holder.imageView)
    }

}