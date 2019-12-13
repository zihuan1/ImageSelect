package com.zihuan1.app


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zhihu.matisse.Matisse
import com.zihuan.app.R
import com.zihuan1.selectimage.ZHSelectSelectImageView.IMAGE_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        zh_view.setMaxNum(9)
//                .setSpanCount(4)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            when (requestCode) {
                IMAGE_REQUEST_CODE -> {
                    zh_view.setData(Matisse.obtainPathResult(data))
                }
            }
    }
}

