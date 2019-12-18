package com.example.user.interview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class SerchProblemActivity extends AppCompatActivity {
    private EditText etSerch;
    private OkHttpClient okHttpClient;
    private List<Problem> problems = new ArrayList<>();
    private ListView listView;
    private ProblemListAdapter adapter;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            findViews();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch_problem);
        etSerch = findViewById(R.id.et_serch);
        listView = findViewById(R.id.lv_problem);
        okHttpClient = new OkHttpClient();
        searchListener();
    }

    private void findViews() {
        adapter = new ProblemListAdapter(problems, R.layout.paper_listview_item, SerchProblemActivity.this);
        listView.setAdapter(adapter);
    }

    private void searchListener(){
        etSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("111",s.toString());
                if(!s.toString().equals("")) {
                    requestData(s.toString());
                }else {
                    requestData("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void requestData(String s) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),s);
        Request request = new Request.Builder().url(Constant.BASE_IP+Constant.URL_SERCH_PROBLEM).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String problemStr = response.body().string();
                if(!problemStr.equals("")) {
                    Log.e("333", problemStr);
                    Type type = new TypeToken<List<Problem>>() {
                    }.getType();
                    problems.clear();
                    problems.addAll((List<Problem>) new Gson().fromJson(problemStr, type));
                }else{
                    problems.clear();
                }
                Message message = new Message();
                message.obj = "";
                myHandler.sendMessage(message);
            }
        });
    }
}
