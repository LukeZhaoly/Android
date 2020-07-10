package com.zly.collectapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User  implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String password;
    @Ignore
    List<Beauty> beautyList;
    @Ignore
    List<Article> articleList;

    public User(){

    }

    protected User(Parcel in) {
        id=in.readInt();
        name=in.readString();
        password=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
          dest.writeInt(id);
          dest.writeString(name);
          dest.writeString(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<Beauty> getBeautyList() {
        return beautyList;
    }

    public void setBeautyList(List<Beauty> beautyList) {
        this.beautyList = beautyList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
