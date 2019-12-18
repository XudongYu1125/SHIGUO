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


        //user.setNickname("薛炮");


        TextView tv_name = (TextView) findViewById(R.id.name);
        TextView tv_company = (TextView) findViewById(R.id.company);
        // 查找compnay名字
        //tv_company.setText(user.getCompanyid());


        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject object = new JSONObject();
                try {
                    object.put("companyID", user.getCompanyid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String urlStr = "http://10.7.89.54:8080/ShiguoServerSystem/Conpany/Search";

                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());

                    JSONObject jsonObject = response.getJSONObject("conpany");

                    Company company = new Company();
                    company = gson.fromJson(jsonObject.toString(), Company.class);
                    companyname = company.getName();

                    inputStream.close();//关闭数据流
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

                String urlStr = "http://10.7.89.54:8080/ShiguoServerSystem/User/SearchById";

                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());
                    JSONObject jsonObject = response.getJSONObject("user");

                    username = gson.fromJson(jsonObject.toString(), User.class).getNickname();

                    inputStream.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
