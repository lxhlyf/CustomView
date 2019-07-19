package com.customview.musicplayer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.audioplayer.IPlayBack;
import com.audioplayer.PlayBackService;
import com.audioplayer.PlayState;
import com.audioplayer.ServiceConstant;
import com.audioplayer.utils.MyTimeUtils;
import com.customview.R;
import com.customview.musicplayer.localaudio.MediaItem;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 简言 on 2019/2/17  13:35.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.musicplayer
 * Description :
 */
public class MusicPlayerActivity extends AppCompatActivity implements IPlayBack.Callback {

    private PlayBackService service;

    @BindView(R.id.seek_bar)
    AppCompatSeekBar seekBar;
    @BindView(R.id.start_time)
    TextView textViewProgress;
    @BindView(R.id.end_time)
    TextView textViewDuration;
    @BindView(R.id.button_play_toggle)
    AppCompatImageView buttonPlayToggle;
    @BindView(R.id.button_play_last)
    AppCompatImageView buttonPlayLast;
    @BindView(R.id.button_play_next)
    AppCompatImageView getButtonPlayNext;
    @BindView(R.id.btn_audio_playmode)
    Button btnPlayMode;

    private  ArrayList<MediaItem> mediaItems;
    private int position;

    private Unbinder bind;

    String songPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "jiewo.mp3";

    /**
     *  Activity 的生命周期方法
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        bind = ButterKnife.bind(this);

        mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("mediaItems");
        position = getIntent().getIntExtra("position", 0);

        bindService(new Intent(this, PlayBackService.class), conn, Service.BIND_AUTO_CREATE);

        initListener();
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        bind.unbind();
        super.onDestroy();
    }

    /**
     * 设置 的 activity 视图内部的监听器
     */

    private void initListener() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //手指触摸的监听器

