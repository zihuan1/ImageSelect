package com.zihuan1.selectpicture

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(private val space: Int, private val column: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // 第一列左边贴边、后边列项依次移动一个space和前一项移动的距离之和
        val mod = parent.getChildAdapterPosition(view) % column
        outRect.left = space * mod
    }

}