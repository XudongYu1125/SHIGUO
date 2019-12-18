package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ResumeCenterActivity extends AppCompatActivity {
    private TextView tvMyRemuse;
    private TextView tvReceivedRemuse;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remuse_center);
        findViews();
        setOnclicked();
    }

    private void setOnclicked() {
        tvMyRemuse.setOnClickListener(new onClicked());
        tvReceivedRemuse.setOnClickListener(new onClicked());
    }

    private void findViews() {
        tvMyRemuse = findViewById(R.id.tv_my_resume);
        tvReceivedRemuse = findViewById(R.id.tv_received_resume);
    }
    private class onClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_my_resume:
                    intent = new Intent();
                    intent.setClass(ResumeCenterActivity.this,MyResumeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_received_resume:
                    sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
                    user = new Gson().
                            fromJson(sharedPreferences.getString("user",null),User.class);
                    if (user.getCompanyid()==0) {
                        Toast.makeText(getApplicationContext(),"您没有访问权限！",Toast.LENGTH_SHORT).show();
                    }else{
                        intent = new Intent();
                        intent.setClass(ResumeCenterActivity.this,ReceivedResumeActivity.class);
                        intent.putExtra("company",user.getCompanyid());
                        startActivity(intent);
                    }
                    break;
            }

        }
    }
}
