package com.zihuan1.selectpicture

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
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
            val addImg = attributes.getResourceId(R.styleable.SelectPicture_add_image, 0)
            val addImgHeight = attributes.getInteger(R.styleable.SelectPicture_add_image_height, 0)
            val addImgWidth = attributes.getInteger(R.styleable.SelectPicture_add_image_width, 0)
            mSpanCount = attributes.getInteger(R.styleable.SelectPicture_span_count, 4)

            setDelImgHeight(delImgHeight)
            setDelImgWidth(delImgWidth)
            setAddImgHeight(addImgHeight)
            setAddImgWidth(addImgWidth)
            setDeleteImage(delImg)
            setAddImage(addImg)
            setMaxNum(mMAxNum)
        }
        //    左边距有问题
        val manager = FullyGridLayoutManager(mContext, mSpanCount)
        recyclerView.layoutManager = manager
        recyclerView.adapter = mGridImageAdapter

    }

    fun setAddImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.setAddImageHeight(dip2px(height.toFloat()))
        return this
    }

    fun setAddImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.setAddImageWidth(dip2px(width.toFloat()))
        return this
    }

    fun setAddImage(delImg: Int): SelectPictureView {
        mGridImageAdapter.setAddImage(delImg)
        return this
    }

    fun setDelImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.setDelImageHeight(dip2px(height.toFloat()))
        return this
    }

    fun setDelImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.setDelImageWidth(dip2px(width.toFloat()))
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

    private fun dip2px(dpValue: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}