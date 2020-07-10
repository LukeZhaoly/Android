package com.zly.collectapp.entity;

import android.nfc.tech.NfcA;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

@Entity
public class Article implements Parcelable {
    @NonNull
    @PrimaryKey
    String _id;
    String title;
    String content;
    @ColumnInfo(name = "publish_at")
    String publishAt;
    String author;
    String desc;
    String images;

    public Article() {
    }


    protected Article(Parcel in) {
        _id = in.readString();
        title = in.readString();
        content = in.readString();
        publishAt = in.readString();
        author = in.readString();
        desc = in.readString();
        images = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(publishAt);
        dest.writeString(author);
        dest.writeString(desc);
        dest.writeString(images);
    }

    @Override
    public String toString() {
        return "Article{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", images='" + images + '\'' +
                '}';
    }

    @NonNull
    public String get_id() {
        return _id;
    }

    @JSONField(name = "_id")
    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }
    @JSONField(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishAt() {
        return publishAt;
    }
    @JSONField(name = "publishedAt")
    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getAuthor() {
        return author;
    }
    @JSONField(name = "author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }
    @JSONField(name = "desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImages() {
        return images;
    }
    @JSONField(name = "images")
    public void setImages(String images) {
        this.images = images;
    }
}

