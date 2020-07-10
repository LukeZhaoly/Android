package com.zly.collectapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zly.collectapp.ContentActivity;
import com.zly.collectapp.R;
import com.zly.collectapp.adapter.TitleAdapter;
import com.zly.collectapp.entity.Article;
import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.utils.UserUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TitleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG="TitleFragment";
    private static final int GET_SUCCESS=1;
    private View mView;
    private ListView lvTitleList;
    private List<Article> articles=new ArrayList<>();
    private static TitleAdapter ta;
    private SmartRefreshLayout refreshLayout;

    private String url="https://gank.io/api/v2/data/category/GanHuo/type/Android/page/";
    private int count=10;
    private int page=1;


    public static class TitleHandler extends Handler{
        private SoftReference<Fragment> mFragment;

        public TitleHandler(Fragment fragment){
            mFragment=new SoftReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Fragment fragment = mFragment.get();
            if (fragment!=null){
                switch (msg.what){
                    case GET_SUCCESS:
                        ta.notifyDataSetChanged();
                        Log.e(TAG, "handleMessage:"+GET_SUCCESS+"-成功更新数据");
                        break;
                }
            }
        }
    }

    private TitleHandler mHandler=new TitleHandler(this);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: " );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         long starDate=System.currentTimeMillis();
         mView = inflater.inflate(R.layout.fragment_title, container, false);
         //Log.e(TAG, "onCreateView: ");
         initView();
         long endDate=System.currentTimeMillis();
         Log.e(TAG, "onCreateView:时间:"+(endDate-starDate)+"ms");
         return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化视图组件
     */
    public void initView(){
        refreshLayout=mView.findViewById(R.id.title_refresh);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.FixedBehind));
        lvTitleList = mView.findViewById(R.id.lv_title_list);
        ta=new TitleAdapter(getActivity(),articles);
        lvTitleList.setAdapter(ta);
        lvTitleList.setOnItemClickListener(this);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                articles.clear();
                page=1;
                queryFromServer(url+page+"/count/"+count);
                refreshLayout.finishRefresh(true);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page=page+1;
                getArticle();
                refreshLayout.finishLoadMore(true);
            }
        });
    }

    /**
     * 初始化数据,判断是收藏还是主页展示，实现fragment的复用
     */
    private void initData(){
        Bundle bundle = getArguments();
        String type=null;
        if (bundle!=null){
            type = bundle.getString("type");
        }
        if (type!=null && type.equals("collect")){
            articles.clear();
            List<Article> articleList = UserDatabase.getInstance(getActivity()).getArticleDao().getArticlesByUserId(UserUtils.getUserId());
            articles.addAll(articleList);
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setEnableRefresh(false);
            ta.notifyDataSetChanged();
        }else{
            getArticle();
        }
    }

    /**
     * 从本地和网络进行查询
     */
    private void getArticle(){
        int star=(page-1)*count;
        List<Article> articleList = UserDatabase.getInstance(getActivity()).getArticleDao().getAllArtilces(star, count);
        articles.addAll(articleList);
        Log.e(TAG, "getArticle-size: "+articles.size() );
        if (articleList.size()==count){
            ta.notifyDataSetChanged();
        }else {
            queryFromServer(url+page+"/count/"+count);
        }
    }


    /**
     * 从服务器查询数据
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
                final List<Article> articleList = JsonToModel.getTitleList(responseText);
                if (articleList!=null){
                    articles.addAll(articleList);
                    UserDatabase.getInstance(getActivity()).getArticleDao().insert(articleList);
                    mHandler.sendEmptyMessage(GET_SUCCESS);
                }
                //Log.e(TAG, "onResponse: "+page);
            }
        });
    }


    /**
     * 点击某一项，跳转到具体的内容
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("article",articles.get(position));
        getActivity().startActivity(intent);
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
        Log.e(TAG, "onStart:");
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
