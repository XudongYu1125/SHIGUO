package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

public class MyPositionActivity extends AppCompatActivity {
    private Intent intent;
    private Position position;
    private TextView tvName;
    private TextView tvSalary;
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_position);
        intent = getIntent();
        String positionStr = intent.getStringExtra("position");
        position = new Gson().fromJson(positionStr,Position.class);
        findViews();
        tvName.setText(position.getName());
        tvSalary.setText(position.getSalary());
        tvContent.setText(position.getRequest());
    }

    private void findViews() {
        tvName = findViewById(R.id.tv_position_name);
        tvSalary = findViewById(R.id.tv_position_salary);
        tvContent = findViewById(R.id.tv_positon_content);
    }
}
