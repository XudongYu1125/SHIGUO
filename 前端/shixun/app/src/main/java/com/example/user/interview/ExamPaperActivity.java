package com.example.user.interview;

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

public class ExamPaperActivity extends AppCompatActivity {
    private ListView lvExamPaper;
    private ExamPaperAdapter adapter;
    private List<Paper> paperList = new ArrayList<>();
    private OkHttpClient okHttpClient;
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
//        findViews();
//        Paper paper = new Paper();
//        paper.setName("111");
//        paper.setLevel(5);
//        paperList.add(paper);
        requestData();
    }


    private void findViews() {
        lvExamPaper = findViewById(R.id.lv_exam_paper);
        adapter = new ExamPaperAdapter(paperList, R.layout.exam_paper_listview_item, ExamPaperActivity.this);
        Log.e("11",paperList.toString());
        lvExamPaper.setAdapter(adapter);
    }


    private void requestData() {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),"1");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_PAPER).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("1",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String paperListStr = response.body().string();
                Type type = new TypeToken<List<Paper>>(){}.getType();
                paperList.addAll((List<Paper>) new Gson().fromJson(paperListStr,type));
                Log.e("paperlist",paperList.toString());
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }



}
