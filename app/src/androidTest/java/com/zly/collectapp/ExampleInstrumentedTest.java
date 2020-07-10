package com.zly.collectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.zly.collectapp.entity.Article;
import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.entity.User;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<User> users = UserDatabase.getInstance(appContext).getUserDao().getAllUsers();
        for (User user:users) {
            Log.e("userall", "useAppContext: "+user.toString() );
        }
        List<Beauty> beauties = UserDatabase.getInstance(appContext).getBeautyDao().getBeautiesByUserId(1);

        Log.e("user1","1");
        for (Beauty user:beauties) {
            Log.e("user",user.toString());
        }
    }




}
