package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class ProblemActivity extends AppCompatActivity{
    private Intent intent;
    private String problemStr;
    private Problem problem;
    private TextView tvProblem;
    private Button btnAnswer;
    private Intent intentAnswer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        intent = getIntent();
        problemStr = intent.getStringExtra("problem");
        problem = new Gson().fromJson(problemStr,Problem.class);
        tvProblem = findViewById(R.id.tv_paper);
        tvProblem.setText(problem.getContent());
        btnAnswer = findViewById(R.id.btn_paper_answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentAnswer = new Intent();
                intentAnswer.setClass(getApplicationContext(),ProblemAnswerActivity.class);
                intentAnswer.putExtra("answer",problem.getAnswer());
                startActivity(intentAnswer);
            }
        });
    }
}
