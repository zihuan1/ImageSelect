package com.zihuan1.app


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zhihu.matisse.Matisse
import com.zihuan.app.R
import com.zihuan1.selectpicture.PictureItemClickListener
import com.zihuan1.selectpicture.SelectPictureView.Companion.IMAGE_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity2 : Activity(), PictureItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        zh_view.setMaxNum(9)
//                .setSpanCount(4)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
        }
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


    override fun onSelectPictureItem(view: View?, position: Int, isClickAdd: Boolean) {
        if (isClickAdd) {

        } else {
            Log.e("图片详情", "")
        }
    }
}

