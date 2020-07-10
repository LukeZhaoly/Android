package com.zly.collectapp.utils;


import android.content.Context;
import android.widget.TextView;

import io.noties.markwon.Markwon;
import io.noties.markwon.image.glide.GlideImagesPlugin;

/**
 * mark转换工具
 */
public class MarkdownUtils {

    private static volatile MarkdownUtils mInstance;

    private  Markwon markwon;


    public static synchronized MarkdownUtils getInstance(Context context) {
        if (mInstance==null){
            mInstance=new MarkdownUtils();
        }
        mInstance.setRes(context);
        return mInstance;
    }



    private void setRes(Context context) {
        markwon=Markwon.builder(context).usePlugin(GlideImagesPlugin.create(context)).build();
    }


    public boolean converMarkToText(TextView textView,String value){
       markwon.setMarkdown(textView,value);
       return true;
    }


}
