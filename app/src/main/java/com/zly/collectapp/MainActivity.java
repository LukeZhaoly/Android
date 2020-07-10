package com.zly.collectapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.zly.collectapp.adapter.MyFragmentAdapter;
import com.zly.collectapp.entity.Article;
import com.zly.collectapp.fragment.ImgListFragment;
import com.zly.collectapp.fragment.MeFragment;
import com.zly.collectapp.fragment.TitleFragment;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity {
    private TabLayout tabBtn;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private ViewPager vpMain;
    private String[] titles=new String[]{"文章鉴赏","图片欣赏","个人中心"};
    private MyFragmentAdapter fa;
    private ArrayList<Fragment> fragments=new ArrayList<>();

    public static final String DOWNLOAD_MSG="download_msg";
    private DownloadMsgReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化视图组件
     */
    public void initView(){
        TitleFragment tf=new TitleFragment();
        fragments.add(tf);
        ImgListFragment ilf=new ImgListFragment();
        fragments.add(ilf);
        MeFragment mf=new MeFragment();
        fragments.add(mf);
        tabBtn=findViewById(R.id.tab_btn);
        vpMain=findViewById(R.id.vp_main);
        fa=new MyFragmentAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments,titles);
        vpMain.setAdapter(fa);
        //TabLayout布局绑定viewPager
        tabBtn=findViewById(R.id.tab_btn);
        tabBtn.setupWithViewPager(vpMain);
        //指定tab位置
        one=tabBtn.getTabAt(0);
        two=tabBtn.getTabAt(1);
        three=tabBtn.getTabAt(2);
        //设置图标
        if (one.isSelected()){
            initNarBar("文章鉴赏",false,true);
        }else if (two.isSelected()){
            initNarBar("图片欣赏",false,false);
        }else {
            initNarBar("个人中心",false,false);
        }
        one.setIcon(R.drawable.beauty);
        two.setIcon(R.drawable.article);
        three.setIcon(R.drawable.me);
        List<String> permissonList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.READ_PHONE_STATE);
        }if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        receiver=new DownloadMsgReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DOWNLOAD_MSG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"拒绝权限,程序将无法使用",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }else {
                    Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    /**
     * 广播接收器
     */
    class DownloadMsgReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isOk = intent.getBooleanExtra("isOk",false);
            if (isOk){
                Toast.makeText(MainActivity.this,"文件地址："+intent.getStringExtra("fileUrl"),Toast.LENGTH_SHORT).show();
            }
        }
    }

}
