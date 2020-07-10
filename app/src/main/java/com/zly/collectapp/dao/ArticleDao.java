package com.zly.collectapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.zly.collectapp.entity.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Article> articles);


    @Delete
    void delete(Article... articles);


    @Update
    void update(Article... articles);

    @Query("SELECT * FROM article limit :size offset :star")
    List<Article> getAllArtilces(int star,int size);

    //根据主键查询香瓜的内容
    @Query("select * from article where _id=:id")
    Article getArticle(String id);

    //更改内容
    @Query("update article set content=:content where _id=:id")
    void updateContent(String id,String content);

    @Query("SELECT * FROM article where _id IN (SELECT article_id FROM articlecollect where user_id=:userId)")
    List<Article> getArticlesByUserId(int userId);
}