                cancelTimer(); //当手指触摸的时候，取消时间器
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { //手指停止触摸的监听器

                service.seekTo(getSeekDuration(seekBar.getProgress()));
                playTimer(); //开启时间器
            }
        });
    }

    /**
     *  绑定 Service
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            service = ((PlayBackService.LocalBinder) iBinder).getService();
            service.play(mediaItems.get(position).getData());
            //service.play(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "jiewo.mp3");
            service.registerCallback(MusicPlayerActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            if (service != null){
                service.unregisterCallback(MusicPlayerActivity.this);
            }
        }
    };

    // 开始和暂停
    @OnClick(R.id.button_play_toggle)
    public void onClick() {
        if (service == null || songPath == null) {
            Log.d("mPlaybackService","mPlaybackService == null");
            return;
        }
        if (service.isPlaying()) {
            if (songPath.equals(service.getSongPath())) {
                service.pause();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
            } else {
                service.play(songPath);
                buttonPlayToggle.setImageResource(R.drawable.ic_pause);
            }
        } else {
            if (songPath.equals(service.getSongPath())) {
                service.play();
            } else {
                service.play(songPath);
            }
            buttonPlayToggle.setImageResource(R.drawable.ic_pause);
        }
    }

    @OnClick({R.id.button_play_last, R.id.button_play_next})
    public void lastNext(View view){

        switch (view.getId()){
            case R.id.button_play_last:
                if (mediaItems != null && mediaItems.size() > 0 && position >= 0){
                    position -= position;
                    if (position >= 0){
                        service.play(mediaItems.get(position).getData());
                    }else {

                    Toast.makeText(this, "已播放到第一首歌", Toast.LENGTH_LONG).show();
                }
                }
                break;
            case R.id.button_play_next:
                if (mediaItems != null && mediaItems.size() > 0 && position >= 0){
                    position += 1;
                    if (position <= mediaItems.size() - 1){
                        service.play(mediaItems.get(position).getData());
                    }else{

                        Toast.makeText(this, "已播放到最后一首歌", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @OnClick(R.id.btn_audio_playmode)
    public void playModeControlor(View view){

        if (service.getCurrentPalyMode().equals(ServiceConstant.LOOP_PLAYBACK)){ //顺序播放
            service.setCurrentPalyMode(ServiceConstant.RANDOM_PLAY);
            btnPlayMode.setBackgroundResource(R.drawable.ic_shuffle_black_24dp);
        }else if (service.getCurrentPalyMode().equals(ServiceConstant.RANDOM_PLAY)){ //随机播放
            service.setCurrentPalyMode(ServiceConstant.SINGLE_PLAY);
            btnPlayMode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
        }else { // 单曲循环
            service.setCurrentPalyMode(ServiceConstant.LOOP_PLAYBACK);
            btnPlayMode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
        }
    }


    /**
     *  activity内部 自定义实现的方法
     */
    private int getSeekDuration(int progress) {
        return (int) (getCurrentSongDuration() * ((float) progress / seekBar.getMax()));
    }
    private int getCurrentSongDuration() {
        int duration=0;
        if (service != null){
            duration = service.getDuration();
        }
        return duration;
    }


    /**
     * MediaPlayer 播放一首歌完整的生命周期
     */
    @Override
    public void onComplete(PlayState state) {

        cancelTimer();

        if (state.equals(PlayState.COMPLETE)){

            if (service.getCurrentPalyMode().equals(ServiceConstant.LOOP_PLAYBACK)){//顺序播放

                if (mediaItems != null && mediaItems.size() > 0 && position >= 0){
                    position += 1;
                    if (position <= mediaItems.size() - 1){
                        service.play(mediaItems.get(position).getData());
                    }else{

                        Toast.makeText(this, "已播放到最后一首歌", Toast.LENGTH_LONG).show();
                    }
                }
            }else if (service.getCurrentPalyMode().equals(ServiceConstant.RANDOM_PLAY)){//随机播放

                if (mediaItems != null && mediaItems.size() > 0 && position >= 0){
                    Random random = new Random();
                    position = random.nextInt(mediaItems.size());
                    if (position>=0 && position<= mediaItems.size()-1){
                        service.play(mediaItems.get(position).getData());
                    }
                }

            }else { //单曲循环
                service.play(mediaItems.get(position).getData());
            }
        }
    }

    @Override
    public void onPlayStatusChanged(PlayState status) {
        switch (status) {
            case INIT:
                break;
            case PREPAREIGG:
                break;
            case PLAYING:
                updateDuration();
                playTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_pause);
                Log.d("mPlaybackService", ""+service.getDuration());
                break;
            case PAUSE:
                cancelTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
                break;
            case ERROR:
                break;
            case COMPLETE:
                cancelTimer();
                buttonPlayToggle.setImageResource(R.drawable.ic_play);
                seekBar.setProgress(0);
                break;
        }

    }

    @Override
    public void onPosition(int position) {  //service 没调

    }

    /**
     *  实现一个定时器， 动态的更新 seekbar 等
     */
    Timer timer = null; //timer 不能用于处理耗时任务 ， 耗时用alarm

    private void playTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (service == null)
                    return;
                if (service.isPlaying()) {
                    handleProgress.post(runnable);
                }
            }
        }, 0, 1000);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (service.isPlaying()) {
                if (isFinishing()){ //如果activity 被finish掉了，就直接返回那么
                    return;
                }
                int progress = (int) (seekBar.getMax()
                        * ((float) service.getCurrentPosition() / (float) service.getDuration()));
                updateProgressTextWithProgress(service.getCurrentPosition());
                if (progress >= 0 && progress <= seekBar.getMax()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(progress, true);
                    } else {
                        seekBar.setProgress(progress);
                    }
                }
            }
        }
    };
    Handler handleProgress = new Handler();

    private void updateProgressTextWithProgress(int progress) {
        textViewProgress.setText(MyTimeUtils.formatDuration(progress));
    }
    private void updateDuration() {
        textViewDuration.setText(MyTimeUtils.formatDuration(service.getDuration()));
    }

}
