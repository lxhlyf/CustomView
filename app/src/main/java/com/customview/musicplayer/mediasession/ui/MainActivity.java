/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.customview.musicplayer.mediasession.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.customview.R;
import com.mediabrowser.xiaxl.client.model.MusicInfo;
import com.mediabrowser.xiaxl.client.MusicManager;
import com.mediabrowser.xiaxl.client.listener.OnSaveRecordListener;
import com.mediabrowser.xiaxl.setting.SettingConfig;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 1.客户端需要实现MediaBrowser,并且需要connect
 *         1.1 MediaBrowser.ConnectionCallback,
 *         1.2 当连接成功的时候需要注册控制面板，并注册MediaController.Callback,   //当在Service中设置Token的时候被触发
 *         1.3 并实现订阅MediaBrowser.SubscriptionCallback的注册  //挡在1.2中订阅的时候被处罚
 *         1.4 在订阅回调中，我们能够获取到数据  onChildrenLoaded()
 * 2.service中我们需要做的事情：
 *         2.1 Service要继承 MediaBrowserServiceCompat 会重写onGetResult和onLoadChildren这俩个方法
 *               2.1.1 在onGetResult   Browser 和 BrowserService的订阅关系
 *               2.1.2 在onLoadChildren中， 进行网络或者本地的数据加载(异步)
 *         2.2 需要在service初始化的时候就完成MediaSession的构建过程，并为它设置相应的标志，状态等
 *               2.2.1 设置状态   PlaybackState.STATE_PLAYING
 *               2.2.2 设置MediaSession.Callback的回调  作用是:响应控制器指令的回调
 *               2.2.3 设置标志
 *         2.3 设置Token后触发MediaBrowserCompat.ConnectionCallback回调方法，表示MediaBrowser与MediaBrowserService连接成功
 */
public class MainActivity extends Activity {


    /**
     * UI
     */
    // 歌曲标题
    private TextView mTitleTv;
    // 歌曲作者
    private TextView mArtistTv;
    // 歌曲图片
    private ImageView mAlbumArtImg;
    // 播放控制器 背景
    private View mControlBgLayout;

    // seekbar
    private MediaSeekBar mSeekBarAudio;

    /**
     * 数据
     */
    // 是否正在播放的标识
    private boolean mIsPlaying;
    // 音频数据
    ArrayList<MusicInfo> mMusicInfos;

    /**
     *
     */
    MusicManager mMusicManager = null;

    private  ArrayList<MusicInfo> mediaItems;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mediaItems = (ArrayList<MusicInfo>) getIntent().getSerializableExtra("mediaItems");
        position = getIntent().getIntExtra("position", 0);

        Log.i("MainActivity-music-onCr", ""+mediaItems.get(0).getMediaId());

