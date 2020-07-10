package com.zly.collectapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zly.collectapp.entity.Beauty;

import java.util.List;

@Dao
public interface BeautyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Beauty> beauties);


    @Delete
    void delete(Beauty... beauties);


    @Update
    void update(Beauty... beauties);

    @Query("SELECT * FROM beauty limit :size offset :star")
    List<Beauty> getBeauties(int star,int size);

    //查询出用户所有收藏的图片
    @Query("SELECT * FROM beauty where _id IN (SELECT beauty_id FROM beautycollect where user_id=:userId)")
    List<Beauty> getBeautiesByUserId(int userId);

}
