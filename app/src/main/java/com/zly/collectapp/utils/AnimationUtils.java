package com.zly.collectapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.zly.collectapp.R;

/**
 * 动画相关
 */
public class AnimationUtils {

    private int fps=40;//时间间隔

    private int resId= R.array.sleep_moon;

    private  static volatile AnimationUtils mInstance;
    private Context mContext;

    public synchronized static   AnimationUtils getInstance(int resId, int fps, Context context){
        if (mInstance==null){
            mInstance=new AnimationUtils();
        }
        mInstance.setRes(resId,fps,context);
        return mInstance;
    }

    private void setRes(int resId, int fps,Context context) {
        this.resId=resId;
        this.fps=fps;
        this.mContext=context;
    }

    /**
     * 获取资源数组
     */
    private int[] getData(){
        TypedArray array=mContext.getResources().obtainTypedArray(resId);
        int length=array.length();
        int[] data=new int[length];
        for (int i = 0; i <length ; i++) {
            data[i]=array.getResourceId(i,0);
        }
        array.recycle();
        return data;
    }

    public FrameAnimation createFrame(ImageView imageView){
        return new FrameAnimation(imageView,fps);
    }

    /**
     * 帧数组动画播放
     */
    public class FrameAnimation{
        private int delay;
        private boolean isRunning;
        private ImageView imageView;
        private AnimationDrawable ad=new AnimationDrawable();

        public FrameAnimation(ImageView imageView,int fps){
            this.delay=fps;
            this.imageView=imageView;
        }

        public void star(){
            if (isRunning)
                return;
            int[] res=getData();
            for (int i = 0; i <res.length ; i++) {
                Drawable drawable = mContext.getResources().getDrawable(res[i]);
                ad.addFrame(drawable,delay);
            }
            ad.setOneShot(false);
            imageView.setBackground(ad);
            ad.start();
            isRunning=true;
        }

        public void stop(){
            if (ad!=null && ad.isRunning()){
                ad.stop();
                isRunning=false;
            }
        }
    }


}
