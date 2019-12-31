package com.example.user.interview;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author june
 */
public class AddNewApply extends AppCompatActivity {

    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private Toolbar toolbar;
    private ImageView submit;




    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        setContentView (R.layout.add_new_apply);
        setBack ();
        setSubmit ();

    }



/**    取消发布*/
    private void setBack(){
        toolbar = findViewById (R.id.add_apply_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                JobSearch jobSearch = new JobSearch ();
                Intent intent = new Intent ();
                Gson gson = new Gson ();
                intent.putExtra ("发布新的求职",gson.toJson (jobSearch));
                intent.putExtra ("状态",false);
                AddNewApply.this.setResult (1,intent);
                finish ();
            }
        });
    }

/**发布求职*/
    private  void setSubmit(){
        submit = findViewById (R.id.new_apply_send);
        submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                JobSearch jobSearch = getJobSearch ();
                Intent intent = new Intent ();
                Gson gson = new Gson ();
                intent.putExtra ("发布新的求职",gson.toJson (jobSearch));
                intent.putExtra ("状态",true);
                Log.e ("ssss",jobSearch.toString ());
                submitJobSearch (jobSearch);
                AddNewApply.this.setResult (1,intent);
                finish ();
            }
        });
    }


    private JobSearch getJobSearch(){
        final EditText edPosition = findViewById (R.id.new_apply_ed_job);
        final EditText edPlace = findViewById (R.id.new_apply_ed_place);
        final EditText edSalary = findViewById (R.id.new_apply_ed_salary);
        final EditText edExperience = findViewById (R.id.new_apply_ed_experience);
        String position = edPosition.getText ().toString ();
        String place = edPlace.getText ().toString ();
        String salary = edSalary.getText ().toString ();
        String request = edExperience.getText ().toString ();
        JobSearch jobSearch = new JobSearch ();
        jobSearch.setUserid (user.getUserid ());

        jobSearch.setPosition (position);
        jobSearch.setPlace (place);
        jobSearch.setSalary (salary);
        jobSearch.setExperience (request);

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date = new Date (System.currentTimeMillis ());
        String time = dateFormat.format (date);

        jobSearch.setDate (time);
        return jobSearch;
    }


    private void submitJobSearch(JobSearch jobSearch){

        final String str = gson.toJson (jobSearch);
        new Thread (new Runnable ( ) {
            @Override
            public void run() {



                String urlStrPosition = "http://" + Constant.ip + ":8080/ShiguoServerSystem/JobSearch/Add";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);
                    Log.e ("ssssaaaaaa",str);
                    conn.connect ( );

                    conn.getOutputStream ( ).write (str.getBytes ("UTF-8"));
                    conn.getOutputStream ( ).flush ( );

                    conn.getInputStream ();



                    conn.disconnect ( );
                    Message message = new Message ( );
                    message.what = 1;
                    handler.sendMessage (message);


                } catch (Exception e) {
                    e.printStackTrace ( );
                }


            }
        }).start ( );

    }

    private Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                default:
                    break;
            }
        }
    };


}
