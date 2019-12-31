package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * @author june
 */
public class PositionInfo extends AppCompatActivity {

    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.job_info);
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        Intent intent = getIntent ();
        String s = intent.getStringExtra ("职位信息");
        Position p = gson.fromJson (s,Position.class);
        showItem (p);
        onClick (p);
    }


    private void showItem(Position p){

        TextView tv_name = findViewById (R.id.position_name);
        TextView tv_salary = findViewById (R.id.position_salary);
        TextView tv_request = findViewById (R.id.position_request);
        tv_name.setText (p.getName ());
        tv_salary.setText (p.getSalary ());
        Log.e ("position",p.toString ());
        tv_request.setText (p.getRequest ());

    }

    private void onClick( final Position p){

        Toolbar toolbar = findViewById (R.id.add_apply_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        });

        Button button = findViewById (R.id.job_info_submit);
        button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                if (user.getCompanyid ()==0){
                    Intent intent = new Intent (PositionInfo.this,SubmitResume.class);
                    intent.putExtra ("职务信息",gson.toJson (p));
                    startActivity (intent);
                }else {

                    Toast.makeText (getApplicationContext (),"您是面试官认证用户，不能进行求职操作！",Toast.LENGTH_LONG).show ();

                }

            }
        });




    }


}
