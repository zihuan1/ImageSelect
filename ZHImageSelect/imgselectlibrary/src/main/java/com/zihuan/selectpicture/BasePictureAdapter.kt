package com.zihuan.selectpicture

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * 默认的适配器
 *
 * @author Zihuan
 */
abstract class BasePictureAdapter(val context: Context) : RecyclerView.Adapter<PictureViewHolder>() {
    var selectMax = 9
    private var selectPictureListener: SelectPictureListener? = null
    private var pictureItemClickListener: PictureItemClickListener? = null
    private val TYPE_CAMERA = 1
    private val TYPE_PICTURE = 2
    var baseData = ArrayList<String>()
    var mDelRes = 0
    var mAddRes = 0

    init {
        if (context is SelectPictureListener) selectPictureListener = context
        if (context is PictureItemClickListener) pictureItemClickListener = context
    }


    override fun getItemCount() = if (baseData.size < selectMax) {
        baseData.size + 1
    } else {
        baseData.size
    }


    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val imageView = holder.ivPicture
        val ivDel = holder.ivDel
//        点击当前图片
        imageView.setOnClickListener {
            if (isShowAddItem(position)) {
                addImageClickFun?.invoke(this)
            }
            pictureItemClickListener?.onSelectPictureItem(holder.itemView, position, isShowAddItem(position))
        }
        //少于max张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            imageView.setImageResource(if (mAddRes == 0) R.drawable.addimg_1x else mAddRes)
            ivDel.visibility = View.GONE
        } else {
            loaderImage?.invoke(imageView, baseData[position])
            if (mDelRes != 0) {
                ivDel.setImageResource(mDelRes)
            }
            ivDel.visibility = View.VISIBLE
            ivDel.setOnClickListener {
                selectPictureListener?.onDeleteListener(baseData[position])
                baseData.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowAddItem(position)) TYPE_CAMERA else TYPE_PICTURE
    }

    fun update(list: List<String>) {
        baseData.addAll(list)
        notifyDataSetChanged()
    }

    fun getActivity() = context as Activity

    fun isShowAddItem(position: Int): Boolean {
        val size = if (baseData.isEmpty()) 0 else baseData.size
        return position == size
    }

    //当前剩余最大可用数
    fun currentCount() = selectMax.minus(baseData.size)

    companion object {
        /**
         * 配置添加点击加号图片的动作
         */
        var addImageClickFun: (BasePictureAdapter.() -> Unit?)? = null

        /**
         * 配置图片加载方式
         */
        var loaderImage: ((view: ImageView, url: String) -> Unit)? = null

        var defAdapter: (SelectPictureView.() -> BasePictureAdapter)? = null
    }


}