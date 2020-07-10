package com.zly.collectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zly.collectapp.application.MyApplication;
import com.zly.collectapp.entity.Article;
import com.zly.collectapp.entity.ArticleCollect;
import com.zly.collectapp.utils.JsonToModel;
import com.zly.collectapp.utils.MarkdownUtils;
import com.zly.collectapp.utils.OkHttpUtils;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.utils.UserUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.noties.markwon.Markwon;
import io.noties.markwon.image.glide.GlideImagesPlugin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="ContentActivity";
    private TextView tvContentTitle,tvContentAuthor,tvContentInfo;
    private ImageView ivImgCollect;
    private Article article;
    private long endDate;
    private long starDate;
    private SharedPreferences sp;
    private Set<String> stringsSet;

    private ProgressBar pbLoading;

    //private MarkdownUtils markUtils= MyApplication.getMarkUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        long starDate=System.currentTimeMillis();
        initView();
        initData();
        long endDate=System.currentTimeMillis();
        Log.e(TAG, "onCreateView:时间:"+(endDate-starDate)+"ms");
        //getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public void initView(){
        tvContentTitle=findViewById(R.id.tv_content_title);
        tvContentAuthor=findViewById(R.id.tv_content_author);
        tvContentInfo=findViewById(R.id.tv_content_info);
//        contentWv=findViewById(R.id.content_wv);
//        contentWv.getSettings().setJavaScriptEnabled(true);
//        contentWv.getSettings().setDefaultTextEncodingName("utf-8");
        ivImgCollect=findViewById(R.id.iv_img_collect);
        ivImgCollect.setOnClickListener(this);
        pbLoading=findViewById(R.id.pb_loading);
    }

    private void initData(){
        //获取intent的数据
        article = getIntent().getParcelableExtra("article");
        sp=getSharedPreferences("user_collect",MODE_PRIVATE);
        //进行数据获取
        tvContentTitle.setText(article.getTitle());
        tvContentAuthor.setText(article.getAuthor());
        stringsSet = sp.getStringSet(UserUtils.getUserId() + "", new HashSet<String>());
        //ArticleCollect one = UserDatabase.getInstance(this).getArticleCollectDao().getOne(UserUtils.getUserId(), article.get_id());
        if (stringsSet.contains(article.get_id())){
            ivImgCollect.setVisibility(View.GONE);
        }
        onArticleTitleSelected(article.get_id());
    }

    public void onArticleTitleSelected(String id) {
        Article article = UserDatabase.getInstance(this).getArticleDao().getArticle(id);
        //判断是否存在内容
        if (article.getContent()!=null&&!article.getContent().equals("")){
            //contentWv.loadData(article.getContent(),"markdown","utf-8");
            pbLoading.setVisibility(View.GONE);
            final Markwon markwon = Markwon.builder(ContentActivity.this).usePlugin(GlideImagesPlugin.create(ContentActivity.this)).build();
            markwon.setMarkdown(tvContentInfo,article.getContent());
            //RichText.from(article.getContent()).clickable(true).type(RichType.markdown).into(tvContentInfo);
        }else {
            //不存在，进行网络查询
            String url="https://gank.io/api/v2/post/"+id;
            queryArticleContent(id,url);
        }
    }

    /**
     * 网路查询该文章内容
     * @param url
     */
    public void queryArticleContent(final String id, String url) {
        starDate=System.nanoTime();
        OkHttpUtils.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                final String content = JsonToModel.getArticleContent(responseText);
                if (content!=null&&!content.equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserDatabase.getInstance(ContentActivity.this).getArticleDao().updateContent(id,content);
                            //contentWv.loadData(article.getContent(),"markdown","utf-8");
                            //RichText.from(content).type(RichType.markdown).into(tvContentInfo);
                           // final Markwon markwon = Markwon.builder(ContentActivity.this).usePlugin(GlideImagesPlugin.create(ContentActivity.this)).build();
                            pbLoading.setVisibility(View.GONE);
                            final Markwon markwon = Markwon.builder(ContentActivity.this).usePlugin(GlideImagesPlugin.create(ContentActivity.this)).build();
                            markwon.setMarkdown(tvContentInfo,content);
                            NetTime();
                        }
                    });
                }
            }
        });
    }

    private void NetTime() {
        endDate=System.nanoTime();
        Log.e(TAG, "NetTime: 网络耗时"+(endDate-starDate));
    }

    @Override
    public void onClick(View v) {
        //点击进行收藏
        if (v==ivImgCollect){
            HandlerThread thread=new HandlerThread("articleCollect");
            thread.start();
            Handler handler=new Handler(thread.getLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what){
                        case 1:Toast.makeText(ContentActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();break;
                    }
                }
            };
            ArticleCollect ac=new ArticleCollect();
            ac.setArticleId(article.get_id());
            ac.setUserId(UserUtils.getUserId());
            UserDatabase.getInstance(this).getArticleCollectDao().insert(ac);
            Set<String> listId=new HashSet<>();
            listId.add(article.get_id());
            stringsSet.addAll(listId);
            sp.edit().putStringSet(UserUtils.getUserId()+"",stringsSet).apply();
            handler.sendEmptyMessage(1);
            thread.quit();
            ivImgCollect.setVisibility(View.INVISIBLE);
        }
    }
}
