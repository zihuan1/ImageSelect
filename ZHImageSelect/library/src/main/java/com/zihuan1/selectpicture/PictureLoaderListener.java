package com.zihuan1.selectpicture;

import android.widget.ImageView;

/**
 * 实现图片的加载
 */
public interface PictureLoaderListener {
    void onSelectPictureLoader(ImageView view, String url);
}
