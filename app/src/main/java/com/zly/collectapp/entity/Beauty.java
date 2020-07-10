package com.zly.collectapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Beauty  implements Parcelable {

    @NonNull
    @PrimaryKey
    String _id;
    String url;
    String type;
    String category;
    @ColumnInfo(name = "like_counts")
    int likeCounts;

    public Beauty() {
    }

    protected Beauty(Parcel in) {
        _id = in.readString();
        url = in.readString();
        type = in.readString();
        category = in.readString();
        likeCounts = in.readInt();
    }

    public static final Creator<Beauty> CREATOR = new Creator<Beauty>() {
        @Override
        public Beauty createFromParcel(Parcel in) {
            return new Beauty(in);
        }

        @Override
        public Beauty[] newArray(int size) {
            return new Beauty[size];
        }
    };

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    @Override
    public String toString() {
        return "Beauty{" +
                "id='" + _id + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", likeCounts=" + likeCounts +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(url);
        dest.writeString(type);
        dest.writeString(category);
        dest.writeInt(likeCounts);
    }
}
