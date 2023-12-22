package com.example.third;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
      TextView mTvLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String jsonData = "{\n" +
                "       \"data\":[\n" +
                "       {\n" +
                "             \"category\":\"源码\",\n " +
                "             \"icon\":\"\",\n" +
                "             \"id\":22,\n" +
                "             \"link\": \"https://www.androidos.net.cn/sourcecode\",\n" +
                "             \"name\":\"androidos\",\n" +
                "             \"order\":11,\n" +
                "             \"visible\":1,\n" +
                "       },\n" +
                "      {\n" +
                "             \"category\":\"源码\",\n" +
                "             \"icon\":\"\",\n" +
                "             \"id\":41,\n" +
                "             \"link\":\"http://androidxref.com/\",\n" +
                "             \"name\": \"androidxref\",\n" +
                "             \"order\":12,\n" +
                "             \"visible\":1,\n" +
                "       },\n" +
                "       {\n" +
                "             \"category\":\"源码\",\n" +
                "             \"icon\":\"\",\n" +
                "             \"id\":44,\n" +
                "             \"link\":\"https://cs.android.com\",\n" +
                "             \"name\": \"cs.android\",\n" +
                "             \"order\":13,\n" +
                "             \"visible\":1,\n" +
                "      },\n" +
                "          {\n" +
                "             \"category\":\"官方\",\n" +
                "             \"icon\":\"\",\n" +
                "             \"id\":21,\n" +
                "             \"link\": \"https://developer.android.google.cn/\",\n" +
                "             \"name\":\"API文档\",\n" +
                "             \"order\":15,\n" +
                "             \"visible\":1,\n" +
                "       },\n" +
                "          {\n" +
                "             \"category\":\"官方\",\n" +
                "             \"icon\":\"\",\n" +
                "             \"id\":46,\n" +
                "             \"link\":\"https://www.wanandroid.com/blog/show/2791\",\n " +
                "             \"name\":\"系统版本\",\n " +
                "             \"order\":16,\n " +
                "             \"visible \":1,\n" +
                "      }\n" +
                "  ],\n"+
                "  \"errorCode\":0,\n"+
                "  \"errorMsg\":\"\"\n"+
                "}";
        BannerData bannerData =decodeJson(jsonData);
        setData(bannerData);
        }

    private BannerData decodeJson(String jsonData) {
        BannerData bannerData = new BannerData();
        bannerData.data = new ArrayList<>();
        try {
// 获得这个JSON对象{}
            JSONObject jsonObject = new JSONObject(jsonData);
// 获取并列的三个，errorCode，errorMsg，data
            bannerData.errorCode = jsonObject.getInt("errorCode");
            bannerData.errorMsg = jsonObject.getString("errorMsg");
// data是⼀个对象集合
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            BannerData.DetailData detailData;
            for (int i = 0; i < jsonArray.length(); i++) {
// 遍历数组，给集合添加对象
                detailData = new BannerData.DetailData();
                JSONObject singleData = jsonArray.getJSONObject(i);
                detailData.category = singleData.getString("category");
                detailData.icon=singleData.getString("icon");
                detailData.id=singleData.getInt("id");
                detailData.name=singleData.getString("name");
                detailData.link=singleData.getString("link");
                detailData.order=singleData.getInt("order");
                detailData.visible=singleData.getInt("visible");
                bannerData.data.add(detailData);
            }
        }catch (Exception e){
            Log.w("lx","(JsonActivity.java:59)-->>",e);
        }
        return bannerData;

    }

    private void setData(BannerData bannerData) {
        mTvLink.setText(bannerData.data.get(0).link);
    }

    private void initView() {
        mTvLink=findViewById(R.id.main_activity_json_tv_link);
    }

}