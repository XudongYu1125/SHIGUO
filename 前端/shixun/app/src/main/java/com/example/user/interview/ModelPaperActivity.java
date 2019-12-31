package com.example.user.interview;

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

public class ModelPaperActivity extends AppCompatActivity {
    private ListView lvModelPaper;
    private ModelPaperAdapter adapter;
    private List<Paper> paperList = new ArrayList<>();
    private OkHttpClient okHttpClient;
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

        okHttpClient = new OkHttpClient();
        requestData();
    }

    private void requestData() {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),"2");
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_PAPER).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String paperListStr = response.body().string();
                Type type = new TypeToken<List<Paper>>(){}.getType();
                paperList.addAll((List<Paper>) new Gson().fromJson(paperListStr,type));
                Log.e("model",paperList.toString());
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }

    private void findViews() {
        lvModelPaper = findViewById(R.id.lv_exam_paper);
        adapter = new ModelPaperAdapter(paperList,R.layout.paper_listview_item,ModelPaperActivity.this);
        lvModelPaper.setAdapter(adapter);
    }

}
