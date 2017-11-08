package com.example.gsyvideoplayer.model;

import com.volokh.danylo.videolist.visibility_demo.adapter.items.VisibilityItem;

/**
 * Created by shuyu on 2016/11/11.
 */

public class VideoModel extends VisibilityItem {
    private boolean isAutoPlay;

    private String type;//1 文字类item，2 纯图片，3 视频
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
