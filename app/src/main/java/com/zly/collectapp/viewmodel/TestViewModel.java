package com.zly.collectapp.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zly.collectapp.utils.NumUtils;

public class TestViewModel extends ViewModel {
    private MutableLiveData<Integer> nums=new MutableLiveData<>();

    public void add(int num){
        nums.setValue(num);
        NumUtils numUtils = NumUtils.getInstance();
        numUtils.createNum(nums.getValue());
        numUtils.star(new Handler());
    }
}
