package com.azmetov.playlistmaker.features.search.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory

class ButtonVHFactory : ViewHolderFactory<ButtonVH> {
    override fun create(parent: ViewGroup): ButtonVH {
        return ButtonVH(
            LayoutInflater.from(parent.context).inflate(R.layout.button_item, parent, false)
        )
    }
}

class ButtonVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val buttonView: Button = itemView.findViewById(R.id.btn_item)
}

class ButtonBinder : BaseVHBinder<ButtonVH, String?> {

    var buttonClickListener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: ButtonVH, data: String?, position: Int) {
        holder.buttonView.text = data
        holder.buttonView.setOnClickListener { buttonClickListener?.invoke() }
    }
}