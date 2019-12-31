package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProblemAnswerActivity extends AppCompatActivity {
    private Intent intent;
    private String answerStr;
    private TextView tvAnswer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_answer);
        intent = getIntent();
        answerStr = intent.getStringExtra("answer");
        tvAnswer = findViewById(R.id.tv_paper_answer);
        tvAnswer.setText(answerStr);
    }
}
