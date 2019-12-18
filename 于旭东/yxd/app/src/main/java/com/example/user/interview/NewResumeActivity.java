package com.example.user.interview;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    }

    private void setContents(Resume resume) {
        tvResumeName.setText(resume.getResumename());
        Log.e("111",tvResumeName.getText().toString());
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
    }
}
