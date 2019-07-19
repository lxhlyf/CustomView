package com.audioplayer;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 简言 on 2019/2/17.
 * 努力吧 ！ 少年 ！
 *
 * Description :  封装了MediaPlayer
 */
public class Player implements IPlayBack, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {

    private static volatile Player sInstance;
    private MediaPlayer mPlayer;
    private List<Callback> mCallbacks = new ArrayList<>(2);
    private boolean isPaused;
    private String songPath;

    private Player() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnPreparedListener(this); //注册异步监听器
        mPlayer.setOnBufferingUpdateListener(this); //缓冲的监听器
    }

    public static Player getInstance() {
        if (sInstance == null) {
            synchronized (Player.class) {
                if (sInstance == null) {
                    sInstance = new Player();
                }
            }
        }
        return sInstance;
    }

    /**
     *   IPlayBack 接口中定义的规则
     */
    @Override
    public boolean play() {
        if (isPaused){
            mPlayer.start();
            notifyPlayStatusChanged(PlayState.PLAYING);
            return true;
        }
        return false;
    }

    @Override
    public boolean play(String songPath) {
        try {
            mPlayer.reset();
            mPlayer.setDataSource(songPath);
            mPlayer.prepareAsync();
            this.songPath = songPath;
            notifyPlayStatusChanged(PlayState.PLAYING);
            return true;
        } catch (IOException e) {
            notifyPlayStatusChanged(PlayState.ERROR);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            isPaused = true;
            notifyPlayStatusChanged(PlayState.PAUSE);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
          if (mPlayer == null) return false;
        return mPlayer.isPlaying();
    }

    @Override
    public int getCurrentPosition() { //得到当前播放的位置
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() { //得到音乐总时长
        return mPlayer.getDuration();
    }

    @Override
    public boolean seekTo(int progress) {
        try {
            mPlayer.seekTo(progress);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    @Override
    public void releasePlayer() {
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        sInstance = null;
        songPath = null;
    }

    /**
     *  本类自定义方法
     * @param status
     */
    private void notifyPlayStatusChanged(PlayState status) {
        if (mCallbacks.size() > 0){
            for (Callback callback : mCallbacks) {
                callback.onPlayStatusChanged(status);
            }
        }
    }
    private void notifyComplete(PlayState state) {
        if (mCallbacks.size() > 0){
            for (Callback callback : mCallbacks) {
                callback.onComplete(state);
            }
        }
    }

    public String getSongPath() {
        if (songPath != null){
            return songPath;
        }
        return "";
    }


    /**
     *  以下为 MediaPlayer 的监听器
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i("onCompletion---","onCompletion");
        mPlayer.reset();
        notifyComplete(PlayState.COMPLETE);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i("onPrepared---", "onPrepared");
        mPlayer.start();
        notifyPlayStatusChanged(PlayState.PLAYING);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
       //定义缓冲
    }
}
