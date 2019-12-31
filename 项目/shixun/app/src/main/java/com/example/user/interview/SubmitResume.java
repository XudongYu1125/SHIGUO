package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SubmitResume extends AppCompatActivity {

    private ArrayList <Resume> resumes = new ArrayList <> ( );
    private static ResumeRecyclerAdapter resumeRecyclerAdapter;
    private String ip = Constant.ip;
    private Gson gson;
    private User user;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.submit_resume_layout);
        gson = new Gson ( );
        sharedPreferences = getSharedPreferences ("loginInfo" , MODE_PRIVATE);
        user = gson.fromJson (sharedPreferences.getString ("user" , null) , User.class);
        resumes = new ArrayList <> ();
        initResume ( );
        setBack ( );
        onAddNewResume ( );
    }

    public void setBack() {

        Toolbar toolbar = findViewById (R.id.submit_resume_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ( );
            }
        });


    }

    public void onAddNewResume() {

        ImageView imageView = findViewById (R.id.add_new_resume);
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (SubmitResume.this , AddNewResume.class);
                startActivityForResult (intent , 0);
            }
        });


    }

    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        switch (resultCode) {
            case 0:

                boolean status = data.getBooleanExtra ("状态" , false);
                if (status) {
                    initResume ();
                }
                break;
            default:
                break;
        }

        super.onActivityResult (requestCode , resultCode , data);
    }

    private void initRecyclerView() {

        final RecyclerView recyclerView = findViewById (R.id.rv_submit_resume);

        resumeRecyclerAdapter = new ResumeRecyclerAdapter (resumes);
        recyclerView.setLayoutManager (new LinearLayoutManager (this , LinearLayout.VERTICAL , false));
        recyclerView.addItemDecoration (new DividerItemDecoration (this , DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter (resumeRecyclerAdapter);
        resumeRecyclerAdapter.setOnItemClickListener (( view , data ) -> {

            Intent intent = new Intent (SubmitResume.this , ResumeInfo.class);
            Intent intent1 = getIntent ();
            Gson gson = new Gson ( );
            Position p = gson.fromJson (intent1.getStringExtra ("职务信息"),Position.class);
            intent.putExtra ("简历信息" , gson.toJson (data));
            intent.putExtra ("目标用户信息",gson.toJson (p));
            startActivity (intent);


        });


    }


    private void initResume() {
        new Thread (() -> {
            resumes = new ArrayList <> ( );
            String str = String.valueOf (user.getUserid ( ));
            Gson gson = new Gson ( );
            String urlStrPosition = "http://" + ip + ":8080/ShiguoServerSystem/Resume/SearchByUserId";
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


                InputStream inputStream = conn.getInputStream ( );
                byte[] buffer = new byte[2048];
                int len;
                StringBuffer stringBuffer = new StringBuffer ( );
                while ((len = inputStream.read (buffer)) != -1) {
                    stringBuffer.append (new String (buffer , 0 , len));
                }
                List <Resume> re = gson.fromJson (stringBuffer.toString ( ) , new TypeToken<List<Resume>> (){}.getType ());
                resumes.addAll (re);
                inputStream.close ( );//关闭数据流
                conn.disconnect ( );
                Message message = new Message ( );
                message.what = 0;
                handler.sendMessage (message);
            } catch (Exception e) {
                e.printStackTrace ( );
            }


        }).start ( );


    }


    private Handler handler = new Handler ( ) {
        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage (msg);
            switch (msg.what) {
                case 0:
                    initRecyclerView ( );
                    break;
                default:
                    break;
            }
        }
    };


}
