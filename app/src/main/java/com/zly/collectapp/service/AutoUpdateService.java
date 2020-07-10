package com.zly.collectapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.zly.collectapp.entity.Article;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 进行自动更新的服务类
 */
public class AutoUpdateService extends Service {

    private String url="https://gank.io/api/v2/data/category/GanHuo/type/Android/page/";
    private int page=1;
    private int count=10;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateBeauty();
        updateArticle();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=60*60*1000*5; //每隔5小时进行查询数据更新
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi= PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 进行文章的更新
     */
    private void updateArticle() {
        String articleUrl="https://gank.io/api/v2/data/category/GanHuo/type/Android/page/"+page+"/count/"+count;

        OkHttpUtils.sendHttpRequest(articleUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("updateArticle",responseText);
                List<Article> articles = JsonToModel.getTitleList(responseText);
            }
        });
    }



    /**
     * 进行图片的更新
     */
    private void updateBeauty() {
        String beautyUrl="https://gank.io/api/v2/data/category/Girl/type/Girl/page/2/count/10";
        OkHttpUtils.sendHttpRequest(beautyUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                JsonToModel.getBeauties(responseText);
                Log.d("updateBeauty",responseText);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
