package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ReceivedResumeActivity extends AppCompatActivity {
    private ListView lvResume;
    private ReceivedResumeAdapter adapter;
    private List<Resume> resumes = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private  User user;
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
        okHttpClient = new OkHttpClient();
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        Resume resume = new Resume();
        resume.setResumename("1111");
        resumes.add(resume);
        requestData(user.getUserid());
    }

    private void requestData(int userid) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),userid+"");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_SERCH_RECEIVED_RESUME).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resumeListStr = response.body().string();
                Type type = new TypeToken<List<Resume>>(){}.getType();
                resumes.addAll((List<Resume>) new Gson().fromJson(resumeListStr,type));
                Log.e("myresume",resumes.toString());
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }

    private void findViews() {
        lvResume = findViewById(R.id.lv_exam_paper);
        adapter = new ReceivedResumeAdapter(resumes,R.layout.paper_listview_item,ReceivedResumeActivity.this);
        lvResume.setAdapter(adapter);
    }
}
