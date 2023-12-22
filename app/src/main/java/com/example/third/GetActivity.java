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
// ⽹络请求
                sendGetRequest(mGetUrl);
            }
        });
    }
    private void sendGetRequest(String theUrl){
        new Thread(
                () -> {
                    try {
                        URL url = new URL(theUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");//设置请求⽅式为GET
                        connection.setConnectTimeout(8000);//设置最⼤连接时间，单位
                        connection.setReadTimeout(8000);//设置最⼤的读取时间，单位为
                        connection.setRequestProperty("Accept-Language",
                                "zh-CN,zh;q=0.9");
                        connection.setRequestProperty("Accept-Encoding",
                                "gzip,deflate");
                        connection.connect();//正式连接
                        InputStream in = connection.getInputStream();//从接⼝处获取
                        String responseData = StreamToString(in);//这⾥就是服务器返
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler.sendMessage(message);
                        Log.d("lx", "sendGetNetRequest: "+responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
//这个Message msg 就是从另⼀个线程传递过来的数据
//在这⾥进⾏你要对msg的处理，将JSON字段解析
            String responseData = msg.obj.toString();
            setText(decodeJson(responseData));
        }
    }
    private void setText(BannerData bannerData) {
        ArrayList<Data> data1=new ArrayList<>();
        for(int i=0;i<4;i++){
            Data dataContent=new Data();
        dataContent.setData(bannerData.data.get(i).link);
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
    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();//新建⼀个StringBuilder，⽤于⼀点⼀点
        String oneLine;//流转换为字符串的⼀⾏
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {while ((oneLine = reader.readLine()) != null) {//readLine⽅法将读取⼀⾏
            sb.append(oneLine).append('\n');//拼接字符串并且增加换⾏，提⾼可读性
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();//关闭InputStream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();//将拼接好的字符串返回出去
    }
}
