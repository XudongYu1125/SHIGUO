package com.example.user.interview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class PaperActivity extends AppCompatActivity {
    private TextView tvPaper;
    private Button btnAnswer;
    private OkHttpClient okHttpClient;
    private List<Problem> problems = new ArrayList<>();
    private String problemStr ="";
    private String paper =null;
    private Intent intent;
    private String str;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            findViews();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        intent = getIntent();
        paper = intent.getStringExtra("paper");
        //根据paperid，进行数据查询，获得problem对象
        okHttpClient = new OkHttpClient();
        requestData();

    }

    private void findViews() {
        tvPaper = findViewById(R.id.tv_paper);
        btnAnswer = findViewById(R.id.btn_paper_answer);
        tvPaper.setText(problemStr);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAnswer = new Intent();
                intentAnswer.setClass(PaperActivity.this,PaperAnswerActivity.class);
                intentAnswer.putExtra("answer",str);
                startActivity(intentAnswer);
            }
        });
    }
    private void requestData() {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),paper);
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_ALL_PROBLEM).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String paperListStr = response.body().string();
                str = paperListStr;
                Type type = new TypeToken<List<Problem>>(){}.getType();
                problems.addAll((List<Problem>) new Gson().fromJson(paperListStr,type));
                for (int i = 0;i<problems.size();i++) {
                    problemStr = problemStr +(i+1)+"、"+ problems.get(i).getContent().toString() +"\n";
                }
                Message message = new Message();
                message.obj = problemStr;
                myHandler.sendMessage(message);
            }
        });
    }

}
