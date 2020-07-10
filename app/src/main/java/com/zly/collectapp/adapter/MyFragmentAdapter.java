package com.zly.collectapp.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] titles=new String[10];
    private FragmentManager mfm;

    public MyFragmentAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<Fragment> fragments,String[] titles) {
        super(fm, behavior);
        this.mfm=fm;
        this.fragments=fragments;
        this.titles=titles;
    }


    /**
     * 每一个单独的窗口
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
//        if (position==1){
//            return new TitleFragment();
//        }else if (position==2){
//            return new MeFragment();
//        }
//        return new ImgListFragment();
    }

    /**
     * 获取窗口数量
     * @return
     */
    @Override
    public int getCount() {
        return titles.length;
    }

    /**
     * 每一个子项的描述
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
