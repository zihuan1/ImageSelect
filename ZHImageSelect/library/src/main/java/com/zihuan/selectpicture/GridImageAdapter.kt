package com.zihuan.selectpicture

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * 默认的适配器
 *
 * @author Zihuan
 */
class GridImageAdapter(context: Context) : BasePictureAdapter(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = View.inflate(parent.context, R.layout.zh_rv_image, null)
        return PictureViewHolder(view, R.id.iv_picture, R.id.iv_del)
    }

}