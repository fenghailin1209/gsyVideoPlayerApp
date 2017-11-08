package com.example.gsyvideoplayer.model;

import com.volokh.danylo.videolist.visibility_demo.adapter.items.VisibilityItem;

/**
 * Created by shuyu on 2016/11/11.
 */

public class VideoModel extends VisibilityItem {
    private boolean isAutoPlay;

    public VideoModel(ItemCallback callback) {
        super(callback);
    }

    public VideoModel() {
        super(null);
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }
}
