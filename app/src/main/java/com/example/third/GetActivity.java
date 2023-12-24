package com.example.third;
//https://www.wanandroid.com/friend/json

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
public class GetActivity extends AppCompatActivity {

    private Button mBtnSend;
    private RecyclerView recyclerView;
    private Handler mHandler;
    private MyNetRequest myNetRequest;
    private final String mGetUrl = "https://www.wanandroid.com/friend/json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        mHandler = new MyHandler();
        initView();
        initClick();
    }
    private void initView() {
        mBtnSend = findViewById(R.id.get_activity_btn_send);
        recyclerView=findViewById(R.id.recyclerview);
    }
    private void initClick() {
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// ⽹络请求,把网站和Handler传入封装的请求
               myNetRequest=new MyNetRequest(mGetUrl,mHandler);
            }
        });
    }
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String responseData = msg.obj.toString();
            setText(decodeJson(responseData));
        }
    }
    private void setText(BannerData bannerData) {
        ArrayList<Data> data1=new ArrayList<>();
        for(int i =0; i<bannerData.data.size(); i++){
            Data dataContent=new Data();
        dataContent.setData("第"+(i+1)+"个网站:"+bannerData.data.get(i).link);
        data1.add(dataContent);
    }
        RvAdapter rvAdapter=new RvAdapter(data1);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private BannerData decodeJson(String data) {
// 数据存储对象
        BannerData bannerData = new BannerData();
        bannerData.data = new ArrayList<>();
        try {
// 获得这个JSON对象{}
            JSONObject jsonObject = new JSONObject(data);
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

}
