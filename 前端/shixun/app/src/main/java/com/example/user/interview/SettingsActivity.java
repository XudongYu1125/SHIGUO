package com.example.user.interview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout llChange;
    private LinearLayout llAbout;
    private TextView btnLogout;
    private Intent intent;
    private SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        setOnclicked();
    }

    private void setOnclicked() {
        llChange.setOnClickListener(new onClicked());
        llAbout.setOnClickListener(new onClicked());
        btnLogout.setOnClickListener(new onClicked());
    }

    private void findViews() {
        llChange = findViewById(R.id.ll_change_password);
        llAbout = findViewById(R.id.ll_about);
        btnLogout = findViewById(R.id.tv_logout);
    }

    private class onClicked implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_change_password:
                    intent = new Intent();
                    intent.setClass(SettingsActivity.this,ChangePasswordActivity.class);
                    break;
                case R.id.ll_about:
                    intent = new Intent();
                    intent.setClass(SettingsActivity.this,AboutActivity.class);
                    break;
                case R.id.tv_logout:
                    sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("isRemember");
                    editor.remove("password");
                    editor.remove("nickname");
                    editor.remove("isAuto");
                    intent = new Intent();
                    intent.setClass(SettingsActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;
            }
            startActivity(intent);
        }
    }
}
