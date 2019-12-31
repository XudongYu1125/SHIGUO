package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class NewResumeActivity extends AppCompatActivity {
    private Intent intent;
    private String resumeStr;
    private Resume resume;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvUniversity;
    private TextView tvExperience;
    private TextView tvAbility;
    private TextView tvResumeName;
    private LinearLayout linearLayout;
    private SharedPreferences sharedPreferences;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_resume);
        intent = getIntent();
        resumeStr = intent.getStringExtra("myresume");
        resume = new Gson().fromJson(resumeStr,Resume.class);
        Log.e("11",resumeStr);
        findViews();
        setContents(resume);
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTalk = new Intent();
                intentTalk.setClass(NewResumeActivity.this,ChatRecordActivity.class);
                intentTalk.putExtra("ownersId",user.getUserid()+";"+resume.getUserid());
                startActivity(intentTalk);
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
        tvResumeName = findViewById(R.id.tv_new_resume_resumename);
        tvName = findViewById(R.id.tv_new_resume_name);
        tvBirthday = findViewById(R.id.tv_new_resume_birthday);
        tvUniversity = findViewById(R.id.tv_new_resume_university);
        tvExperience = findViewById(R.id.tv_new_resume_experience);
        tvAbility = findViewById(R.id.tv_new_resume_ability);
        linearLayout = findViewById(R.id.my_talk);
    }
}
