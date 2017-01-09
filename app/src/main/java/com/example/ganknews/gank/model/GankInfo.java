package com.example.ganknews.gank.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by zhooker on 2016/10/11.
 */
@Entity
public class GankInfo implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @SerializedName("_id")
    private String guid;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private String used;
    private String who;

    @Override
    public String toString() {
        return "GankInfo{" +
                "id=" + id +
                ", guid='" + guid +
                ", who='" + who +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(guid);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(used);
        dest.writeString(who);
    }

    //实现反序列化，从Parcel解析出来
    public static final Parcelable.Creator<GankInfo> CREATOR = new Creator<GankInfo>() {

        @Override
        public GankInfo[] newArray(int size) {
            return new GankInfo[size];
        }

        @Override
        public GankInfo createFromParcel(Parcel source) {
            GankInfo info = new GankInfo();
            info.setGuid(source.readString());
            info.setCreatedAt(source.readString());
            info.setDesc(source.readString());
            info.setPublishedAt(source.readString());
            info.setType(source.readString());
            info.setUrl(source.readString());
            info.setUsed(source.readString());
            info.setWho(source.readString());
            return info;
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsed() {
        return this.used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Generated(hash = 1445507222)
    public GankInfo(Long id, String guid, String createdAt, String desc, String publishedAt,
            String source, String type, String url, String used, String who) {
        this.id = id;
        this.guid = guid;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    @Generated(hash = 1682206697)
    public GankInfo() {
    }
}

