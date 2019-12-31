package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

/**
 * @author june
 */
public class ApplyPositionUserInfo extends AppCompatActivity {
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private JobSearch jobSearch;
    private User newUser;
    private String ip =Constant.ip;
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.apply_position_info_layout);
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        showItem ();
        setChat ();
        setBack ();
    }







    private void setChat(){

        ImageView imageView = findViewById (R.id.chat);
        imageView.setOnClickListener (v -> {
            if (user.getCompanyid () != 0){
                Intent intent = new Intent (ApplyPositionUserInfo.this,ChatRecordActivity.class);
                String ownerId = ""+user.getUserid ()+";"+jobSearch.getUserid ();
                intent.putExtra ("ownersId",ownerId);
                startActivityForResult (intent,1);
            }else {
                Toast.makeText(getApplicationContext(),"您不是面试官认证用户，无法与其他用户通讯！",Toast.LENGTH_LONG).show();
            }

        });



    }


    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {

        switch (resultCode){
            case 1:
                break;
        }


        super.onActivityResult (requestCode , resultCode , data);


    }

    private void showItem(){
        Intent intent = getIntent ();
        String u = intent.getStringExtra ("用户信息");
        newUser = gson.fromJson (u,User.class);
        String s = intent.getStringExtra ("求职信息");
        jobSearch = gson.fromJson (s,JobSearch.class);
        TextView tv_date = findViewById (R.id.apply_info_date);
        TextView tv_position = findViewById (R.id.apply_info_position);
        TextView tv_place = findViewById (R.id.apply_info_place);
        TextView tv_salary = findViewById (R.id.apply_info_salary);
        TextView tv_exp = findViewById (R.id.apply_info_experience);
        TextView tv_name = findViewById (R.id.apply_info_user_name);
        ImageView imageView = findViewById (R.id.apply_info_image);
        tv_name.setText (newUser.getNickname());
        RequestOptions requestOptions = new RequestOptions ().circleCrop();
        Glide.with (getApplicationContext ())
                .load ("http://" + ip + ":8080/ShiguoServerSystem/avatarimg/"+newUser.getImageurl ())
                .placeholder (R.drawable.unlogin)
                .error (R.drawable.error)
                .apply (requestOptions)
                .into (imageView);
        tv_date.setText (jobSearch.getDate ());
        tv_position.setText (jobSearch.getPosition ());
        tv_place.setText (jobSearch.getPlace ());
        tv_salary.setText (jobSearch.getSalary ());
        tv_exp.setText (jobSearch.getExperience ());
    }


    private void setBack(){

        Toolbar toolbar = findViewById (R.id.apply_job_toolbar);
        toolbar.setNavigationOnClickListener (v -> finish ());


    }
}
