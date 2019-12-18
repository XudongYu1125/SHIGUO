package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ProblemListActivity extends AppCompatActivity {
    private Intent intent;
    private int type;
    private ListView lvProblem;
    private List<Problem> problems = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private ProblemListAdapter adapter;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            findViews();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_paper);
        intent = getIntent();
        type = intent.getIntExtra("type",0);
        okHttpClient = new OkHttpClient();
        requestData(type);
    }
    private void requestData(int type) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),type+"");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_PROBLEM).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String problemStr = response.body().string();
                Log.e("333",problemStr);
                Type type = new TypeToken<List<Problem>>(){}.getType();
                problems.addAll((List<Problem>) new Gson().fromJson(problemStr,type));
                Log.e("ppp",problems.toString());
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }
    private void findViews() {
        lvProblem = findViewById(R.id.lv_exam_paper);
        adapter = new ProblemListAdapter(problems, R.layout.paper_listview_item, ProblemListActivity.this);
        lvProblem.setAdapter(adapter);
    }
}
