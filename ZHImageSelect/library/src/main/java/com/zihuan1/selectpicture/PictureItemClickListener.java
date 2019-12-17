package com.zihuan1.selectpicture;

import android.view.View;

public interface PictureItemClickListener {
    /**
     * @param view     当前Item
     * @param position 当前点击位置
     * @param isClickAdd 当前点击的是否是加号按钮
     */
    void onSelectPictureItem(View view, int position, boolean isClickAdd);
}
