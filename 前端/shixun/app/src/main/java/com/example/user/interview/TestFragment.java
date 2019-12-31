package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestFragment extends Fragment {
    private TextView etType;
    private LinearLayout llExam;
    private LinearLayout llModel;
    private LinearLayout llFace;
    private LinearLayout llChoose;
    private LinearLayout llShort;
    private LinearLayout llBlanks;
    private Intent intent;
    private String type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_test,container,false );
        findViews(view);
        setOnclicked();
        return view;
    }

    private void setOnclicked() {
        llExam.setOnClickListener(new onclicked());
        llModel.setOnClickListener(new onclicked());
        llFace.setOnClickListener(new onclicked());
        llChoose.setOnClickListener(new onclicked());
        llShort.setOnClickListener(new onclicked());
        llBlanks.setOnClickListener(new onclicked());
        etType.setOnClickListener(new onclicked());
    }

    private void findViews(View view) {
        etType = view.findViewById(R.id.et_type);
        llExam = view.findViewById(R.id.ll_exam);
        llModel = view.findViewById(R.id.ll_model);
        llFace = view.findViewById(R.id.ll_face);
        llChoose = view.findViewById(R.id.choose_question);
        llShort = view.findViewById(R.id.short_question);
        llBlanks = view.findViewById(R.id.blanks_question);
    }
    private class onclicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_exam:
                    intent = new Intent();
                    intent.setClass(getContext(),ExamPaperActivity.class);
                    break;
                case R.id.ll_model:
                    intent = new Intent();
                    intent.setClass(getContext(),ModelPaperActivity.class);
                    break;
                case R.id.ll_face:
                    intent = new Intent();
                    intent.setClass(getContext(),HandBookListActivity.class);
                    break;
                case R.id.choose_question:
                    intent = new Intent();
                    intent.setClass(getContext(),ProblemListActivity.class);
                    intent.putExtra("type",0);
                    break;
                case R.id.short_question:
                    intent = new Intent();
                    intent.setClass(getContext(),ProblemListActivity.class);
                    intent.putExtra("type",2);
                    break;
                case R.id.blanks_question:
                    intent = new Intent();
                    intent.setClass(getContext(),ProblemListActivity.class);
                    intent.putExtra("type",1);
                    break;
                case R.id.et_type:
                    intent = new Intent();
                    intent.setClass(getContext(),SerchProblemActivity.class);
                    break;

            }
            startActivity(intent);
        }
    }
}
