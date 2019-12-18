package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private TextView register;
    private TextView findPwd;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();
    }

    public void findId() {
        register = findViewById(R.id.tv_register);
        login = findViewById(R.id.btn_login);
        findPwd = findViewById(R.id.tv_find_psw);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.btn_login:
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(MainActivity.this, TabHostActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
                break;
            //注册
            case R.id.tv_register:
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            //忘记密码
            case R.id.tv_find_psw:
                findPwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(MainActivity.this, FindPassword.class);
                        startActivity(intent);
                    }
                });
                break;
        }

    }


}

