package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class ResumeInfo extends AppCompatActivity {

    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private String ip = Constant.ip;
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.resume_info);
        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        Intent intent = getIntent ();
        String s = intent.getStringExtra ("简历信息");
        Gson gson = new Gson ();
        Resume re = gson.fromJson (s,Resume.class);
        showItem (re);
        onSubmit (re);
        onBack ();

    }

    private void showItem(Resume re){

        TextView tv = findViewById (R.id.title_toolbar);
        TextView tv_name = findViewById (R.id.resume_info_name);
        TextView tv_birthday = findViewById (R.id.resume_info_birthday);
        TextView tv_university = findViewById (R.id.resume_info_university);
        TextView tv_experience = findViewById (R.id.resume_info_exp);
        TextView tv_ability = findViewById (R.id.resume_info_ability);
        String str = "<font color='#fffffff'>"+re.getResumename ()+"</font>";
        tv.setText(Html.fromHtml (str));
        tv_name.setText (re.getName ());
        tv_birthday.setText (re.getBirthday ());
        tv_university.setText (re.getUniversity ());
        tv_experience.setText (re.getExperience ());
        tv_ability.setText (re.getAbility ());


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


    public void onSubmit( final Resume re) {

        Button button = findViewById (R.id.submit_resume_submit);
        button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Toast.makeText (getApplicationContext ( ) , "提交成功！" , Toast.LENGTH_LONG).show ( );
                sendResume (re);

            }
        });

    }



    private void sendResume(Resume re){

        Intent intent = getIntent ();
        Position p = gson.fromJson (intent.getStringExtra ("目标用户信息"),Position.class);
        final String senderid = String.valueOf (user.getUserid ());
        final String receiverid = String.valueOf (p.getUserid ());
        final String resumeid = String.valueOf (re.getResumeid ());

        new Thread (new Runnable ( ) {
            @Override
            public void run() {

                JSONObject object = new JSONObject();
                try {
                    object.put("senderid", senderid);
                    object.put ("receiverid",receiverid);
                    object.put ("resumeid",resumeid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem//ResumeApplication/AddBySRRId";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    conn.getOutputStream ( ).write (object.toString ().getBytes ("UTF-8"));
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
