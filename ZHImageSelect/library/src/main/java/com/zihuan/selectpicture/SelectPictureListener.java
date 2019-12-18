package com.zihuan.selectpicture;


import java.util.List;

public interface SelectPictureListener {
    void onAddImagesListener(List<String> paths);

    void onDeleteListener(String path);
}
