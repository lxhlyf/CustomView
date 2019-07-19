package com.customview.musicplayer.localaudio;


import java.io.Serializable;

/**
 * Created by 简言 on 2018/10/26.
 * 努力吧 ！ 少年 ！
 */

public class MediaItem implements Serializable {

    //sdcard
    private String displayName;//视频文件在sdcard的名称
    private long duration;   //视频总时长
    private long size;    //视频的文件大小
    private String data;    //视频的绝对地址, 来自网络，或者本地视频的播放路径
    private String artist;  //歌曲的演唱者

    //net
    private String id;
    private String movieName;
    private String coverImg;
    private String url;
    private String videoTitle;
    private String videoLength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "displayName='" + displayName + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                ", id='" + id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", url='" + url + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoLength='" + videoLength + '\'' +
                '}';
    }
}
