package com.zihuan1.app

import android.app.Application
import android.content.pm.ActivityInfo
import com.bumptech.glide.Glide
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zihuan.selectpicture.BasePictureAdapter
import com.zihuan.selectpicture.SelectPictureView

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //        配置点击图片的动作
        BasePictureAdapter.addImageClickFun = {
            Matisse.from(getActivity())
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                    .countable(true)
                    .maxSelectable(currentCount())
                    .gridExpectedSize(300)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(GlideEngineHeight()) //                    .originalEnable(true)//是否显示原图
                    .capture(true) //是否提供拍照功能
                    .captureStrategy(CaptureStrategy(true, "com.zihuan.selectimage"))
                    .spanCount(5)
                    .forResult(SelectPictureView.IMAGE_REQUEST_CODE)
        }
//        配置加载图片
        BasePictureAdapter.loaderImage = { view, url ->
            Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(view)
        }
//        配置默认的Adapter
        BasePictureAdapter.defAdapter = {
            GridImageAdapter2(mContext)
        }
    }
}