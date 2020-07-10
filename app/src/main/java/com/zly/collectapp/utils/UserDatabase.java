package com.zly.collectapp.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zly.collectapp.dao.ArticleCollectDao;
import com.zly.collectapp.dao.ArticleDao;
import com.zly.collectapp.dao.BeautyCollectDao;
import com.zly.collectapp.dao.BeautyDao;
import com.zly.collectapp.dao.UserDao;
import com.zly.collectapp.entity.Article;
import com.zly.collectapp.entity.ArticleCollect;
import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.entity.BeautyCollect;
import com.zly.collectapp.entity.User;

/**
 * 自定义Room数据库操作
 */
@Database(entities = {User.class, Article.class, Beauty.class,ArticleCollect.class, BeautyCollect.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "ImageInfo.db";

    private static volatile UserDatabase instance;

    /**
     * 单例模式，全局唯一实例
     * @param context
     * @return
     */
    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    /**
     * 进行room数据库的构建
     * @param context
     * @return
     */
    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    /**
     * 引入抽象的dao方法
     * @return
     */
    public abstract UserDao getUserDao();
    public abstract ArticleDao getArticleDao();
    public abstract BeautyDao getBeautyDao();
    public abstract BeautyCollectDao getBeautyCollectDao();
    public abstract ArticleCollectDao getArticleCollectDao();
}
