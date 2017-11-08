package com.example.gsyvideoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gsyvideoplayer.utils.JumpUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    Button openBtn;

    Button openBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debuger.enable();

        initView();

        setListener();
    }

    private void initView() {
        openBtn = (Button) findViewById(R.id.open_btn);
        openBtn2 = (Button) findViewById(R.id.open_btn_empty);
    }

    private void setListener() {
        openBtn.setOnClickListener(this);
        openBtn2.setOnClickListener(this);

        findViewById(R.id.list_btn_2).setOnClickListener(this);
        findViewById(R.id.list_detail).setOnClickListener(this);
        findViewById(R.id.clear_cache).setOnClickListener(this);
        findViewById(R.id.recycler).setOnClickListener(this);
        findViewById(R.id.recycler_2).setOnClickListener(this);
        findViewById(R.id.list_detail_list).setOnClickListener(this);
        findViewById(R.id.web_detail).setOnClickListener(this);
        findViewById(R.id.danmaku_video).setOnClickListener(this);
        findViewById(R.id.fragment_video).setOnClickListener(this);
        findViewById(R.id.more_type).setOnClickListener(this);
        findViewById(R.id.input_type).setOnClickListener(this);
        findViewById(R.id.open_control).setOnClickListener(this);
        findViewById(R.id.list_btn).setOnClickListener(this);
        findViewById(R.id.open_filter).setOnClickListener(this);
        findViewById(R.id.open_btn_pick).setOnClickListener(this);
    }

    public void onClick(View view) {
        Log.i(TAG,"--->>>id:"+view.getId());
        int i = view.getId();
        if (i == R.id.open_btn) {//直接一个页面播放的
            JumpUtils.goToVideoPlayer(this, openBtn);

        } else if (i == R.id.list_btn) {//普通列表播放，只支持全屏，但是不支持屏幕重力旋转，滑动后不持有
            JumpUtils.goToVideoPlayer(this);

        } else if (i == R.id.list_btn_2) {//支持全屏重力旋转的列表播放，滑动后不会被销毁
            JumpUtils.goToVideoPlayer2(this);

        } else if (i == R.id.recycler) {//recycler的demo
            JumpUtils.goToVideoRecyclerPlayer(this);

        } else if (i == R.id.recycler_2) {//recycler的demo
            JumpUtils.goToVideoRecyclerPlayer2(this);

        } else if (i == R.id.list_detail) {//支持旋转全屏的详情模式
            JumpUtils.goToDetailPlayer(this);

        } else if (i == R.id.list_detail_list) {//播放一个连续列表
            JumpUtils.goToDetailListPlayer(this);

        } else if (i == R.id.web_detail) {//正常播放，带preview
            JumpUtils.gotoWebDetail(this);

        } else if (i == R.id.danmaku_video) {//播放一个弹幕视频
            JumpUtils.gotoDanmaku(this);

        } else if (i == R.id.fragment_video) {//播放一个弹幕视频
            JumpUtils.gotoFragment(this);

        } else if (i == R.id.more_type) {//跳到多类型详情播放器，比如切换分辨率，旋转等
            JumpUtils.gotoMoreType(this);

        } else if (i == R.id.input_type) {
            JumpUtils.gotoInput(this);

        } else if (i == R.id.open_btn_empty) {
            JumpUtils.goToPlayEmptyControlActivity(this, openBtn2);

        } else if (i == R.id.open_control) {
            JumpUtils.gotoControl(this);

        } else if (i == R.id.open_filter) {
            JumpUtils.gotoFilter(this);

        } else if (i == R.id.open_btn_pick) {//无缝切换
            JumpUtils.goToVideoPickPlayer(this, openBtn);

        } else if (i == R.id.clear_cache) {//清理缓存
            GSYVideoManager.clearAllDefaultCache(MainActivity.this);
            //String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
            //GSYVideoManager.clearDefaultCache(MainActivity.this, url);

        }
    }
}
