package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author june
 */
public class ApplyPositionUserInfo extends AppCompatActivity {
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.apply_position_info_layout);
        setChat ();
        setBack ();
        showItem ();
    }







    private void setChat(){

        ImageView imageView = findViewById (R.id.chat);
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (ApplyPositionUserInfo.this,TabHostActivity.class);
                intent.putExtra ("id",1);
                startActivity (intent);
            }
        });


    }


    private void showItem(){

        Intent intent = getIntent ();
        JobSearch jobSearch = intent.getParcelableExtra ("求职信息");
        TextView tv_date = findViewById (R.id.apply_info_date);
        TextView tv_position = findViewById (R.id.apply_info_position);
        TextView tv_place = findViewById (R.id.apply_info_place);
        TextView tv_salary = findViewById (R.id.apply_info_salary);
        TextView tv_exp = findViewById (R.id.apply_info_experience);
        tv_date.setText (jobSearch.getDate ());
        tv_position.setText (jobSearch.getPosition ());
        tv_place.setText (jobSearch.getPlace ());
        tv_salary.setText (jobSearch.getSalary ());
        tv_exp.setText (jobSearch.getRequest ());




    }


    private void setBack(){

        Toolbar toolbar = findViewById (R.id.apply_job_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        });


    }
}
