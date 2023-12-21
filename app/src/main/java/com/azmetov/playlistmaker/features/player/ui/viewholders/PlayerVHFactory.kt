package com.azmetov.playlistmaker.features.player.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class PlayerVHFactory : ViewHolderFactory<PlayerVH> {

    override fun create(parent: ViewGroup): PlayerVH {
        return PlayerVH(
            LayoutInflater.from(parent.context).inflate(R.layout.player_player_item, parent, false)
        )
    }
}

class PlayerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playlistButton: ImageButton = itemView.findViewById(R.id.player_media_ib)
    val favoriteButton: ImageButton = itemView.findViewById(R.id.player_favorites_ib)
    val playButton: ImageButton = itemView.findViewById(R.id.player_play_ib)
}

class PlayerBinder : BaseVHBinder<PlayerVH, Unit> {

    var holder: PlayerVH? = null
    var favoriteClickListener: (() -> Unit)? = null
    var playClickListener: (() -> Unit)? = null
    var playlistClickListener: (() -> Unit)? = null
    private var isPlaying = false
    private var isFavorite = false
    private var isInPlaylist = false


    override fun onBindViewHolder(holder: PlayerVH, data: Unit, position: Int) {
        if (this.holder == null) this.holder = holder
        holder.favoriteButton.setOnClickListener { favoriteClickListener?.invoke() }
        holder.playlistButton.setOnClickListener { playlistClickListener?.invoke() }
        holder.playButton.setOnClickListener { playClickListener?.invoke() }
        setPlayState(this.isPlaying)
        setFavoritesState(this.isFavorite)
        setAddToPlaylistState(this.isInPlaylist)
    }

    fun setPlayState(isPlaying: Boolean) {
        this.isPlaying = isPlaying
        holder?.let { playerVH ->
            val image = when (this.isPlaying) {
                true -> R.drawable.ic_pause
                false -> R.drawable.ic_play
            }
            val context = playerVH.playButton.context
            val imageDrawable = AppCompatResources.getDrawable(context, image)
            playerVH.playButton.setImageDrawable(imageDrawable)
        }
    }

    fun setFavoritesState(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        holder?.let { playerVH ->
            val image = when (this.isFavorite) {
                true -> R.drawable.ic_like_on
                false -> R.drawable.ic_like_off
            }
            val context = playerVH.playButton.context
            val imageDrawable = AppCompatResources.getDrawable(context, image)
            playerVH.favoriteButton.setImageDrawable(imageDrawable)
        }
    }

    fun setAddToPlaylistState(isInPlaylist: Boolean) {
        this.isInPlaylist = isInPlaylist
        holder?.let { playerVH ->
            val image = when (this.isInPlaylist) {
                true -> R.drawable.ic_in_playlist
                false -> R.drawable.ic_not_in_playlist
            }
            val context = playerVH.playButton.context
            val imageDrawable = AppCompatResources.getDrawable(context, image)
            playerVH.playlistButton.setImageDrawable(imageDrawable)
        }
    }


}