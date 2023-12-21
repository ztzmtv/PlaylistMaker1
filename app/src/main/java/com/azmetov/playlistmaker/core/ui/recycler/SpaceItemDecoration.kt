package com.azmetov.playlistmaker.core.ui.recycler

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class SpaceItemDecoration(
    private val context: Context,
    private val position: Int,
    private val spaceTopDp: Int = 0,
    private val spaceBottomDp: Int = 0,
    private val spaceRightDp: Int = 0,
    private val spaceLeftDp: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == this.position) {
            outRect.top = dpToPx(spaceTopDp)
            outRect.bottom = dpToPx(spaceBottomDp)
            outRect.left = dpToPx(spaceLeftDp)
            outRect.right = dpToPx(spaceRightDp)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).roundToInt()
    }
}