package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

public class MyJobSearchActivity extends AppCompatActivity {
    private Intent intent;
    private JobSearch jobSearch;
    private TextView tvName;
    private TextView tvSalary;
    private TextView tvTime;
    private TextView tvPlace;
    private TextView tvExperience;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_search);
        intent = getIntent();
        String positionStr = intent.getStringExtra("job");
        jobSearch = new Gson().fromJson(positionStr,JobSearch.class);
        findViews();
        tvName.setText(jobSearch.getPosition());
        tvSalary.setText(jobSearch.getSalary());
        tvTime.setText(jobSearch.getDate());
        tvPlace.setText(jobSearch.getPlace());
        //tvExperience.setText(jobSearch.getExperience());
    }

    private void findViews() {
        tvName = findViewById(R.id.tv_request_name1);
        tvSalary = findViewById(R.id.tv_request_salary1);
        tvTime = findViewById(R.id.tv_request_time1);
        tvPlace = findViewById(R.id.tv_request_place1);
        tvExperience = findViewById(R.id.tv_request_experience);
    }
}
