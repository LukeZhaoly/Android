package com.zly.collectapp.utils;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.zly.collectapp.entity.Article;
import com.zly.collectapp.entity.Beauty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json转化
 */
public class JsonToModel {



//    public static <T> List<T> getJsonArrayToModel(Message msg, Class<T> t,
//                                                  T model) {
//        List<T> list = new ArrayList<T>();
//        try {
//            JSONObject json = new JSONObject(msg.obj.toString());
//            for (int i = 0; i < json.getJSONArray(getInfo()).length(); i++) {
//                model = GsonHelper.toType(json.getJSONArray(getInfo()).get(i).toString(), t);
//                list.add(model);
//            }
//            return list;
//        } catch (Exception e) {
//            Log.e("getJsonArrayToModel", "error");
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 美女图片Json to Model
     * @param JsonStr  获取的Json字符串
     * @return
     */
    public static List<Beauty> getBeauties(String JsonStr){
        List<Beauty> beauties=new ArrayList<>();
        try {
            JSONObject json=new JSONObject(JsonStr);
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                Beauty beauty=new Beauty();
                beauty.set_id(data.getJSONObject(i).getString("_id"));
                beauty.setUrl(data.getJSONObject(i).getString("url"));
                beauty.setCategory(data.getJSONObject(i).getString("category"));
                beauty.setType(data.getJSONObject(i).getString("type"));
                beauty.setLikeCounts(data.getJSONObject(i).getInt("likeCounts"));
                //UserDatabase.getInstance(context).getBeautyDao().insert(beauty);
                beauties.add(beauty);
            }
            return beauties;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 进行文章的请求
     * @param JsonStr
     * @return
     */
    public static List<Article> getTitleList(String JsonStr){
        List<Article> articles=new ArrayList<>();
        try {
            JSONObject json=new JSONObject(JsonStr);
            JSONArray data = json.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                Article article=new Article();
                article.set_id(data.getJSONObject(i).getString("_id"));
                article.setTitle(data.getJSONObject(i).getString("title"));
                article.setAuthor(data.getJSONObject(i).getString("author"));
                article.setDesc(data.getJSONObject(i).getString("desc"));
                article.setPublishAt(data.getJSONObject(i).getString("publishedAt"));
                article.setImages(data.getJSONObject(i).getJSONArray("images").getString(0));
                //UserDatabase.getInstance(context).getArticleDao().insert(article);
                System.out.println(article.toString());
                articles.add(article);
            }
            return articles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String getArticleContent(String JsonStr){
        try {
            JSONObject json = new JSONObject(JsonStr);
            JSONObject data = json.getJSONObject("data");
            String content = data.getString("markdown");
            return content;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
