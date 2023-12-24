package com.example.third.Post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.third.BannerData;
import com.example.third.MyNetRequest;
import com.example.third.R;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONObject;
import java.util.HashMap;

public class PostActivityLogin extends AppCompatActivity {
    private TextInputEditText mEtUsername;
    private TextInputEditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private CheckBox mCbRemember;
    SharedPreferences loginPreference;
    private Handler handler;
    private final String mPostUrlLogin= "https://www.wanandroid.com/user/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postactivity_login);
        ImageView mImgHead = findViewById(R.id.school);
        Glide.with(this).load("https://tse1-mm.cn.bing.net/th/id/OIP-C.8DCWFDaFFA_Z-SqKbZqVGgAAAA?rs=1&pid=ImgDetMain").into(mImgHead);
        handler=new MyHandler();
        initView();
        initRegister();
        initLogin();
    }
    //注册相应的UI
    private void initView() {

        mEtUsername = findViewById(R.id.et_main_username);
        mEtPassword = findViewById(R.id.et_main_password);
        mBtnLogin = findViewById(R.id.denglu);
        mBtnRegister=findViewById(R.id.register);
        mCbRemember=findViewById(R.id.remember_code);
        //建立一个记住密码的储存，并且按要求读取
        loginPreference=getSharedPreferences("remember",MODE_PRIVATE);
        if(loginPreference.getBoolean("check",false)){
            mEtUsername.setText((CharSequence) loginPreference.getString("username",""));
            mEtPassword.setText((CharSequence) loginPreference.getString("password",""));
            mCbRemember.setChecked(loginPreference.getBoolean("check",false));
        }
    }
    //点击登录按键
    private void initLogin() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {login();}
        });
    }
   //登录是否成功的判断
    private void login() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        HashMap<String,String> sure=new HashMap<>();
        sure.put("username",mEtUsername.getText().toString());
        sure.put("password",mEtPassword.getText().toString());
        sure.put("repassword",mEtPassword.getText().toString());
        MyNetRequest sureInformation=new MyNetRequest(mPostUrlLogin,sure,handler);
        // 1当前创建一个handler，然后重写handler中的方法
        // 2在handler重写的方法中就可以拿到数据了
        // 3解析Json之后，保存到一个对象中
        // 4你可以直接从对象中拿，判断是否成功。然后直接跳转或者你想要进行的操作。

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String responseData = msg.obj.toString();
            panduan(decodeJson(responseData));
        }
    }

    private void panduan(BannerData bannerData) {
        if(bannerData.errorCode==-1){
            loginSuccess();
        }
        else {
            loginFailure();
        }
    }



    //登录失败
    private void loginFailure() {
        Toast.makeText(this, "账号或者密码好像输错了 :(",
                Toast.LENGTH_SHORT).show();
    }
    //登录成功并按要求保存账号和密码
    private void loginSuccess() {
        SharedPreferences.Editor registrar=loginPreference.edit();
        if(mCbRemember.isChecked()){
                registrar.putString("username",mEtUsername.getText().toString());
                registrar.putString("password",mEtPassword.getText().toString());
                registrar.putBoolean("check",mCbRemember.isChecked());
        }else{
            registrar.remove("username");
            registrar.remove("password");
            registrar.remove("check");
        }
        registrar.apply();
        Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, PostActivityLogined.class);
        startActivity(intent);
    }
    //点击注册按键
    private void initRegister() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {register();}
        });
    }
    private void register() {
        Intent intent=new Intent(this, PostActivityRegister.class);
        startActivity(intent);
    }

    private BannerData decodeJson(String data) {
// 数据存储对象
        BannerData bannerData = new BannerData();
        try {
// 获得这个JSON对象{}
            JSONObject jsonObject = new JSONObject(data);
// 获取并列的三个，errorCode，errorMsg，data
            bannerData.errorCode = jsonObject.getInt("errorCode");
            bannerData.errorMsg = jsonObject.getString("errorMsg");
// data是⼀个对象集合
        }catch (Exception e){
            Log.w("lx","(JsonActivity.java:59)-->>",e);
        }
        return bannerData;
    }
}
