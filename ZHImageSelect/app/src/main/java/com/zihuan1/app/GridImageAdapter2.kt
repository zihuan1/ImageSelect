package com.zihuan1.app

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.zihuan.app.R
import com.zihuan.selectpicture.BasePictureAdapter
import com.zihuan.selectpicture.PictureViewHolder

/**
 * @author Zihuan
 */
class GridImageAdapter2(context: Context) : BasePictureAdapter(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = View.inflate(parent.context, R.layout.item_rv_image, null)
        return PictureViewHolder(view, R.id.iv_picture2, R.id.iv_del2)
    }

}