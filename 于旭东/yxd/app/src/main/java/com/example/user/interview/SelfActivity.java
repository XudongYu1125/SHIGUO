package com.example.user.interview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SelfActivity extends AppCompatActivity {

    private String companyname;
    private String username;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_clickhead);

        // 接受userid 查找user
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);

        int id = getIntent().getIntExtra("userid", 1);
        if (id == user.getUserid()) {

        } else {
            searchUsername(id);
        }


        TextView tv_name = (TextView) findViewById(R.id.name);
        TextView tv_company = (TextView) findViewById(R.id.company);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    URL url = new URL(Constant.URL_LOCAL+"Conpany/Search");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(gson.toJson(user.getCompanyid()).getBytes());
                    conn.getOutputStream().flush();

                    InputStream is = conn.getInputStream();
                    byte[] btr = new byte[1024];
                    int len;

                    while ((len = is.read(btr)) != -1) {
                        result = new String(btr, 0, len);
                    }

                    Company company = gson.fromJson(result, Company.class);
                    companyname = company.getName();

                    is.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        tv_name.setText(user.getNickname());
        tv_company.setText(companyname);
    }

    private void searchUsername(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();

                JSONObject object = new JSONObject();
                try {
                    object.put("userID", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    URL url = new URL(Constant.URL_LOCAL+"SearchById");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream is = conn.getInputStream();
                    byte[] btr = new byte[1024];
                    int len;
                    String result = null;
                    while ((len = is.read(btr)) != -1) {
                        result = new String(btr, 0, len);
                    }
                    username = gson.fromJson(result, User.class).getNickname();

                    is.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
