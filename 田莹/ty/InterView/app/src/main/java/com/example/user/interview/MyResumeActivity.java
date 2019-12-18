package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class MyResumeActivity extends AppCompatActivity {
    private ListView lvResume;
    private MyResumeAdapter adapter;
    private List<Resume> resumes = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private  User user;
    private Button btnAdd;
    private Intent intent;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            findViews();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume);
        lvResume = findViewById(R.id.lv_resume);
        btnAdd = findViewById(R.id.btn_add_resume);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(getApplicationContext(),AddResumeActivity.class);
                intent.putExtra("userid",user.getUserid());
                startActivityForResult(intent,100);
            }
        });
        okHttpClient = new OkHttpClient();
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        requestData(user.getUserid());
    }

    private void requestData(int userid) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),userid+"");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_SERCH_MY_RESUME).post(body).build();
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
                resumes.clear();
                resumes.addAll((List<Resume>) new Gson().fromJson(resumeListStr,type));
                Log.e("myresume",resumes.toString());
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }

    private void findViews() {
        adapter = new MyResumeAdapter(resumes,R.layout.paper_listview_item,MyResumeActivity.this);
        lvResume.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode){
            case 101:
                Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();
                requestData(user.getUserid());
                break;
            case 201:
                Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                requestData(user.getUserid());
                break;
            default:
                break;
        }
    }
}
