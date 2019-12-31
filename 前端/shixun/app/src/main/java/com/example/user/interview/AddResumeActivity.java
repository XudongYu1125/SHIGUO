package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddResumeActivity extends AppCompatActivity {
    private Resume resume;
    private EditText etName;
    private EditText etBirthday;
    private EditText etUniversity;
    private EditText etExperience;
    private EditText etAbility;
    private Button button;
    private User user;
    private Intent intent;
    private  EditText etResumeName;
    private OkHttpClient okHttpClient;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resume);
        resume = new Resume();
        findViews();
        intent = getIntent();
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        okHttpClient = new OkHttpClient();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resume.setUserid(user.getUserid());
                resume.setName(etName.getText().toString());
                resume.setAbility(etAbility.getText().toString());
                resume.setBirthday(etBirthday.getText().toString());
                resume.setExperience(etExperience.getText().toString());
                resume.setUniversity(etUniversity.getText().toString());
                resume.setResumename(etResumeName.getText().toString());
                intent.putExtra("result",1);
                setResult(101,intent);
                requestData(resume);
            }
        });
    }
    private void findViews() {
        etName = findViewById(R.id.et_resume_name);
        etBirthday = findViewById(R.id.et_resume_birthday);
        etUniversity = findViewById(R.id.et_resume_university);
        etExperience = findViewById(R.id.et_resume_experience);
        etAbility = findViewById(R.id.et_resume_ability);
        button = findViewById(R.id.btn_ok);
        etResumeName = findViewById(R.id.et_resume_resumename);
    }
    private void requestData(Resume resume) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),new Gson().toJson(resume));
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_ADD_MY_RESUME).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String change = response.body().string();
                Log.e("111",change.toString());
                if (change.equals("true")){
                    Log.e("11","chengg");
                    finish();

                }else {
                    Log.e("11","shibai");
                }
            }
        });
    }
}
