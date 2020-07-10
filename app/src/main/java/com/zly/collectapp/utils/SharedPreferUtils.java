package com.zly.collectapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * SharedPreferences工具类
 */
public class SharedPreferUtils {
    private static final String TAG="SharedPreferUtils";

    private static  SharedPreferUtils mInstance;

    private SharedPreferences sp;

    private SharedPreferences.Editor editor;

    /**
     * 单例
     * @return
     */
    public static SharedPreferUtils getInstance(String name,Context context){
        if (mInstance==null){
            synchronized (SharedPreferUtils.class){
                mInstance=new SharedPreferUtils();
            }
        }
        mInstance.setRes(name,context);
        return mInstance;
    }

    private void setRes(String name,Context context) {
        sp=context.getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    /**
     * 懒加载
     * @return
     */
    public SharedPreferences.Editor getEdit(){
        if (editor==null){
            editor = sp.edit();
        }
        return editor;
    }






}
