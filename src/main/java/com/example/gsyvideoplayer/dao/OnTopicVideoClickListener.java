package com.example.gsyvideoplayer.dao;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/11/10.
 */

public interface OnTopicVideoClickListener{
    public void onVideoClick(StandardGSYVideoPlayer gsyVideoPlayer, ViewHolder holder);
}
