package com.zly.collectapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView ivBack,ivSearch,ivMe;
    private TextView tvBarTitle;

    /**
     * 进行标题初始化
     * @param title
     * @param isBack
     * @param isSearch
     */
    protected void  initNarBar(String title,boolean isBack,boolean isSearch){
        ivBack=findViewById(R.id.icv_back);
        ivSearch=findViewById(R.id.iv_bar_search);
        ivMe=findViewById(R.id.iv_bar_me);
        tvBarTitle=findViewById(R.id.tv_bar_title);
        if (!isBack){
            ivBack.setVisibility(View.GONE);
        }
        if (!isSearch){
            ivSearch.setVisibility(View.GONE);
        }
        tvBarTitle.setText(title);
    }

    public void  initView(){

    }

}
