package com.zly.collectapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.zly.collectapp.R;
import com.zly.collectapp.entity.Beauty;
import com.zly.collectapp.entity.BeautyCollect;
import com.zly.collectapp.service.DownloadService;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.utils.UserUtils;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import java.util.List;

/**
 * 美女图片的加载适配器
 */
public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.BeautyViewHolder>{
    private Context mContext;
    private List<Beauty> beautyList;

    public BeautyAdapter(Context context,List<Beauty> beauties){
        this.mContext=context;
        this.beautyList=beauties;
    }



    @NonNull
    @Override
    public BeautyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iView = LayoutInflater.from(mContext).inflate(R.layout.img_list_item, parent, false);
        BeautyViewHolder holder = new BeautyViewHolder(iView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeautyViewHolder holder, final int position) {
        final Beauty beauty = beautyList.get(position);
        final String imgUrl= beauty.getUrl();
        RequestOptions options=new RequestOptions().override(1000,1400).placeholder(R.drawable.place).centerCrop();
        Glide.with(mContext.getApplicationContext()).load(imgUrl).transition(new DrawableTransitionOptions().crossFade()).apply(options).into(holder.ivImg);
        holder.ivImg.setAdjustViewBounds(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgCheck(beauty);
                }});

    }

    @Override
    public int getItemCount() {
        return beautyList==null?0:beautyList.size();
    }



    /**
     * 每一个图片样式
     */
    class BeautyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        View iView;

        public BeautyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            iView=itemView;

        }

    }

    private void imgCheck(final Beauty beauty){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.img_check_item, null);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        final PopupWindow  mPopWindow = new PopupWindow(contentView,
                width-300, height-500, true);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setContentView(contentView);
        ImageView ivImgCheck=contentView.findViewById(R.id.iv_img_check);
        Button btnImgDown = contentView.findViewById(R.id.btn_img_download);
        Button btnImgCollect = contentView.findViewById(R.id.btn_img_collect);
        Glide.with(contentView).load(beauty.getUrl()).into(ivImgCheck);
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        ivImgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                //Toast.makeText(mContext,"点击图片返回",Toast.LENGTH_SHORT).show();
            }
        });
        btnImgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"下载开始",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,DownloadService.class);
                intent.putExtra("url",beauty.getUrl());
                mContext.startService(intent);
            }
        });
        btnImgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeautyCollect beautyCollect=new BeautyCollect();
                beautyCollect.setUserId(UserUtils.getUserId());
                beautyCollect.setBeautyId(beauty.get_id());
                UserDatabase.getInstance(mContext).getBeautyCollectDao().insert(beautyCollect);
                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
