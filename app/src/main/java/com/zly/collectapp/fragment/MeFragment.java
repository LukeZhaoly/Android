package com.zly.collectapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zly.collectapp.CollectActivity;
import com.zly.collectapp.R;
import com.zly.collectapp.utils.UserUtils;


public class  MeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static  final String TAG="MeFragment";

    private View mView;
    private ListView lvMe;
    private TextView tvMeName;
    private String[] meList=new String[]{"收藏文章","收藏图片"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long star=System.currentTimeMillis();
        Log.e(TAG, "onCreateView: ");
        mView =inflater.inflate(R.layout.fragment_me, container, false);
        initView();
        long endTime=System.currentTimeMillis();
        Log.e(TAG, "onCreateView: "+(endTime-star));
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(){
        lvMe=mView.findViewById(R.id.lv_me);
        tvMeName=mView.findViewById(R.id.tv_me_name);
        String userName = UserUtils.getUserName();
        tvMeName.setText(userName);
        lvMe.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,meList));
        lvMe.setOnItemClickListener(this);
    }

    /**
     * 点击listview
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = meList[position];
        Toast.makeText(getActivity(),s+"-内容",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), CollectActivity.class);
        int flags=0;
        switch (s){
            case "收藏文章":
                flags=1;
                break;
            case "收藏图片":
                flags=2;
                break;
            default:
        }
        intent.putExtra("flags",flags);
        startActivity(intent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: " );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: " );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView:" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
    }
}
