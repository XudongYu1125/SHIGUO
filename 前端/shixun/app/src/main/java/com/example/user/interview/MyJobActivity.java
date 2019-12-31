package com.example.user.interview;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyJobActivity extends AppCompatActivity {
    private ListView listView;
    private MyJobAdapter adapter;
    private List<JobSearch> jobSearches = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private User user;
    private SharedPreferences sharedPreferences ;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            findViews();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_paper);
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user", null), User.class);
        okHttpClient = new OkHttpClient();
        requestData();
    }

    private void requestData() {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),user.getUserid()+"");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_MY_JOB_SEARCH).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("1",e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String list = response.body().string();
                Type type = new TypeToken<List<JobSearch>>(){}.getType();
                jobSearches.addAll((List<JobSearch>) new Gson().fromJson(list,type));
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }

    private void findViews() {
        listView = findViewById(R.id.lv_exam_paper);
        adapter = new MyJobAdapter(jobSearches, R.layout.my_job_item, MyJobActivity.this);
        listView.setAdapter(adapter);
    }
}
