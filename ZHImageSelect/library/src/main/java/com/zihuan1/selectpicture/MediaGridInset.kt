package com.zihuan1.selectpicture

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MediaGridInset(private val mSpanCount: Int, private val mSpacing: Int, private val mIncludeEdge: Boolean) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //            如果rv是充满布局的话,可能会出现右边距没有贴边的问题
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % mSpanCount // item column
//        Log.e("列数", column.toString() + "")
        if (mIncludeEdge) { // spacing - column * ((1f / spanCount) * spacing)
            outRect.left = mSpacing - column * mSpacing / mSpanCount
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * mSpacing / mSpanCount
            if (position < mSpanCount) { // top edge
                outRect.top = mSpacing
            }
            outRect.bottom = mSpacing // item bottom
        } else { // column * ((1f / spanCount) * spacing)
            outRect.left = column * mSpacing / mSpanCount
            //            outRect.right = 0;
            outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount
            if (position >= mSpanCount) {
                outRect.top = mSpacing // item top
            }
        }
    }

}