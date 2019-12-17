package com.zihuan1.selectpicture

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import com.zihuan.selectpicture.R
import com.zihuan1.selectpicture.GridImageAdapter.MyViewHolder
import java.util.*

/**
 * 默认的适配器
 *
 * @author Zihuan
 */
class GridImageAdapter(`object`: Context?, private val selectPictureView: SelectPictureView) : RecyclerView.Adapter<MyViewHolder>() {
    private var selectMax = 9
    private var selectPictureListener: SelectPictureListener? = null
    private var pictureItemListener: PictureItemListener? = null
    private var pictureLoaderListener: PictureLoaderListener? = null

    private val TYPE_CAMERA = 1
    private val TYPE_PICTURE = 2
    var baseData = ArrayList<String>()
    private var topLeft = 0f
    private var topRight = 0f
    private var bottomLeft = 0f
    private var bottomRight = 0f
    private var mDelRes = 0
    private var mAddRes = 0
    private var mAddImgHeight = 160
    private var mAddImgWidth = 160
    private var mDelResHeight = 0
    private var mDelResWidth = 0

    init {
        if (`object` is SelectPictureListener) selectPictureListener = `object`
        if (`object` is PictureItemListener) pictureItemListener = `object`
        if (`object` is PictureLoaderListener) pictureLoaderListener = `object`
    }


    override fun getItemCount(): Int {
        return if (baseData.size < selectMax) {
            baseData.size + 1
        } else {
            baseData.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.zh_rv_image, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageView = holder.iv_picture
        val iv_del = holder.iv_del
        val params = imageView.layoutParams
        params.height = mAddImgHeight
        params.width = mAddImgWidth
        imageView.layoutParams = params
        imageView.setCornerRadius(topLeft, topRight, bottomLeft, bottomRight)
        if (mDelResWidth != 0 && mDelResHeight != 0) {
            val del_params = iv_del.layoutParams
            del_params.height = mDelResHeight
            del_params.width = mDelResWidth
            iv_del.layoutParams = del_params
        }
//        点击当前图片
        imageView.setOnClickListener {
            if (isShowAddItem(position)) {
                addImageClickFun?.invoke(selectPictureView)
            }
            pictureItemListener?.onSelectPictureItem(holder.itemView, position, isShowAddItem(position))
        }
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            imageView.setImageResource(if (mAddRes == 0) R.drawable.addimg_1x else mAddRes)
            iv_del.visibility = View.GONE
        } else {
            pictureLoaderListener?.onSelectPictureLoader(imageView, baseData[position])
            if (mDelRes != 0) {
                iv_del.setImageResource(mDelRes)
            }
            iv_del.visibility = View.VISIBLE
            iv_del.setOnClickListener {
                selectPictureListener?.onDeleteListener(baseData[position])
                baseData.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, baseData.size)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowAddItem(position)) TYPE_CAMERA else TYPE_PICTURE
    }

    fun update(list: List<String>) {
        baseData.clear()
        baseData.addAll(list)
        notifyDataSetChanged()
    }

    fun setSelectMax(selectMax: Int) {
        this.selectMax = selectMax
    }

    fun setItemImageHeight(height: Int) {
        mAddImgHeight = height
    }

    fun setItemImageWidth(width: Int) {
        mAddImgWidth = width
    }

    fun setDelImageHeight(height: Int) {
        mDelResHeight = height
    }

    fun setDelImageWidth(width: Int) {
        mDelResWidth = width
    }

    fun setItemImage(addImage: Int) {
        mAddRes = addImage
    }

    fun setDelImage(delImage: Int) {
        mDelRes = delImage
    }


    /**
     * 设置圆角半径
     */
    fun setCornerRadius(tLeft: Float, tRight: Float, bLeft: Float, bRight: Float) {
        topLeft = tLeft
        topRight = tRight
        bottomLeft = bLeft
        bottomRight = bRight
    }

    fun isShowAddItem(position: Int): Boolean {
        val size = if (baseData.isEmpty()) 0 else baseData.size
        return position == size
    }

    inner class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var iv_picture: RoundedImageView = itemView.findViewById(R.id.iv_picture)
        var iv_del: ImageView = itemView.findViewById(R.id.iv_del)
    }

    companion object {
        /**
         * 添加点击加号图片的动作
         */
        var addImageClickFun: ((pictureView: SelectPictureView) -> Unit)? = null
    }
}