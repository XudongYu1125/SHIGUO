package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ResumeActivity extends AppCompatActivity {
    private Intent intent;
    private String resumeStr;
    private Resume resume;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvUniversity;
    private TextView tvExperience;
    private TextView tvAbility;
    private Button button;
    private Button btnDelete;
    private TextView tvResumeName;
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        intent = getIntent();
        resumeStr = intent.getStringExtra("myresume");
        resume = new Gson().fromJson(resumeStr,Resume.class);
        findViews();
        setContents(resume);
        Log.e("111",resume.toString());
        okHttpClient = new OkHttpClient();
        btnDelete.setOnClickListener(new onClicked());
        button.setOnClickListener(new onClicked());
    }

    private class onClicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_change_resume:
                    Intent intentChange = new Intent();
                    intentChange.setClass(getApplicationContext(),EditResumeActivity.class);
                    intentChange.putExtra("resume",resumeStr);
                    startActivityForResult(intentChange,100);
                    break;
                case R.id.btn_delete_resume:
                    setResult(201,intent);

                    deleteData(resume);
                    break;
            }
        }
    }
    private void deleteData(Resume resume) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),new Gson().toJson(resume));
        Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_DELETE_MY_RESUME).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String delete = response.body().string();
                if (delete.equals("true")){
                    Log.e("111","删除成功");
                    finish();
                }else {
                    Log.e("111","删除失败");
                }
            }
        });
    }

    private void setContents(Resume resume) {
        tvResumeName.setText(resume.getResumename());
        tvName.setText(resume.getName());
        tvExperience.setText(resume.getExperience());
        tvAbility.setText(resume.getAbility());
        tvUniversity.setText(resume.getUniversity());
        tvBirthday.setText(resume.getBirthday());
    }

    private void findViews() {
        btnDelete = findViewById(R.id.btn_delete_resume);
        tvResumeName = findViewById(R.id.tv_resume_resumename);
        tvName = findViewById(R.id.tv_resume_name);
        tvBirthday = findViewById(R.id.tv_resume_birthday);
        tvUniversity = findViewById(R.id.tv_resume_university);
        tvExperience = findViewById(R.id.tv_resume_experience);
        tvAbility = findViewById(R.id.tv_resume_ability);
        button = findViewById(R.id.btn_change_resume);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode){
            case 101:
                String newResume = data.getStringExtra("newresume");
                resume = new Gson().fromJson(newResume,Resume.class);
                setContents(resume);
                Toast.makeText(ResumeActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent myIntent = new Intent();
            myIntent = new Intent(ResumeActivity.this, MyResumeActivity.class);
            startActivity(myIntent);
            this.finish();
        }
            return super.onKeyDown(keyCode, event);
    }
}
