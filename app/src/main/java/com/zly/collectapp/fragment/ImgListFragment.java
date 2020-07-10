package com.zly.collectapp.fragment;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zly.collectapp.R;
import com.zly.collectapp.adapter.BeautyAdapter;
import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.utils.UserUtils;
import com.zly.collectapp.view.BeautyDividerDecoration;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ImgListFragment extends Fragment {
    private static final String TAG="ImgListFragment";
    private static final int GET_BEAUTY=1;
    private String url="https://gank.io/api/v2/data/category/Girl/type/Girl/page/";
    private int page=1;
    private int size=10;
    private View mView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rvImgList;
    private List<Beauty> beautyList=new ArrayList<>();
    private BeautyAdapter ba;

    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case GET_BEAUTY:
                    ba.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long starDate=System.currentTimeMillis();
       mView=inflater.inflate(R.layout.fragment_img_list, container, false);
       initView();
       initData();
        long endDate=System.currentTimeMillis();
        Log.e(TAG, "onCreateView:时间:"+(endDate-starDate)+"ms");
       return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:时间:");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated:时间:");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume:时间:");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause:时间:");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart:时间:");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop:时间:");
    }

    /**
     * 进行美女图片的展示
     */
    private void initView() {
        rvImgList=mView.findViewById(R.id.rv_img_list);
        rvImgList.setItemAnimator(new DefaultItemAnimator());
        refreshLayout=mView.findViewById(R.id.srl_img_list);
        ba=new BeautyAdapter(getActivity(),beautyList);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),2);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.FixedBehind));
        rvImgList.setAdapter(ba);
        rvImgList.setLayoutManager(glm);
        rvImgList.addItemDecoration (new BeautyDividerDecoration());
        //上拉加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page=page+1;
                getBeauties();
                refreshLayout.finishLoadMore(true);
            }
        });
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                beautyList.clear();
                page=1;
                queryFromServer(url+page+"/count/"+size);
                refreshLayout.finishRefresh(true);
            }
        });
    }

    private void initData(){
        Bundle bundle = getArguments();
        String type =null;
        if (bundle!=null){
            type = bundle.getString("type");
        }
        if (type!=null && type.equals("collect")){
            beautyList.clear();
            int userId = UserUtils.getUserId();
            List<Beauty> beauties = UserDatabase.getInstance(getActivity()).getBeautyDao().getBeautiesByUserId(userId);
            Log.e(TAG, "initData: "+(beauties==null));
            if (beauties != null) {
                beautyList.addAll(beauties);
            }
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setEnableRefresh(false);
            ba.notifyDataSetChanged();
        } else {
            getBeauties();
        }
    }

    private void getBeauties(){
        int star=(page-1)*size;
        List<Beauty> beauties = UserDatabase.getInstance(getActivity()).getBeautyDao().getBeauties(star,size);
        if (beauties.size()>0 && beauties.size()==size){
            beautyList.addAll(beauties);
            ba.notifyDataSetChanged();
        }else {
            queryFromServer(url+page+"/count/"+size);
        }
    }

    /**
     * 从服务器获取数据
     */
    private void queryFromServer(String url){
        OkHttpUtils.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                final List<Beauty> beauties = JsonToModel.getBeauties(responseText);
//                Log.e(TAG, "onResponse: "+beauties.get(0).toString() );
                if (beauties!=null){
                    beautyList.addAll(beauties);
                    mHandler.sendEmptyMessage(GET_BEAUTY);
                    UserDatabase.getInstance(getActivity()).getBeautyDao().insert(beauties);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
