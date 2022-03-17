package com.audioplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class PlayBackService extends Service implements IPlayBack, IPlayBack.Callback {



    private String currentPalyMode = ServiceConstant.LOOP_PLAYBACK;

    private RemoteViews mContentViewBig, mContentViewSmall;

    private static final int NOTIFICATION_ID = 1;

    private Player mPlayer;

    private final Binder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {

        public PlayBackService getService() {
            return PlayBackService.this;
        }
    }

    /**
     * Service 的生命周期的 重写方法
     */
    @Override
    public void onCreate() {
        mPlayer = Player.getInstance();
        mPlayer.registerCallback(this);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {  //bindService 回调
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  //startService 回调
        if (intent != null) {
            String action = intent.getAction();
            if (ServiceConstant.ACTION_PLAY_TOGGLE.equals(action)) {
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
            } else if (ServiceConstant.ACTION_STOP_SERVICE.equals(action)) {
                if (isPlaying()) {
                    pause();
                }
                stopForeground(true);
                unregisterCallback(this);
            }
        }
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("onUnbind---", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.i("stopService---", "stopService");
        stopForeground(true);
        unregisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        Log.i("onDestroy---", "onDestroy");
        releasePlayer();
        super.onDestroy();
    }

    // MediaPlayer 的生命周期的回调监听
    @Override
    public void onComplete(PlayState state) {
        showNotification(state);
    }

    @Override
    public void onPlayStatusChanged(PlayState status) {
        showNotification(status);
    }

    @Override
    public void onPosition(int position) {

    }

    private void showNotification(PlayState status) {
        //The PendingIntent to launch our activity if the user selects this notification

        Intent intent = new Intent();
        intent.setClassName("com.customview", "com.customview.musicplayer.MusicPlayerActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("service", "service", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Set the info for the views that show in the notification panel.
        Notification notification = new NotificationCompat.Builder(this, "service")
                .setSmallIcon(com.audioplayer.R.mipmap.ic_launcher)  // the status icon
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setCustomContentView(getSmallContentView())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)  //设置为ture，表示它为一个正在进行的通知。简单的说，当为ture时，不可以被侧滑消失。
                .build();

        // Send the notification.
        startForeground(NOTIFICATION_ID, notification);
    }

    private RemoteViews getSmallContentView() {
        if (mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(getPackageName(), com.audioplayer.R.layout.remote_view_music_player_small);
            setUpRemoteView(mContentViewSmall);
        }
        updateRemoteViews(mContentViewSmall);
        return mContentViewSmall;
    }

    private RemoteViews getBigContentView() {
        if (mContentViewBig == null) {
            mContentViewBig = new RemoteViews(getPackageName(), com.audioplayer.R.layout.remote_view_music_player);
            setUpRemoteView(mContentViewBig);
        }
        updateRemoteViews(mContentViewBig);
        return mContentViewBig;
    }

    private void setUpRemoteView(RemoteViews remoteView) {
        remoteView.setImageViewResource(com.audioplayer.R.id.image_view_close, com.audioplayer.R.drawable.ic_remote_view_close);
        remoteView.setImageViewResource(com.audioplayer.R.id.image_view_play_last, com.audioplayer.R.drawable.ic_remote_view_play_last);
        remoteView.setImageViewResource(com.audioplayer.R.id.image_view_play_next, com.audioplayer.R.drawable.ic_remote_view_play_next);

        remoteView.setOnClickPendingIntent(com.audioplayer.R.id.button_close, getServicePendingIntent(ServiceConstant.ACTION_STOP_SERVICE));
        remoteView.setOnClickPendingIntent(com.audioplayer.R.id.button_play_last, getServicePendingIntent(ServiceConstant.ACTION_PLAY_LAST));
        remoteView.setOnClickPendingIntent(com.audioplayer.R.id.button_play_next, getServicePendingIntent(ServiceConstant.ACTION_PLAY_NEXT));
        remoteView.setOnClickPendingIntent(com.audioplayer.R.id.button_play_toggle, getServicePendingIntent(ServiceConstant.ACTION_PLAY_TOGGLE));
    }

    private void updateRemoteViews(RemoteViews remoteView) {
        remoteView.setImageViewResource(com.audioplayer.R.id.image_view_play_toggle, isPlaying()
                ? com.audioplayer.R.drawable.ic_remote_view_pause : com.audioplayer.R.drawable.ic_remote_view_play);

    }

    // PendingIntent

    private PendingIntent getServicePendingIntent(String action) {

        return PendingIntent.getService(this, 0, new Intent(action), 0);
    }

    /**
     * 获取当前播放歌曲的路径
     * @return
     */
    public String getSongPath() {
        return mPlayer.getSongPath();
    }

    /**
     *   获取当前播放模式
     * @return
     */
    public String getCurrentPalyMode(){

        return currentPalyMode;
    }

    public void setCurrentPalyMode(String playMode){

        this.currentPalyMode = playMode;
    }

    /**
     * IPlayBack 中定义的 音乐播放器的 规则
     */
    @Override
    public boolean play() {
        return mPlayer.play();
    }

    @Override
    public boolean play(String songPath) {
        return mPlayer.play(songPath);
    }

    @Override
    public boolean pause() {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public boolean seekTo(int progress) {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void registerCallback(Callback callback) {
        mPlayer.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mPlayer.unregisterCallback(callback);
    }

    @Override
    public void removeCallbacks() {
        mPlayer.removeCallbacks();
    }

    @Override
    public void releasePlayer() {
        mPlayer.releasePlayer();
    }
}
