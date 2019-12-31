package com.example.user.interview;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private String imageurl;
    private int companyid;
    private int id;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    TextView tv_name = (TextView) findViewById(R.id.name);
                    TextView tv_company = (TextView) findViewById(R.id.company);
                    ImageView iv_url = (ImageView) findViewById(R.id.iv_click);
                    Log.e("公司名字1", companyname + "");
                    Log.e("user名字2", username + "");
                    Glide.with(SelfActivity.this)
                            .load(Constant.BASE_URL + "avatarimg/" + imageurl)
                            .into(iv_url);
                    tv_name.setText(username);
                    tv_company.setText(companyname);
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_clickhead);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);

        id = getIntent().getIntExtra("userid", 1);

        if (id == user.getUserid()) {
            username = user.getNickname();
            companyid = user.getCompanyid();
            imageurl = user.getImageurl();
            Init2 init2 = new Init2();
            init2.execute("");
        } else {
            Init2 init2 = new Init2();
            init2.execute("");
        }
    }

    private void setCompany() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    URL url = new URL("http://" + Constant.ip + ":8080/ShiguoServerSystem/Company/SearchById");
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(String.valueOf(companyid).getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream is = conn.getInputStream();
                    byte[] btr = new byte[1024];
                    int len;

                    while ((len = is.read(btr)) != -1) {
                        result = new String(btr, 0, len);
                    }

                    Company company = gson.fromJson(result, Company.class);
                    companyname = company.getName();
                    Log.e("公司名字", gson.fromJson(result, Company.class).getName());
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                    is.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public class Init2 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                URL url = new URL(Constant.URL_LOCAL + "SearchById");
                //获得连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();
                conn.getOutputStream().write(String.valueOf(id).getBytes("UTF-8"));
                conn.getOutputStream().flush();

                InputStream is = conn.getInputStream();
                byte[] btr = new byte[1024];
                int len;
                while ((len = is.read(btr)) != -1) {
                    result = new String(btr, 0, len);
                }
                username = gson.fromJson(result, User.class).getNickname();
                Log.e("查找到的姓名", username);
                Log.e("查找到的姓名", gson.fromJson(result, User.class).getNickname());
                companyid = gson.fromJson(result, User.class).getCompanyid();
                imageurl = gson.fromJson(result, User.class).getImageurl();

                is.close();//关闭数据流
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            setCompany();


        }
    }
}
