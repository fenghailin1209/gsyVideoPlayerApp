package com.example.gsyvideoplayer.delagate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.listener.SampleListener;
import com.example.gsyvideoplayer.model.VideoModel;
import com.example.gsyvideoplayer.utils.JumpUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/11/8.
 */

public class TopicVideoItemDelagate implements ItemViewDelegate<VideoModel> {
    //TODO:注意这里的TAG要和外面界面的TAG一样，所以传过来
    private String TAG;
    private StandardGSYVideoPlayer gsyVideoPlayer;
    private Context context;
    private ImageView imageView;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public StandardGSYVideoPlayer getGsyVideoPlayer() {
        return gsyVideoPlayer;
    }

    public TopicVideoItemDelagate(Context context,String TAG) {
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        this.context = context;
        this.TAG = TAG;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_video_item_normal;
    }

    @Override
    public boolean isForViewType(VideoModel item, int position) {
        return item.getType().equals("3");
    }

    @Override
    public void convert(ViewHolder holder, VideoModel item, int position) {
        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (position % 2 == 0) {
            imageView.setImageResource(R.mipmap.xxx1);
        } else {
            imageView.setImageResource(R.mipmap.xxx2);
        }
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }

        setCommonGSYVideoOptionBuilderOperation(holder, item, position);
    }

    /**
     * 设置StandardGSYVideoPlayer的公共方法
     * @param holder
     * @param item
     * @param position
     */
    private void setCommonGSYVideoOptionBuilderOperation(ViewHolder holder, VideoModel item, int position) {
        //TODO:注意 目的是当视频item可见的时候回调出去
        item.onBindViewHolder(this);

        String url = item.getUrl();

        gsyVideoPlayer = holder.getView(R.id.video_item_player);
        //fhl add 20171107
        gsyVideoPlayer.setIsCanTouchChangeView(false);
        gsyVideoPlayer.setPrepareFinishIsShowStartButton(false);
        gsyVideoPlayer.setIsShowBottomContainer(false);
        gsyVideoPlayer.setOnVideoClickListener(new StandardGSYVideoPlayer.OnVideoClickListener() {
            @Override
            public void OnVideoClick() {
                //支持旋转全屏的详情模式
                JumpUtils.goToDetailPlayer((Activity) context);
            }
        });
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle("")
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setSeekOnStart(10)
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        Log.i(TAG, "--->>>!gsyVideoPlayer.isIfCurrentIsFullscreen(): " + !gsyVideoPlayer.isIfCurrentIsFullscreen());
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Log.i(TAG, "--->>>onQuitFullscreen");
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Log.i(TAG, "--->>>onEnterFullscreen");
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                }).build(gsyVideoPlayer);
        Log.i(TAG, "--->>>build");

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    public void autoPlay() {
        if (gsyVideoPlayer != null) {
            gsyVideoPlayer.startPlayLogic();
        }
    }
}
