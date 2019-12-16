package com.zihuan1.selectpicture;

import java.util.List;

public interface SelectPictureListener {
    void onAddImagesListener(List<String> paths);

    void onDeleteListener(String path);
}
