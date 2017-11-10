package com.example.gsyvideoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import static com.example.gsyvideoplayer.RecyclerViewActivity.gsyVideoPlayer;


/**
 * 详情界面
 */
public class DetailPlayer extends AppCompatActivity {
    public static final int RESURT_CODE = 2;
    private static final String TAG = DetailPlayer.class.getSimpleName();
    RelativeLayout id_video_play_father_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_player);

        initView();

        id_video_play_father_rl.addView(gsyVideoPlayer);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "--->>>getFullscreenButton onClick");
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        gsyVideoPlayer.setOnVideoClickListener(null);
        GSYVideoManager.instance().setNeedMute(false);
        gsyVideoPlayer.setIsCanTouchChangeView(true);
        gsyVideoPlayer.setPrepareFinishIsShowStartButton(true);
        gsyVideoPlayer.setIsShowBottomContainer(true);
        GSYVideoManager.onResume();
    }

    private void initView() {
        id_video_play_father_rl = (RelativeLayout) findViewById(R.id.id_video_play_father_rl);
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(this, true, true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            gsyVideoPlayer.hideAllWidget();
            id_video_play_father_rl.removeView(gsyVideoPlayer);
            setResult(RESURT_CODE);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
