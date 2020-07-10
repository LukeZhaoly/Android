package com.zly.collectapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zly.collectapp.entity.ArticleCollect;

@Dao
public interface ArticleCollectDao {
    @Insert
    void insert(ArticleCollect...articleCollects);
    @Delete
    void delete(ArticleCollect... articleCollects);
    @Update
    void update(ArticleCollect... articleCollects);
    @Query("select * from articlecollect where user_id=:userId and article_id=:id")
    ArticleCollect getOne(int userId, String id);
}
