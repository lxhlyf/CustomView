package com.audioplayer;

/**
 * Created by 简言 on 2019/2/17.
 * 努力吧 ！ 少年 ！
 *
 *  Description : 用于定义音乐播放的规则
 */
public interface IPlayBack {

    boolean play();

    boolean play(String songPath);

    boolean pause();

    boolean isPlaying();

    //得到播放的进度
    int getCurrentPosition();

    /**
     *  得到播放的时长
     * @return
     */
    int getDuration();

    boolean seekTo(int progress);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback {

        void onComplete(PlayState state);

        void onPlayStatusChanged(PlayState status);

        void onPosition(int position);
    }
}
