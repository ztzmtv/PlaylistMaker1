package com.azmetov.playlistmaker.core.ui.recycler.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.utils.Converter
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class TrackVHFactory : ViewHolderFactory<TrackVH> {
    override fun create(parent: ViewGroup): TrackVH {
        return TrackVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false)
        )
    }
}

class TrackVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val artwork: ImageView =
        itemView.findViewById(R.id.iv_item_artwork)
    private val artistNameAndTrackTime: TextView =
        itemView.findViewById(R.id.tv_artist_name_and_track_time)
    private val trackName: TextView =
        itemView.findViewById(R.id.tv_track_name)

    fun bind(track: Track) {
        trackName.text = track.trackName
        Glide.with(itemView.context)
            .load(track.artworkUrl100)
            .centerCrop()
            .transform(RoundedCorners(8))
            .placeholder(R.drawable.album_placeholder)
            .into(artwork)
        val template = itemView.context.getString(R.string.album_title_and_track_time_template)
        artistNameAndTrackTime.text =
            String.format(template, track.artistName, Converter.convertTime(track.trackTime))
    }
}

class TrackBinder : BaseVHBinder<TrackVH, Track> {

    var trackClickListener: ((Track) -> Unit)? = null

    override fun onBindViewHolder(holder: TrackVH, data: Track, position: Int) {
        holder.bind(data)
        holder.itemView.setOnClickListener { trackClickListener?.invoke(data) }
    }
}
