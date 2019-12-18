package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EditResumeActivity extends AppCompatActivity {
    private Intent intent;
    private String resumeStr;
    private Resume resume;
    private EditText etName;
    private EditText etBirthday;
    private EditText etUniversity;
    private EditText etExperience;
    private EditText etAbility;
    private EditText etResumeName;
    private Button button;
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resume);
        intent = getIntent();
        resumeStr = intent.getStringExtra("resume");
        resume = new Gson().fromJson(resumeStr,Resume.class);
        Log.e("re",resume.toString());
        findViews();
        okHttpClient = new OkHttpClient();

        button.setOnClickListener(new onClicked());
    }

    private class onClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_ok:
                    resume.setName(etName.getText().toString());
                    resume.setAbility(etAbility.getText().toString());
                    resume.setBirthday(etBirthday.getText().toString());
                    resume.setExperience(etExperience.getText().toString());
                    resume.setUniversity(etUniversity.getText().toString());
                    resume.setResumename(etResumeName.getText().toString());
                    intent.putExtra("newresume",new Gson().toJson(resume));
                    setResult(101,intent);
                    requestData(resume);
                    break;
            }
        }
    }


    private void requestData(Resume resume) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),new Gson().toJson(resume));
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_CHANGE_MY_RESUME).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String change = response.body().string();
                Log.e("111",change);
                if (change.equals("true")){
                    Log.e("111","修改成功");
                    finish();
                }else {
                    Log.e("111","修改失败");
                }
            }
        });
    }

    private void findViews() {
        etName = findViewById(R.id.et_resume_name);
        etBirthday = findViewById(R.id.et_resume_birthday);
        etUniversity = findViewById(R.id.et_resume_university);
        etExperience = findViewById(R.id.et_resume_experience);
        etAbility = findViewById(R.id.et_resume_ability);
        etResumeName = findViewById(R.id.et_resume_resumename);
        button = findViewById(R.id.btn_ok);
        etName.setText(resume.getName());
        etExperience.setText(resume.getExperience());
        etAbility.setText(resume.getAbility());
        etUniversity.setText(resume.getUniversity());
        etBirthday.setText(resume.getBirthday());
        etResumeName.setText(resume.getResumename());
    }
}
