package com.zihuan1.selectimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zihuan.baseadapter.RecyclerAdapter;
import com.zihuan.baseadapter.RecyclerViewHolder;
import com.zihuan.selectimage.R;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends RecyclerAdapter {

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private int selectMax = 9;

    private SelectImageListener selectImageListener;

    @Override
    public int getLayoutResId() {
        return R.layout.zh_rv_image;
    }

    public GridImageAdapter(Object object) {
        super(object);
        selectImageListener = (SelectImageListener) object;
    }


    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    @Override
    public int getItemCount() {
        if (baseDatas.size() < selectMax) {
            return baseDatas.size() + 1;
        } else {
            return baseDatas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public void convert(final RecyclerViewHolder holder, final int position, Context context) {

        ImageView imageView = holder.getImageView(R.id.iv_picture);
        ImageView iv_del = holder.getImageView(R.id.iv_del);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = mAddImgHeight;
        params.width = mAddImgWidth;
        imageView.setLayoutParams(params);
        if (mDelResWidth != 0 && mDelResHeight != 0) {
            ViewGroup.LayoutParams del_params = iv_del.getLayoutParams();
            del_params.height = mDelResHeight;
            del_params.width = mDelResWidth;
            iv_del.setLayoutParams(del_params);
        }
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            imageView.setImageResource(mAddRes == 0 ? R.drawable.addimg_1x : mAddRes);
            iv_del.setVisibility(View.GONE);
        } else {
            if (mDelRes != 0) {
                iv_del.setImageResource(mDelRes);
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_def)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(baseDatas.get(position).toString())
                    .apply(options)
                    .into(imageView);
            iv_del.setVisibility(View.VISIBLE);
            iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImageListener.onDeleteListener(baseDatas.get(position).toString());
                    baseDatas.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, baseDatas.size());
                }
            });
        }
    }

    private int mDelRes;
    private int mAddRes;
    private int mAddImgHeight = 160;
    private int mAddImgWidth = 160;
    private int mDelResHeight = 0;
    private int mDelResWidth = 0;

    public void setAddImageHeight(int height) {
        mAddImgHeight = height;
    }

    public void setAddImageWidth(int width) {
        mAddImgWidth = width;
    }

    public void setDelImageHeight(int height) {
        mDelResHeight = height;
    }

    public void setDelImageWidth(int width) {
        mDelResWidth = width;
    }

    public void setAddImage(int addImage) {
        mAddRes = addImage;
    }

    public void setDelImage(int delImage) {
        mDelRes = delImage;
    }


    public boolean isShowAddItem(int position) {
        int size = baseDatas.size() == 0 ? 0 : baseDatas.size();
        return position == size;
    }

}
