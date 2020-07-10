package com.zly.collectapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.zly.collectapp.entity.BeautyCollect;

@Dao
public interface BeautyCollectDao {
    @Insert
    void insert(BeautyCollect...beautyCollects);
    @Delete
    void delete(BeautyCollect... beautyCollects);
    @Update
    void update(BeautyCollect... beautyCollects);
}
