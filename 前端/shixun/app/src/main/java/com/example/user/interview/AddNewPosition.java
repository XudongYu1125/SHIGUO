package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.user.interview.InterviewFragment.setCompanyImg;
import static com.example.user.interview.InterviewFragment.setCompanyName;


/**
 * @author june
 */
public class AddNewPosition extends AppCompatActivity {

    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;
    private String imgUrl;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.add_new_job);
        gson = new Gson ( );
        sharedPreferences = getSharedPreferences ("loginInfo" , MODE_PRIVATE);
        user = gson.fromJson (sharedPreferences.getString ("user" , null) , User.class);
        setBack ( );
        setSubmit ( );
    }


    private void setBack() {
        Toolbar toolbar = findViewById (R.id.add_job_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Position p = getPosition ( );
                Intent intent = new Intent ( );
                Gson gson = new Gson ( );
                intent.putExtra ("发布新的职位" , gson.toJson (p));
                intent.putExtra ("状态" , false);
                AddNewPosition.this.setResult (0 , intent);
                AddNewPosition.this.finish ( );
            }
        });
    }


    private void setSubmit() {
        final ImageView submit = findViewById (R.id.new_job_submit);
        submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {


                Position p = getPosition ( );
                Intent intent = new Intent ( );
                Gson gson = new Gson ( );
                EditText edCompanyName = findViewById (R.id.new_job_ed_companyName);
                getImg ();
                String companyName = edCompanyName.getText ( ).toString ( );
                intent.putExtra ("发布新的职位" , gson.toJson (p));
                intent.putExtra ("公司名" , companyName);
                intent.putExtra ("状态" , true);
                intent.putExtra ("公司图",imgUrl);
                submitPosition (p);
                AddNewPosition.this.setResult (0 , intent);
                AddNewPosition.this.finish ( );


            }
        });
    }

    private Position getPosition() {
        Position position = new Position ( );
        EditText edPositionName = findViewById (R.id.new_job_ed_jobName);
        EditText edSalary = findViewById (R.id.new_job_ed_salary);
        EditText edPlace = findViewById (R.id.new_job_ed_place);
        EditText edRequest = findViewById (R.id.new_job_ed_request);
        String positionName = edPositionName.getText ( ).toString ( );

        String salary = edSalary.getText ( ).toString ( );
        position.setUserid (user.getUserid ());
        position.setName (positionName);
        position.setSalary (salary);
        position.setPlace (edPlace.getText ( ).toString ( ));
        position.setRequest (edRequest.getText ( ).toString ( ));

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date = new Date (System.currentTimeMillis ());
        String time = dateFormat.format (date);
        position.setDate (time);
        return position;
    }


    private void getImg() {

        new Thread (new Runnable ( ) {
            @Override
            public void run() {
                String userId = String.valueOf (user.getUserid ( ));

                String urlStrPosition = "http://" + Constant.BASE_IP + ":8080/ShiguoServerSystem/Company/SearchByUserId";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

                    conn.connect ( );

                    conn.getOutputStream ( ).write (userId.getBytes ("UTF-8"));
                    conn.getOutputStream ( ).flush ( );


                    InputStream inputStream = conn.getInputStream ( );
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer ( );
                    while ((len = inputStream.read (buffer)) != -1) {
                        stringBuffer.append (new String (buffer , 0 , len));
                    }


                    imgUrl = stringBuffer.toString ( );

                    inputStream.close ( );//关闭数据流
                    conn.disconnect ( );
                    Message message = new Message ( );
                    message.what = 0;
                    handler.sendMessage (message);


                } catch (Exception e) {
                    e.printStackTrace ( );
                }


            }
        }).start ( );
    }


    private void submitPosition(Position p){

        final String str = gson.toJson (p);

        new Thread (new Runnable ( ) {
            @Override
            public void run() {



                String urlStrPosition = "http://" + Constant.ip + ":8080/ShiguoServerSystem/Position/Add";
                try {
                    URL url = new URL (urlStrPosition);
                    //获得连接

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection ( );

                    conn.setRequestMethod ("POST");

                    conn.setDoOutput (true);

                    conn.setDoInput (true);

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
                case  0:
                    break;
                case  1:
                    break;
                default:
                    break;
            }
        }
    };




}

