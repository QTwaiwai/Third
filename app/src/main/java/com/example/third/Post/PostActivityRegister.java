package com.example.third.Post;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.third.MyNetRequest;
import com.example.third.R;

import java.util.HashMap;

public class PostActivityRegister extends AppCompatActivity {
    private Button mBtnRegister;
    private EditText mEtPassword;
    private EditText mEtUsername;
    private final String mPostUrlRegister = "https://www.wanandroid.com/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postactivity_register);
        initView();
        iniRegister();
    }
    //初始相应的UI
    private void initView() {
        mBtnRegister=findViewById(R.id.sure);
        mEtPassword=findViewById(R.id.et_register_password);
        mEtUsername=findViewById(R.id.et_register_username);
    }
    //密码的要求
    public boolean FocusChange(View view,boolean hasFoucs){
        if(hasWindowFocus()){
            String word=mEtPassword.getText().toString();
            if(word.length()<6){
                mEtPassword.requestFocus();
                Toast.makeText(this,"请输入大于6位的密码",Toast.LENGTH_SHORT).show();
                return true;
            }
            if (!word.matches("^[a-z0-9]+$")){
                mEtPassword.requestFocus();
                Toast.makeText(this,"请输入包含数字和字母的密码",Toast.LENGTH_SHORT).show();
                return true;
            } else  {
                return false;
            }
        }
        else return false;
    }
    //点击注册
    private void iniRegister() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FocusChange(v,mBtnRegister.hasFocus()));
                else{
                    HashMap<String,String> params=new HashMap<>();
                    params.put("username",mEtUsername.getText().toString());
                    params.put("password",mEtPassword.getText().toString());
                    params.put("repassword",mEtPassword.getText().toString());
                    new MyNetRequest(mPostUrlRegister,params,new Handler());
                finish();}
            }
        });
    }
}