        // 初始化音乐引擎
        initMusicAgent();
        // 添加音频数据
        //initData();
        // 初始化UI
        initUI();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        if (mMusicManager != null) {
            mMusicManager.removeAudioStateListener(mAudioStatusChangeListener);
        }
    }

    /**
     * 初始化音乐引擎
     */
    private void initMusicAgent() {
        // 初始化
        if (mMusicManager == null) {
            mMusicManager = MusicManager.getInstance();
        }
        mMusicManager.init(this);
        // 音频变化的监听类
        mMusicManager.addOnAudioStatusListener(mAudioStatusChangeListener);
        // 记录播放记录的监听
        mMusicManager.addOnRecorListener(mOnRecordListener);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        // 允许GPRS播放
        SettingConfig.setGPRSPlayAllowed(this, true);
        // 添加音频数据
        mMusicInfos.add(new MusicInfo());

    }


    /**
     * 初始化UI
     */
    private void initUI() {
        // 歌曲标题
        mTitleTv = (TextView) findViewById(R.id.song_title_tv);
        // 歌曲作者
        mArtistTv = (TextView) findViewById(R.id.song_artist_tv);
        // 歌曲图片
        mAlbumArtImg = (ImageView) findViewById(R.id.album_art_img);
        // 播放控制器背景
        mControlBgLayout = findViewById(R.id.control_bg_layout);

        // 上一首
        final Button previousBtn = (Button) findViewById(R.id.previous_btn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusicManager != null) {
                    mMusicManager.skipToPrevious();
                }
            }
        });
        // 播放按钮
        final Button playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying) {
                    if (mMusicManager != null) {
                        mMusicManager.pause();
                    }
                } else {
                    if (mMusicManager != null) {
                        Log.i("MainActivity-music", ""+mediaItems.get(0).getMediaId());
                        mMusicManager.playMusicList(mediaItems, position);
                    }
                }
            }
        });
        // 下一首
        final Button nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusicManager != null) {
                    mMusicManager.skipToNext();
                }
            }
        });
        // seekbar
        mSeekBarAudio = (MediaSeekBar) findViewById(R.id.seekbar_audio);
        mSeekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // seek
                if (mMusicManager != null) {
                    mMusicManager.seekTo(seekBar.getProgress());
                }
            }
        });
    }


    /**
     * 更改播放按钮背景状态
     *
     * @param isPlaying
     */
    private void setControlBg(boolean isPlaying) {
        if (isPlaying) {
            mControlBgLayout.setBackgroundResource(R.drawable.ic_media_with_pause);
        } else {
            mControlBgLayout.setBackgroundResource(R.drawable.ic_media_with_play);
        }
    }


    // ############################################################################################


    /**
     * 音频播放状态变化的回调
     *
     * @param playbackState
     */
    private void onMediaPlaybackStateChanged(PlaybackStateCompat playbackState) {
        if (playbackState == null) {
            return;
        }
        // 正在播放
        mIsPlaying =
                playbackState.getState() == PlaybackStateCompat.STATE_PLAYING;

        // 更新UI
        setControlBg(mIsPlaying);

        /**
         * 设置播放进度
         */
        final int progress = (int) playbackState.getPosition();
        mSeekBarAudio.setProgress(progress);
        switch (playbackState.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                final int timeToEnd = (int) ((mSeekBarAudio.getMax() - progress) / playbackState.getPlaybackSpeed());
                mSeekBarAudio.startProgressAnima(progress, mSeekBarAudio.getMax(), timeToEnd);
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                mSeekBarAudio.stopProgressAnima();
                break;

        }

    }


    /**
     * 播放音频数据 发生变化的回调
     *
     * @param mediaMetadata
     */
    private void onMediaMetadataChanged(MediaMetadataCompat mediaMetadata) {
        if (mediaMetadata == null) {
            return;
        }
        // 音频的标题
        mTitleTv.setText(
                mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
        // 音频作者
        mArtistTv.setText(
                mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
        // 音频图片
//        mAlbumArtImg.setImageBitmap(MusicLibrary.getAlbumBitmap(
//                MainActivity.this,
//                mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)));

        // 进度条
        final int max = mediaMetadata != null
                ? (int) mediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
                : 0;
        mSeekBarAudio.setProgress(0);
        mSeekBarAudio.setMax(max);
    }

    // ############################################################################################


    /**
     * 音频变化回调
     */
    MusicManager.OnAudioStatusChangeListener mAudioStatusChangeListener = new MusicManager.OnAudioStatusChangeListener() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            // 播放音频 状态变化
            onMediaPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            // 播放音频变化的回调
            onMediaMetadataChanged(metadata);
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            // TODO 播放队列发生变化
        }
    };

    /**
     * 记录播放位置的回调
     */
    OnSaveRecordListener mOnRecordListener = new OnSaveRecordListener() {
        @Override
        public void onSaveRecord(MediaMetadataCompat mediaMetadataCompat, long postion) {
            // TODO 保存播放记录用
        }
    };


}