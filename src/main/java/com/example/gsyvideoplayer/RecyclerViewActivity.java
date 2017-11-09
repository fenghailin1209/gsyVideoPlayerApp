package com.example.gsyvideoplayer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.gsyvideoplayer.delagate.TopicCommonItemDelagate;
import com.example.gsyvideoplayer.delagate.TopicImageItemDelagate;
import com.example.gsyvideoplayer.delagate.TopicVideoItemDelagate;
import com.example.gsyvideoplayer.holder.RecyclerItemNormalHolder;
import com.example.gsyvideoplayer.model.VideoModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity{

    private static final String TAG = RecyclerViewActivity.class.getSimpleName();
    RecyclerView videoList;
    LinearLayoutManager mLayoutManager;
    List<VideoModel> dataList = new ArrayList<>();
    boolean mFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        videoList = (RecyclerView) findViewById(R.id.list_item_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        videoList.setLayoutManager(mLayoutManager);

        initData();

        final MultiItemTypeAdapter adapter = new MultiItemTypeAdapter(this, dataList);
        adapter.addItemViewDelegate(new TopicCommonItemDelagate());
        adapter.addItemViewDelegate(new TopicImageItemDelagate());
        adapter.addItemViewDelegate(new TopicVideoItemDelagate(this,TAG));
        videoList.setAdapter(adapter);

        setCommonRecycleViewOnTouchUpOperation(videoList);

        setCommonRecycleView(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onVideoPause();
    }

    private void onVideoPause() {
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

    private void setCommonRecycleViewOnTouchUpOperation(RecyclerView recyclerView) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int ac = event.getAction();
                if (ac == MotionEvent.ACTION_UP) {

                }
                return false;
            }
        });
    }

    /**
     * TODO:标准设置的写法，基本不用改
     * @param adapter
     */
    private void setCommonRecycleView(final MultiItemTypeAdapter adapter) {
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);}

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
                    Log.i(TAG, "--->>>sp:" + sp+",pTag:"+pTag);
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
        GSYVideoManager.onResume();
    }

    private void onVideoDestory() {
        Log.i(TAG, "--->>>releaseAllVideos" );
        GSYVideoPlayer.releaseAllVideos();
    }
    //TODO=========================== 真正关心 end =======================
}
