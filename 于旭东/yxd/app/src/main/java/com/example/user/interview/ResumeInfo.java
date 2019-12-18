package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResumeInfo extends AppCompatActivity {

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.resume_info);
        onSubmit ();
        showItem ();
        onBack ();
    }

    private void showItem(){

        Intent intent = getIntent ();
        Resume re = intent.getParcelableExtra ("简历信息");
        int i = intent.getIntExtra("title",0);
        TextView tv_name = findViewById (R.id.resume_info_name);
        TextView tv_birthday = findViewById (R.id.resume_info_birthday);
        TextView tv_university = findViewById (R.id.resume_info_university);
        TextView tv_experience = findViewById (R.id.resume_info_exp);
        TextView tv_ability = findViewById (R.id.resume_info_ability);
        TextView tv_toolbarTitle = findViewById (R.id.title_toolbar);
        tv_name.setText (re.getName ());
        tv_birthday.setText (re.getBirthday ());
        tv_university.setText (re.getUniversity ());
        tv_experience.setText (re.getExperience ());
        tv_ability.setText (re.getAbility ());
        tv_toolbarTitle.setText ("我的简历"+i);


    }

    private void onBack(){

        Toolbar toolbar = findViewById (R.id.resume_info_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        });


    }


    public void onSubmit() {

        Button button = findViewById (R.id.submit_resume_submit);
        button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Toast.makeText (getApplicationContext ( ) , "提交成功！" , Toast.LENGTH_LONG).show ( );


            }
        });

    }









}
