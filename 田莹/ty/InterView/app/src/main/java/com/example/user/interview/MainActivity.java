package com.example.user.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private TextView register;
    private TextView findPwd;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        findId();
        login.setOnClickListener(new onclicked());
    }

    public void findId(){
        register = findViewById( R.id.tv_register );
        login = findViewById(  R.id.btn_login);
        findPwd = findViewById( R.id.tv_find_psw );
    }
    private class onclicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //登录
                case  R.id.btn_login:
                    login.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("111","111");
                            intent = new Intent( MainActivity.this, TabHostActivity.class );
                        }
                    } );
                    break;
                //注册
                case R.id.tv_register:
                    register.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent( MainActivity.this, RegisterActivity.class );
                        }
                    } );
                    break;
                //忘记密码
                case R.id.tv_find_psw:
                    findPwd.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent( MainActivity.this,FindPassword.class );
                        }
                    } );
                    break;
            }
            startActivity( intent );

        }
    }

}

