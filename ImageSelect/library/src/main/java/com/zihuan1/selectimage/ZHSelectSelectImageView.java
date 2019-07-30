package com.zihuan1.selectimage;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zihuan.baseadapter.ViewOnItemClick;
import com.zihuan.selectimage.R;

import java.util.ArrayList;
import java.util.List;

public class ZHSelectSelectImageView extends FrameLayout implements ViewOnItemClick, SelectImageListener {

    private int mSpanCount = 4;//列数
    private GridImageAdapter mGridImageAdapter;
    private int mMAxNum = 9;
    public static int IMAGE_REQUEST_CODE = 10086;
    private boolean mCapture;
    private List<String> mImages = new ArrayList<>();


    public ZHSelectSelectImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public ZHSelectSelectImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private Context mContext;

    private void initView(AttributeSet attrs) {
        mContext = getContext();
        View view = View.inflate(mContext, R.layout.zh_selectimage_view, null);
        addView(view);
        RecyclerView recyclerView = view.findViewById(R.id.rv_select_image);
        //    还是有左边距的问题
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, mSpanCount);
        mGridImageAdapter = new GridImageAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mGridImageAdapter);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ZHSelectSelectImageView);
        mMAxNum = attributes.getInteger(R.styleable.ZHSelectSelectImageView_max_num, 0);
        int delImg = attributes.getResourceId(R.styleable.ZHSelectSelectImageView_del_image, 0);
        int addImg = attributes.getResourceId(R.styleable.ZHSelectSelectImageView_add_image, 0);
        int addImgHeight = attributes.getInteger(R.styleable.ZHSelectSelectImageView_add_image_height, 0);
        int addImgWidth = attributes.getInteger(R.styleable.ZHSelectSelectImageView_add_image_width, 0);
        setAddImgHeight(addImgHeight);
        setAddImgWidth(addImgWidth);
        setDeleteImage(delImg);
        setAddImage(addImg);
        setMaxNum(mMAxNum);
    }

    //把dp转换成px
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public ZHSelectSelectImageView setAddImgHeight(int height) {
        mGridImageAdapter.setAddImageHeight(dip2px(height));
        return this;
    }

    public ZHSelectSelectImageView setAddImgWidth(int width) {
        mGridImageAdapter.setAddImageWidth(dip2px(width));
        return this;
    }

    public ZHSelectSelectImageView setAddImage(int delImg) {
        mGridImageAdapter.setAddImage(delImg);
        return this;
    }

    public ZHSelectSelectImageView setDeleteImage(int addImg) {
        mGridImageAdapter.setDelImage(addImg);
        return this;
    }

    public ZHSelectSelectImageView setSpanCount(int spanCount) {
        mSpanCount = spanCount;
        return this;
    }

    public ZHSelectSelectImageView setMaxNum(int remainNum) {
        mMAxNum = remainNum;
        mGridImageAdapter.setSelectMax(mMAxNum);
        return this;
    }


    public ZHSelectSelectImageView setData(List<String> paths) {
        mImages.addAll(paths);
        mGridImageAdapter.update(mImages);
        return this;
    }


    public ZHSelectSelectImageView setCapture(boolean capture) {
        mCapture = capture;
        return this;
    }

    @Override
    public void setOnItemClickListener(View view, int postion) {
        if (mGridImageAdapter.isShowAddItem(postion)) {
            Matisse.from((Activity) getContext())
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                    .countable(true)
                    .maxSelectable(mMAxNum - mGridImageAdapter.baseDatas.size())
                    .gridExpectedSize(300)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngineHeight())
//                    .originalEnable(true)//是否显示原图
                    .capture(mCapture) //是否提供拍照功能
                    .captureStrategy(new CaptureStrategy(true, "com.zihuan.selectimage"))
                    .forResult(IMAGE_REQUEST_CODE);
        } else {
            Log.e("图片详情", "");
        }
    }

    @Override
    public void onAddImagesListener(List<String> paths) {

    }

    @Override
    public void onDeleteListener(String path) {
        if (mImages.contains(path)) {
            mImages.remove(path);
        }
    }
}
