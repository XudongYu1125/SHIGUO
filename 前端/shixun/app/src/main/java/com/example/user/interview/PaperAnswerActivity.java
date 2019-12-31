package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaperAnswerActivity extends AppCompatActivity {
    private TextView tvAnswer;
    private List<Problem> problems = new ArrayList<>();
    private String answerStr ="";
    private String answer =null;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_answer);
        tvAnswer = findViewById(R.id.tv_paper_answer);
        intent = getIntent();
        //获得PaperActivity传来的problem答案
       answer = intent.getStringExtra("answer");
        Log.e("problem",answer);
        Type type = new TypeToken<List<Problem>>(){}.getType();
        problems.addAll((List<Problem>) new Gson().fromJson(answer,type));
        for (int i = 0;i<problems.size();i++) {
            answerStr = answerStr + (i+1)+"、"+problems.get(i).getAnswer() +"\n";
        }
        tvAnswer.setText(answerStr);
    }
}
