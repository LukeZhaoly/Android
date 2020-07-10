package com.zly.collectapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... users);


    @Delete
    void delete(User... users);


    @Update
    void update(User... users);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("select * from user where name=:name and password=:pwd")
    User findOneByNameAndPwd(String name,String pwd);

}
