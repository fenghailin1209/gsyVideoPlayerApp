package com.example.gsyvideoplayer.delagate;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.model.VideoModel;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/11/8.
 */

public class TopicImageItemDelagate implements ItemViewDelegate<VideoModel>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.layout_topic_img;
    }

    @Override
    public boolean isForViewType(VideoModel item, int position)
    {
        return item.getType().equals("2");
    }

    @Override
    public void convert(ViewHolder holder, VideoModel item, int position) {
    }
}
