package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class HandBookActivity extends AppCompatActivity {
    private Intent intent;
    private TextView tvHandbook;
    private HandBook handbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_book);
        intent = getIntent();
        String handbookStr = intent.getStringExtra("handbook");
        handbook = new Gson().fromJson(handbookStr,HandBook.class);
        tvHandbook = findViewById(R.id.tv_handbook);
        tvHandbook.setText(handbook.getContent());
    }
}
