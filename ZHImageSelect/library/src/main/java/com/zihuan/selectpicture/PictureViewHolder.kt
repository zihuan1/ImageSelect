package com.zihuan.selectpicture

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PictureViewHolder(itemView: View, iv_picture: Int, iv_del: Int) : RecyclerView.ViewHolder(itemView) {
    var ivPicture: ImageView = itemView.findViewById(iv_picture)
    var ivDel: ImageView = itemView.findViewById(iv_del)
}