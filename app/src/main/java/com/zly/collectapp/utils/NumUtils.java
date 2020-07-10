package com.zly.collectapp.utils;

import android.os.Handler;
import android.util.Log;

public class NumUtils {
    private static  final  String TAG="NumUtils";

    private static NumUtils numUtils;

    private boolean isCancel;
    private long lastTime;
    private int num;

    private int i=2;

    public static NumUtils getInstance() {
        if (numUtils==null){
            numUtils=new NumUtils();
        }
        return numUtils;
    }

    public void createNum(int num){
        this.num=num;
    }

    public synchronized void star(final Handler handler){
        if (i<0){
            return;
        }
        i--;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,400);
                long currentTime = System.currentTimeMillis();
                Log.e(TAG, "currentTime"+currentTime+",num="+num );
                if (currentTime-lastTime>400){
                     lastTime = System.currentTimeMillis();
                     num++;
                     Log.e(TAG, "lastTime"+lastTime+",num="+num );
                }
            }
        };
        handler.post(runnable);
    }
}
