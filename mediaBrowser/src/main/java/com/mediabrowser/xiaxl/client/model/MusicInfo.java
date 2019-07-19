package com.mediabrowser.xiaxl.client.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MusicInfo implements Serializable {

    private long size;
    private String mediaId;  // 音频id
    private String Source; //播放地址
    private String artUrl; //音频封面
    private String title;   //音频名称
    private String description;   //音频描述
    private String artist;  //作者
    private String album;    //合集名称
    private String albumArtUrl;   //合集封面
    private String genre;   //歌曲风格
    private String freeType;   // 类型：付费 or 免费
    private long duration;  // 返回 ms 数

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getArtUrl() {
        return artUrl;
    }

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumArtUrl() {
        return albumArtUrl;
    }

    public void setAlbumArtUrl(String albumArtUrl) {
        this.albumArtUrl = albumArtUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFreeType() {
        return freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

     public MusicInfo(){

     }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
