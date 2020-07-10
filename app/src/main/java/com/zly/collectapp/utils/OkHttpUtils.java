package com.zly.collectapp.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp工具类
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClient;


    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient==null){
            synchronized (OkHttpClient.class){
                okHttpClient=new OkHttpClient();
            }
        }
        return okHttpClient;
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static void sendHttpRequest(String url, Callback callback)  {
        Request request=new Request.Builder().url(url).build();
        getOkHttpClient().newCall(request).enqueue(callback);
    }


}
