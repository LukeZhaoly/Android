package com.zly.collectapp.service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.zly.collectapp.MainActivity;
import com.zly.collectapp.R;
import com.zly.collectapp.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 前台服务，调用通知栏进行显示
 */
public class DownloadService extends Service {
    private static final String CHANNEL_ID="img_down";


    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    private Notification getNotification(String title){
        String id="down_1";
        String name = "download service";
        NotificationChannel channel= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(id,name, NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager().createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,id);
        builder.setSmallIcon(R.mipmap.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
        builder.setContentTitle(title);
        return builder.build();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,getNotification("下载开始"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url=intent.getStringExtra("url");
        new DownloadTask().execute(url);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  class DownloadTask extends AsyncTask<String,Void,Void> {

        private String fileName;
        private String dirPath;
        /**
         * 执行下载逻辑
         * @param strings
         * @return
         */

        @Override
        protected Void doInBackground(String... strings) {
            final String downloadUrl=strings[0];
            //文件名字
            fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //文件地址
            dirPath = Environment.getDataDirectory() + "/data/com.zly.collectapp/files/";
            OkHttpUtils.sendHttpRequest(downloadUrl, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        File file=new File(dirPath+fileName);
                        if (!file.exists()){
                            file.createNewFile();
                        }
                        FileOutputStream out=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                        out.flush();
                        out.close();
                    }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent=new Intent();
            intent.setAction(MainActivity.DOWNLOAD_MSG);
            intent.putExtra("isOk",true);
            intent.putExtra("fileUrl",dirPath+fileName);
            sendBroadcast(intent);
            stopSelf();
        }
    }

}
