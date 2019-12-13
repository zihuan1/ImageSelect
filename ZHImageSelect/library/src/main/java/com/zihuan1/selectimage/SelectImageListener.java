package com.zihuan1.selectimage;

import java.util.List;

public interface SelectImageListener {
    void onAddImagesListener(List<String> paths);

    void onDeleteListener(String path);
}
