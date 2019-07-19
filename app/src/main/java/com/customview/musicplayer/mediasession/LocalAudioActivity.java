package com.customview.musicplayer.mediasession;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.customview.R;
import com.customview.musicplayer.mediasession.ui.MainActivity;
import com.mediabrowser.xiaxl.client.model.MusicInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 简言 on 2019/2/18  8:18.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.musicplayer
 * Description :
 */
public class LocalAudioActivity extends AppCompatActivity {


    @BindView(R.id.lv_local_audio)
    ListView lvLocalAudio;
    @BindView(R.id.tv_error_local_audio)
    TextView tvErrorLocalAudio;
    @BindView(R.id.pb_loading_local_audio)
    ProgressBar pbLoadingLocalAudio;

    private ArrayList<MusicInfo> mediaItems;
    private LocalVideoAdapter localVideoAdapter;
    private static final int ISDATA = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_local_audio);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    private void initListener() {

        lvLocalAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {

                Intent intent = new Intent(LocalAudioActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                Log.i("LocalAc-music-click", ""+mediaItems.get(0).getMediaId());
                bundle.putSerializable("mediaItems", mediaItems);
                intent.putExtra("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initData() {

        getDataFromLocal();
    }

    private void getDataFromLocal() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                super.run();
                mediaItems = new ArrayList<>();
                //解决安卓6.0以上版本不能读取外部存储权限的问题
                isGrantExternalRW(LocalAudioActivity.this);
                //1.第一步，获得内容分解器
                ContentResolver contentResolver = getContentResolver();
                //第二步，从sdcard中查找视频
                //2.1 需要一个uri
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                //2.2 要查找的信息
                String[] infos = {
                        MediaStore.Audio.Media.DISPLAY_NAME,//音频文件在sdcard的名称
                        MediaStore.Audio.Media.DURATION,//音频总时长
                        MediaStore.Audio.Media.SIZE,//音频的文件大小
                        MediaStore.Audio.Media.DATA,//音频的绝对地址
                        MediaStore.Audio.Media.ARTIST,//歌曲的演唱者
                        MediaStore.Audio.Media.TITLE, //歌曲的名称
                };
                //第三步，查询，并获得结果集
                Cursor cursor = contentResolver.query(uri, infos, null, null, null);
                //3.1 从结果级中将信息读出
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        MusicInfo item = new MusicInfo();
                        mediaItems.add(item);
                        item.setAlbum(cursor.getString(0));
                        item.setDuration(cursor.getLong(1));
                        item.setSize(cursor.getLong(2));
                        item.setMediaId(cursor.getString(3));
                        item.setSource(cursor.getString(3));
                        item.setArtist(cursor.getString(4));
                        item.setTitle(cursor.getString(5));
                        Log.i("item", item.getMediaId());
                    }
                    //3.2 将 游标 关闭 ，以便下一次读取
                    cursor.close();
                }

                //无论cursor是否为空，都要法消息，通知
                mHandler.sendEmptyMessageDelayed(ISDATA, 1000);
            }
        }.start();
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ISDATA:
                    if (mediaItems != null && mediaItems.size() > 0) {
                        //展示列表，隐藏错误页
                        localVideoAdapter = new LocalVideoAdapter(LocalAudioActivity.this, mediaItems, true);
                        lvLocalAudio.setAdapter(localVideoAdapter);
                        tvErrorLocalAudio.setVisibility(View.GONE);
                    } else {
                        //展示错误页
                        tvErrorLocalAudio.setText("什么都没有找到呐！");
                        tvErrorLocalAudio.setVisibility(View.VISIBLE);
                    }
                    //隐藏progressBar
                    pbLoadingLocalAudio.setVisibility(View.GONE);
            }
        }
    };

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
}
