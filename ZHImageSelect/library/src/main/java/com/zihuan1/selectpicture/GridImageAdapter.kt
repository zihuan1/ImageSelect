package com.zihuan1.selectpicture

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
    private var pictureItemClickListener: PictureItemClickListener? = null
    private val TYPE_CAMERA = 1
    private val TYPE_PICTURE = 2
    private var baseData = ArrayList<String>()
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
        if (`object` is PictureItemClickListener) pictureItemClickListener = `object`
    }


    override fun getItemCount(): Int {
        return if (baseData.size < selectMax) {
            baseData.size + 1
        } else {
            baseData.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = View.inflate(parent.context, R.layout.zh_rv_image, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageView = holder.ivPicture
        val ivDel = holder.ivDel
        val params = imageView.layoutParams
        params.height = mAddImgHeight
        params.width = mAddImgWidth
        imageView.layoutParams = params
        imageView.setCornerRadius(topLeft, topRight, bottomLeft, bottomRight)
        if (mDelResWidth != 0 && mDelResHeight != 0) {
            val delParams = ivDel.layoutParams
            delParams.height = mDelResHeight
            delParams.width = mDelResWidth
            ivDel.layoutParams = delParams
        }
//        点击当前图片
        imageView.setOnClickListener {
            if (isShowAddItem(position)) {
                addImageClickFun?.invoke(selectPictureView)
            }
            pictureItemClickListener?.onSelectPictureItem(holder.itemView, position, isShowAddItem(position))
        }
        //少于8张，显示继续添加的图标
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
        var ivPicture: RoundedImageView = itemView.findViewById(R.id.iv_picture)
        var ivDel: ImageView = itemView.findViewById(R.id.iv_del)
    }

    companion object {
        /**
         * 配置添加点击加号图片的动作
         */
        var addImageClickFun: ((pictureView: SelectPictureView) -> Unit)? = null

        /**
         * 配置图片加载方式
         */
        var loaderImage: ((view: RoundedImageView, url: String) -> Unit)? = null
    }
}