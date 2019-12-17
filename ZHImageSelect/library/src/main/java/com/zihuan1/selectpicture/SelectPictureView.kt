package com.zihuan1.selectpicture

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zihuan.selectpicture.R
import java.util.*

class SelectPictureView : FrameLayout, SelectPictureListener {
    lateinit var mGridImageAdapter: GridImageAdapter
    var mSpanCount = 4 //列数
    var mMAxNum = 9
    private var mImages = ArrayList<String>()
    private lateinit var mContext: Context

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        mContext = context
        val view = View.inflate(mContext, R.layout.zh_selectimage_view, this)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_image)
        mGridImageAdapter = GridImageAdapter(mContext)
        if (null != attrs) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.SelectPicture)
            mMAxNum = attributes.getInteger(R.styleable.SelectPicture_max_num, 0)
            val delImg = attributes.getResourceId(R.styleable.SelectPicture_del_image, 0)
            val delImgHeight = attributes.getInteger(R.styleable.SelectPicture_del_image_height, 0)
            val delImgWidth = attributes.getInteger(R.styleable.SelectPicture_del_image_width, 0)
            val itemImg = attributes.getResourceId(R.styleable.SelectPicture_item_image, 0)
            val imgHeight = attributes.getInteger(R.styleable.SelectPicture_item_image_height, 0)
            val imgWidth = attributes.getInteger(R.styleable.SelectPicture_item_image_width, 0)
            mSpanCount = attributes.getInteger(R.styleable.SelectPicture_span_count, 3)
            val tLeft = attributes.getInteger(R.styleable.SelectPicture_radius_top_left, 0)
            val tRight = attributes.getInteger(R.styleable.SelectPicture_radius_top_right, 0)
            val bLeft = attributes.getInteger(R.styleable.SelectPicture_radius_bottom_left, 0)
            val bRight = attributes.getInteger(R.styleable.SelectPicture_radius_bottom_right, 0)
            setCornerRadius(dip2px(tLeft), dip2px(tRight), dip2px(bLeft), dip2px(bRight))
            setItemImage(itemImg)
            setItemImgHeight(imgHeight)
            setItemImgWidth(imgWidth)
            setDelImgHeight(delImgHeight)
            setDelImgWidth(delImgWidth)
            setDeleteImage(delImg)
            setMaxNum(mMAxNum)
        }
        recyclerView.layoutManager = GridLayoutManager(mContext, mSpanCount)
        recyclerView.adapter = mGridImageAdapter
        recyclerView.addItemDecoration(MediaGridInset(mSpanCount, dip2px(4), false))
    }

    /**
     * 设置圆角半径
     */
    fun setCornerRadius(tLeft: Int, tRight: Int, bLeft: Int, bRight: Int): SelectPictureView {
        mGridImageAdapter.setCornerRadius(tLeft.toFloat(), tRight.toFloat(), bLeft.toFloat(), bRight.toFloat())
        return this
    }

    fun setItemImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.setItemImageHeight(dip2px(height))
        return this
    }

    fun setItemImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.setItemImageWidth(dip2px(width))
        return this
    }

    fun setItemImage(itemImg: Int): SelectPictureView {
        mGridImageAdapter.setItemImage(itemImg)
        return this
    }

    fun setDelImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.setDelImageHeight(dip2px(height))
        return this
    }

    fun setDelImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.setDelImageWidth(dip2px(width))
        return this
    }


    fun setDeleteImage(addImg: Int): SelectPictureView {
        mGridImageAdapter.setDelImage(addImg)
        return this
    }

    fun setSpanCount(spanCount: Int): SelectPictureView {
        mSpanCount = spanCount
        return this
    }

    fun setMaxNum(remainNum: Int): SelectPictureView {
        mMAxNum = remainNum
        mGridImageAdapter.setSelectMax(mMAxNum)
        return this
    }

    fun setData(paths: List<String>): SelectPictureView {
        mImages.addAll(paths)
        mGridImageAdapter.update(mImages)
        return this
    }


    override fun onAddImagesListener(paths: List<String>) {

    }

    override fun onDeleteListener(path: String) {
        if (mImages.contains(path)) {
            mImages.remove(path)
        }
    }

    companion object {
        var IMAGE_REQUEST_CODE = 10086
    }

    private fun dip2px(dpValue: Int) = (dpValue * resources.displayMetrics.density).toInt()

}