package com.zihuan.selectpicture

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SelectPictureView : FrameLayout {
    private var delImgHeight = 0
    private var delImgWidth = 0
    private var delImg = 0
    private var itemImg = 0
    private var imgHeight = 0
    private var imgWidth = 0
    private lateinit var mGridImageAdapter: BasePictureAdapter
    lateinit var mContext: Context
    private var margin = 0
    var mSpanCount = 4 //列数
    var mMAxNum = 9

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    lateinit var recyclerView: RecyclerView
    private fun initView(attrs: AttributeSet?) {
        TODO("合并到BaseRecyclerViewAdapter 中")
        mContext = context
        val view = View.inflate(mContext, R.layout.zh_selectimage_view, this)
        recyclerView = view.findViewById(R.id.rv_select_image)
        if (null != attrs) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.SelectPicture)
            mMAxNum = attributes.getInteger(R.styleable.SelectPicture_max_num, 0)
            delImg = attributes.getResourceId(R.styleable.SelectPicture_del_image, 0)
            delImgHeight = attributes.getInteger(R.styleable.SelectPicture_del_image_height, 0)
            delImgWidth = attributes.getInteger(R.styleable.SelectPicture_del_image_width, 0)
            itemImg = attributes.getResourceId(R.styleable.SelectPicture_item_image, 0)
            imgHeight = attributes.getInteger(R.styleable.SelectPicture_item_image_height, 0)
            imgWidth = attributes.getInteger(R.styleable.SelectPicture_item_image_width, 0)
            margin = attributes.getInteger(R.styleable.SelectPicture_margin, 0)
            mSpanCount = attributes.getInteger(R.styleable.SelectPicture_span_count, 3)
            val adapter = attributes.getBoolean(R.styleable.SelectPicture_default_adapter, false)
            if (adapter) {
                mGridImageAdapter = GridImageAdapter(mContext as Activity)
                setAdapter(mGridImageAdapter)
            } else if (null != BasePictureAdapter.defAdapter) {
                mGridImageAdapter = BasePictureAdapter.defAdapter!!.invoke(this)
                setAdapter(mGridImageAdapter)
            }
        }
    }

    fun setAdapter(adapterPicture: BasePictureAdapter) {
        mGridImageAdapter = adapterPicture
        recyclerView.layoutManager = GridLayoutManager(mContext, mSpanCount)
        recyclerView.adapter = mGridImageAdapter
        recyclerView.addItemDecoration(MediaGridInset(mSpanCount, dip2px(margin), false))
        setItemImage(itemImg)
        setItemImgHeight(imgHeight)
        setItemImgWidth(imgWidth)
        setDelImgHeight(delImgHeight)
        setDelImgWidth(delImgWidth)
        setDeleteImage(delImg)
        setMaxNum(mMAxNum)
    }

    /**
     * 点击的是否是加号图片
     */
    fun isClickAddImage(position: Int) = mGridImageAdapter.isShowAddItem(position)


    fun setItemImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.mAddImgHeight = dip2px(height)
        return this
    }

    fun setItemImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.mAddImgWidth = dip2px(width)
        return this
    }

    fun setItemImage(itemImg: Int): SelectPictureView {
        mGridImageAdapter.mAddRes = itemImg
        return this
    }

    fun setDelImgHeight(height: Int): SelectPictureView {
        mGridImageAdapter.mDelResHeight = dip2px(height)
        return this
    }

    fun setDelImgWidth(width: Int): SelectPictureView {
        mGridImageAdapter.mDelResWidth = dip2px(width)
        return this
    }


    fun setDeleteImage(addImg: Int): SelectPictureView {
        mGridImageAdapter.mDelRes = addImg
        return this
    }

    fun setSpanCount(spanCount: Int): SelectPictureView {
        mSpanCount = spanCount
        return this
    }

    fun setMaxNum(remainNum: Int): SelectPictureView {
        mGridImageAdapter.selectMax = remainNum
        return this
    }

    fun setData(paths: List<String>): SelectPictureView {
        mGridImageAdapter.update(paths)
        return this
    }


    companion object {
        var IMAGE_REQUEST_CODE = 10086
    }

    private fun dip2px(dpValue: Int) = (dpValue * resources.displayMetrics.density).toInt()

}