package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

public class AddNewResume extends AppCompatActivity {

    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        setContentView (R.layout.add_new_resume);
        setOnBack ();
        setOnSubmit ();
    }


    private void setOnBack(){
        Toolbar toolbar = findViewById (R.id.add_resume_toolbar);

        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Resume resume = new Resume ();
                Intent intent = new Intent ();
                intent.putExtra ("新的简历",resume);
                intent.putExtra ("状态",false);
                AddNewResume.this.setResult (0,intent);
                finish ();

            }
        });

    }



    private void setOnSubmit(){

        ImageView im = findViewById (R.id.new_resume_submit);
        im.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                EditText edName = findViewById (R.id.new_resume_userName_ed);
                EditText edBirthday = findViewById (R.id.new_resume_userBirth_ed);
                EditText edUniversity = findViewById (R.id.new_resume_userUniversity_ed);
                EditText edExperience = findViewById (R.id.new_resume_userExperience_ed);
                EditText edAbility = findViewById (R.id.new_resume_userAbility_ed);
                Resume resume = new Resume ();
                //resume.setUserid (user.getUserid ());
                resume.setName (edName.getText ().toString ());
                resume.setBirthday (edBirthday.getText ().toString ());
                resume.setUniversity (edUniversity.getText ().toString ());
                resume.setExperience (edExperience.getText ().toString ());
                resume.setAbility (edAbility.getText ().toString ());
                Intent intent = new Intent ();
                intent.putExtra ("新的简历",resume);
                intent.putExtra ("状态",true);
                AddNewResume.this.setResult (0,intent);
                finish ();




            }
        });



    }
}
