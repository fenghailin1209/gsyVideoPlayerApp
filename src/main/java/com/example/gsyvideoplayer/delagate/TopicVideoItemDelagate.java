package com.example.gsyvideoplayer.delagate;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.dao.OnTopicVideoClickListener;
import com.example.gsyvideoplayer.listener.SampleListener;
import com.example.gsyvideoplayer.model.VideoModel;
import com.example.gsyvideoplayer.utils.AndroidUtil;
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
    private static String TAG;
    private StandardGSYVideoPlayer gsyVideoPlayer;
    private Context context;
    private ImageView imageView;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;
    private OnTopicVideoClickListener listener;

    public StandardGSYVideoPlayer getGsyVideoPlayer() {
        return gsyVideoPlayer;
    }

    public TopicVideoItemDelagate(Context context, String TAG, OnTopicVideoClickListener listener) {
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        this.context = context;
        this.TAG = TAG;
        this.listener = listener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_rv_video;
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
     *
     * @param holder
     * @param item
     * @param position
     */
    private void setCommonGSYVideoOptionBuilderOperation(final ViewHolder holder, VideoModel item, int position) {
        String url = item.getUrl();

        RelativeLayout id_video_item_player_list_father_ll = holder.getView(R.id.id_video_item_player_list_father_ll);

        gsyVideoPlayer = new StandardGSYVideoPlayer(context);
        int w = AndroidUtil.getScreenWidth(context);
        int h = AndroidUtil.dip2px(context, 200);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, h);
        gsyVideoPlayer.setLayoutParams(params);

        id_video_item_player_list_father_ll.addView(gsyVideoPlayer);

        setGSYCommonParames(gsyVideoPlayer, holder, listener);

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
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                }).build(gsyVideoPlayer);
    }

    public static void setGSYCommonParames(final StandardGSYVideoPlayer gsyVideoPlayer, final ViewHolder holder, final OnTopicVideoClickListener listener) {
        Log.i(TAG, "--->>>setGSYCommonParames");
        //fhl add 20171107
        gsyVideoPlayer.setIsCanTouchChangeView(false);
        gsyVideoPlayer.setPrepareFinishIsShowStartButton(false);
        gsyVideoPlayer.setIsShowBottomContainer(false);
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        gsyVideoPlayer.setOnVideoClickListener(new StandardGSYVideoPlayer.OnVideoClickListener() {
            @Override
            public void OnVideoClick() {
                if (listener != null) {
                    listener.onVideoClick(gsyVideoPlayer, holder);
                }
            }
        });
    }

}
