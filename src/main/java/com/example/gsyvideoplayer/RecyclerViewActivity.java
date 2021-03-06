package com.example.gsyvideoplayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.gsyvideoplayer.dao.OnTopicVideoClickListener;
import com.example.gsyvideoplayer.delagate.TopicCommonItemDelagate;
import com.example.gsyvideoplayer.delagate.TopicImageItemDelagate;
import com.example.gsyvideoplayer.delagate.TopicVideoItemDelagate;
import com.example.gsyvideoplayer.holder.RecyclerItemNormalHolder;
import com.example.gsyvideoplayer.model.VideoModel;
import com.example.gsyvideoplayer.utils.AndroidUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_PLAYING;

public class RecyclerViewActivity extends AppCompatActivity implements OnTopicVideoClickListener {

    public static final int REQUEST_CODE = 1;
    private static final String TAG = RecyclerViewActivity.class.getSimpleName();
    RecyclerView videoList;
    LinearLayoutManager mLayoutManager;
    List<VideoModel> dataList = new ArrayList<>();
    boolean mFull = false;
    //可是范围内的位置
    int firstVisibleItem, lastVisibleItem;
    private MultiItemTypeAdapter adapter;
    private Context context;
    private ViewHolder holder;
    public static StandardGSYVideoPlayer gsyVideoPlayer;
    private RelativeLayout id_video_item_player_list_father_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        context = this;
        setContentView(R.layout.activity_recycler_view);
        videoList = (RecyclerView) findViewById(R.id.list_item_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        videoList.setLayoutManager(mLayoutManager);

        initData();

        adapter = new MultiItemTypeAdapter(this, dataList);
        adapter.addItemViewDelegate(new TopicCommonItemDelagate());
        adapter.addItemViewDelegate(new TopicImageItemDelagate());
        adapter.addItemViewDelegate(new TopicVideoItemDelagate(this, TAG, this));
        videoList.setAdapter(adapter);

        setCommonRecycleView(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onVideoPause();
    }

    private void onVideoPause() {
        Log.i(TAG, "--->>>onVideoPause");
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onVideoDestory();
    }


    private void initData() {
        VideoModel videoModel1 = new VideoModel();
        videoModel1.setType("1");
        dataList.add(videoModel1);

        VideoModel videoModel2 = new VideoModel();
        videoModel2.setType("2");
        dataList.add(videoModel2);

        VideoModel videoModel3 = new VideoModel();
        videoModel3.setType("3");
        videoModel3.setUrl("http://baobab.wdjcdn.com/14564977406580.mp4");
        dataList.add(videoModel3);

        VideoModel videoModel4 = new VideoModel();
        videoModel4.setType("1");
        dataList.add(videoModel4);

        VideoModel videoModel5 = new VideoModel();
        videoModel5.setType("2");
        dataList.add(videoModel5);

        VideoModel videoModel6 = new VideoModel();
        videoModel6.setType("3");
        videoModel6.setUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        dataList.add(videoModel6);

        VideoModel videoModel7 = new VideoModel();
        videoModel7.setType("1");
        dataList.add(videoModel7);

        VideoModel videoModel8 = new VideoModel();
        videoModel8.setType("2");
        dataList.add(videoModel8);
    }

    //TODO=========================== 真正关心 start =======================

    /**
     * TODO:标准设置的写法，基本不用改
     *
     * @param adapter
     */
    private void setCommonRecycleView(final MultiItemTypeAdapter adapter) {
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                Log.i(TAG,"--->>>onScrollStateChanged firstVisibleItem:"+firstVisibleItem+",lastVisibleItem:"+lastVisibleItem);
                for (int postion = firstVisibleItem; postion <= lastVisibleItem; postion++) {
                        VideoModel model = dataList.get(postion);
                        String type = model.getType();
                        if (type.equals("3")) {
                            //这两个方法都是获取的屏幕内的view
//                        View view = mLayoutManager.getChildAt(postion);
//                        View view = recyclerView.getChildAt(postion);
                            //这两个方法都是获取的adapter的绝对view
                            View view = mLayoutManager.findViewByPosition(postion);
                            RelativeLayout id_video_item_player_list_father_ll = (RelativeLayout) view.findViewById(R.id.id_video_item_player_list_father_ll);
                            StandardGSYVideoPlayer standardGSYVideoPlayer  = (StandardGSYVideoPlayer) id_video_item_player_list_father_ll.getChildAt(0);
                            int itemHeight = view.getHeight();
                            if (view != null) {
                                int[] location = new int[2];
                                view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                                int locationY = (int) (location[1] - AndroidUtil.getStatusBarHeight(context) - getResources().getDimension(R.dimen.topic_video_height));
                                int rvHeight = recyclerView.getMeasuredHeight();
                                int state = standardGSYVideoPlayer.getCurrentState();
                                int lastState = GSYVideoManager.instance().getLastState();
                                Log.i(TAG,"--->>>locationY"+locationY+",rvHeight:"+rvHeight+",state:"+state+",itemHeight:"+itemHeight+",lastState:"+lastState);
                                if (locationY > -(itemHeight / 2) && locationY < rvHeight - (itemHeight / 2)) {
                                    if(state != CURRENT_STATE_PLAYING){
                                        standardGSYVideoPlayer.startPlayLogic();
                                        Log.i(TAG,"--->>>startPlayLogic");
                                    }
                                } else {
                                    onVideoDestory();
                                }
                                break;
                            }
                        }
                    }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                gsyVideoPlayer = null;

                //视频设置部分
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                int sp = GSYVideoManager.instance().getPlayPosition();
                if (sp >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    String pTag = GSYVideoManager.instance().getPlayTag();
                    if (pTag.equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!mFull) {
                            onVideoDestory();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    /**
     * TODO:标准写法，基本不用改
     */
    private void onVideoResume() {
        Log.i(TAG, "--->>>onVideoResume");
        GSYVideoManager.onResume();
    }

    private void onVideoDestory() {
        Log.i(TAG, "--->>>releaseAllVideos");
        GSYVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == DetailPlayer.RESURT_CODE){
            GSYVideoManager.instance().setNeedMute(true);
            TopicVideoItemDelagate.setGSYCommonParames(gsyVideoPlayer,this.holder,RecyclerViewActivity.this);
            id_video_item_player_list_father_ll.addView(gsyVideoPlayer);
            onVideoResume();
        }
    }

    @Override
    public void onVideoClick(StandardGSYVideoPlayer gsyVideoPlayer, ViewHolder holder) {
        this.holder = holder;
        RecyclerViewActivity.gsyVideoPlayer = gsyVideoPlayer;
        Log.i(TAG, "--->>>gsyVideoPlayer1:"+RecyclerViewActivity.gsyVideoPlayer+",holder:"+holder);
        id_video_item_player_list_father_ll = holder.getView(R.id.id_video_item_player_list_father_ll);
        id_video_item_player_list_father_ll.removeView(gsyVideoPlayer);
        Log.i(TAG, "--->>>gsyVideoPlayer2:"+RecyclerViewActivity.gsyVideoPlayer);

        Intent intent = new Intent(context, DetailPlayer.class);
        startActivityForResult(intent,REQUEST_CODE);
    }


    //TODO=========================== 真正关心 end =======================
}
