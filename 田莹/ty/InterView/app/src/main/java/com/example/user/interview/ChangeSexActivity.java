package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeSexActivity extends AppCompatActivity {

    private Intent intent;
    private TextView btnMale;
    private TextView btnFemale;
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sex);
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);

        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        intent = getIntent();
        findViews();
        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("changsex","男");
                setResult(201,intent);
                okHttpClient = new OkHttpClient();
                user.setSex("男");
                requestData(user);
            }
        });
        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("changsex","女");
                setResult(201,intent);
                okHttpClient = new OkHttpClient();
                user.setSex("女");
                requestData(user);
            }
        });
    }

    private void requestData(final User user) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),new Gson().toJson(user));
        final Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_Edit_PERSONAL).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("true")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user",new Gson().toJson(user));
                    editor.commit();
                    Log.e("222","成功");
                    finish();
                }else {
                    Log.e("111","shibai");
                }
            }
        });
    }

    private void findViews() {
        btnFemale = findViewById(R.id.btn_change_female);
        btnMale = findViewById(R.id.btn_change_male);
    }
}
