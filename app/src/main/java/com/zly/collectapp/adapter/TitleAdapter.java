package com.zly.collectapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.zly.collectapp.R;
import com.zly.collectapp.entity.Article;
import java.util.List;


public class TitleAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article> articleList;

    public TitleAdapter(Context context, List<Article> articles){
        this.mContext=context;
        this.articleList=articles;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder viewHolder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.title_item,parent,false);
            viewHolder=new TitleViewHolder();
            viewHolder.itemTitle=convertView.findViewById(R.id.item_title);
            viewHolder.itemAuthor=convertView.findViewById(R.id.item_author);
            viewHolder.itemDate=convertView.findViewById(R.id.item_date);
            viewHolder.ivTitle = convertView.findViewById(R.id.iv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (TitleViewHolder) convertView.getTag();
        }
        Glide.with(mContext).asBitmap().load(articleList.get(position).getImages()).into(viewHolder.ivTitle);
        viewHolder.itemTitle.setText(articleList.get(position).getTitle());
        viewHolder.itemDate.setText(articleList.get(position).getPublishAt());
        viewHolder.itemAuthor.setText(articleList.get(position).getAuthor());
        return convertView;
    }


   public class TitleViewHolder {
        TextView itemTitle, itemAuthor, itemDate;
        ImageView ivTitle;
    }
}
