package com.customview.musicplayer.mediasession;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.customview.R;
import com.customview.musicplayer.localaudio.Utils;
import com.mediabrowser.xiaxl.client.model.MusicInfo;

import java.util.List;

/**
 * Created by 简言 on 2018/10/26.
 * 努力吧 ！ 少年 ！
 */

public class LocalVideoAdapter extends BaseAdapter{

    private final boolean isAudio;
    private Context context;
    private List<MusicInfo> mediaItems;
    private Utils utils;

    public LocalVideoAdapter(Context context, List<MusicInfo> mediaItems, boolean isAudio) {
        this.context = context;
        this.mediaItems = mediaItems;
        utils = new Utils();
        this.isAudio = isAudio;
    }

    @Override
    public int getCount() {
        return mediaItems.size() >= 0 ?  mediaItems.size() : 0;
    }

    @Override
    public MusicInfo getItem(int position) {
        return mediaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null){
            holer = new ViewHoler();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_video, parent, false);
            holer.iv_icon = convertView.findViewById(R.id.iv_icon);
            holer.tv_name = convertView.findViewById(R.id.tv_name);
            holer.tv_time = convertView.findViewById(R.id.tv_time);
            holer.tv_size = convertView.findViewById(R.id.tv_size);
            convertView.setTag(holer);
        }else{
            holer = (ViewHoler) convertView.getTag();
        }
            if (isAudio){
                holer.iv_icon.setImageResource(R.drawable.music_default_bg);
            }
            holer.tv_name.setText(getItem(position).getAlbum());
            holer.tv_size.setText(android.text.format.Formatter.formatFileSize(context, getItem(position).getSize()));
            holer.tv_time.setText(utils.stringForTime((int) getItem(position).getDuration()));
        return convertView;
    }

    static class ViewHoler{
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_size;
    }
}
