package com.azmetov.playlistmaker.features.player.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory
import java.text.SimpleDateFormat
import java.util.*

class TimerVHFactory : ViewHolderFactory<TimerVH> {

    override fun create(parent: ViewGroup): TimerVH {
        return TimerVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.player_timer_item, parent, false)
        )
    }

}


class TimerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val timer = itemView as TextView
}

class TimerBinder : BaseVHBinder<TimerVH, Int> {

    private var holder: TimerVH? = null

    override fun onBindViewHolder(holder: TimerVH, data: Int, position: Int) {
        if (this.holder == null) this.holder = holder
        setTimer(data)
    }

    fun setTimer(time: Int) {
        holder?.timer?.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(time)
    }

}