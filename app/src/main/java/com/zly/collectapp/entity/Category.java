package com.zly.collectapp.entity;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;


public class Category implements Parcelable {

   private String _id;
   private String title;
   private String type;
   private String coverImageUrl;
   private String desc;

    protected Category(Parcel in) {
        _id = in.readString();
        title = in.readString();
        type = in.readString();
        coverImageUrl = in.readString();
        desc = in.readString();
    }
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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
        dest.writeString(type);
        dest.writeString(coverImageUrl);
        dest.writeString(desc);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
