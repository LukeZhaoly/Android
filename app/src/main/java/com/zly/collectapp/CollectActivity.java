package com.zly.collectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zly.collectapp.fragment.ImgListFragment;
import com.zly.collectapp.fragment.TitleFragment;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        long star=System.currentTimeMillis();
        initView();
        long endTime=System.currentTimeMillis();
        Log.e("CollectActivity", "onCreateView: "+(endTime-star));
    }

    private void initView(){
        int flags = getIntent().getIntExtra("flags", 0);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        Fragment fragment=null;
        switch (flags){
            case 1:
                fragment=new TitleFragment();
                break;
            case 2:
                fragment=new ImgListFragment();
                break;
        }
        bundle.putString("type","collect");
        if (fragment!=null){
            fragment.setArguments(bundle);
            transaction.replace(R.id.collect,fragment);
            transaction.commit();
        }
    }


}
