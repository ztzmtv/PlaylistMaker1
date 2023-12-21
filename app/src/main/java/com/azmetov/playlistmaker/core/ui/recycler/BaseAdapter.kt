package com.azmetov.playlistmaker.core.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<ListItem<RecyclerView.ViewHolder, Any>>()
    private var recyclerView: RecyclerView? = null

    fun <VH : RecyclerView.ViewHolder, DATA> addItem(item: ListItem<VH, DATA>) {
        items.add(item as ListItem<RecyclerView.ViewHolder, Any>)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun clear() {
        items.clear()
        while (recyclerView?.itemDecorationCount!! > 0) {
            recyclerView?.removeItemDecorationAt(0);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return items.find { it.factory.hashCode() == viewType }?.factory!!.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        item.binder.onBindViewHolder(holder, item.data, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].factory.hashCode()
    }
}

class ListItem<VH : RecyclerView.ViewHolder, DATA>(
    val factory: ViewHolderFactory<VH>,
    val binder: BaseVHBinder<VH, DATA>,
    val data: DATA
)