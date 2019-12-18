package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //用户信息
    private EditText nickname;
    private EditText password;
    //记住密码和自动登录
    private SharedPreferences sharedPreferences = null;
    private CheckBox automatic = null;
    private CheckBox remember = null;
    private TextView copyright = null;
    private ImageView symbol = null;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        init();
        setCopyRight();
        isAutoLogin();

       nickname.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowSymbolTask showSymbolTask = new ShowSymbolTask();
                showSymbolTask.execute(s.toString());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }

    //授权友盟
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //初始化控件
    public void init() {
        gson = new Gson();
        symbol = findViewById(R.id.symbol);
        nickname = findViewById(R.id.et_loginNickname);
        password = findViewById(R.id.et_loginPassword);
        automatic = findViewById(R.id.automatic);
        remember = findViewById(R.id.remember);
        copyright = findViewById(R.id.copyright);
    }

    //Handler
    Handler imgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                Log.e("执行", "onStart");
                showSymbol(Constant.URL_ICON + msg.obj.toString());
            }else{
                Log.e("不执行","  ");
                symbol.setImageResource(R.drawable.unlogin);
            }
        }
    };

    //显示头像
    public void showSymbol(String imageUrl) {
        RequestOptions options = new RequestOptions().circleCrop();
        Glide.with(this)
                .load(imageUrl)
                .apply(options)//应用请求选项
                .into(symbol);
    }

    //点击事件
    public void onClickMain(View v) {
        switch (v.getId()) {
            //登录
            case R.id.btn_login:
                if (nickname.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "请输入完整的账户信息", Toast.LENGTH_SHORT).show();
                } else {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute(Constant.URL_LOCAL + "LoginByNickname");
                }
                break;
            //手机号登录
            case R.id.btn_login_phone:
                Intent toPhone = new Intent(MainActivity.this, LoginByPhoneActivity.class);
                startActivity(toPhone);
                break;
            //注册
            case R.id.tv_register:
                Intent toRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(toRegister);
                break;
            //忘记密码
            case R.id.tv_find_psw:
                Intent toFind = new Intent(MainActivity.this, FindPasswordActivity.class);
                startActivity(toFind);
                break;
            //拉取QQ登录
            case R.id.login_qq:

                UMAuthListener authListener = new UMAuthListener() {
                    /**
                     * @desc 授权开始的回调
                     * @param platform 平台名称
                     */
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    /**
                     * @desc 授权成功的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param data 用户资料返回
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                        Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();

                        User user = new User();
                        Log.e("123",data.toString());
                        user.setQQ(data.get("aid").toString());
                        user.setNickname("QQ用户_" + data.get("aid").toString());
                        String userStr = gson.toJson(user);

                        LoginByQQTask loginByQQTask = new LoginByQQTask();
                        loginByQQTask.execute(Constant.URL_LOCAL + "LoginByQQ", userStr);
                        Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
                        startActivity(intent);

                    }

                    /**
                     * @desc 授权失败的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {

                        Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @desc 授权取消的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform, int action) {
                        Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
                    }
                };
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                umShareAPI.doOauthVerify(MainActivity.this, SHARE_MEDIA.QQ, authListener);

                break;


        }

    }

    //异步任务
    //1.拉取第三方登录
    public class LoginByQQTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //用post方式传递数据
                httpURLConnection.setRequestMethod("POST");
                //发送数据到服务器端
                OutputStream os = httpURLConnection.getOutputStream();

                //发送Gson格式的字符串
                os.write(strings[1].getBytes());

                //接收服务器端发回的数据
                InputStream is = httpURLConnection.getInputStream();
                byte[] btr = new byte[1024];
                int len;

                while ((len = is.read(btr)) != -1) {
                    result = new String(btr, 0, len);
                }
                is.close();
                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //Gson gson = new Gson();
            UserStatus userStatus = gson.fromJson(result, UserStatus.class);

            switch (userStatus.getStatus()) {
                case "0":
                    Log.e("----------", "QQ登录");
                    //在需要写入数据的地方创建Editor对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    User user = userStatus.getUser();
                    String userStr = gson.toJson(user);
                    editor.putString("user", userStr.toString());
                    //put并没有真正生效，必须commit
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case "1":
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    break;

            }
            super.onPostExecute(result);
        }
    }

    //2.账号密码登录
    public class LoginTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //用post方式传递数据
                httpURLConnection.setRequestMethod("POST");
                //发送数据到服务器端
                OutputStream os = httpURLConnection.getOutputStream();

                //发送Gson格式的字符串
                User user = new User();
                user.setNickname(nickname.getText().toString());
                user.setPassword(password.getText().toString());
                String userStr = gson.toJson(user);
                os.write(userStr.getBytes());

                //接收服务器端发回的数据
                InputStream is = httpURLConnection.getInputStream();
                byte[] btr = new byte[1024];
                int len;

                while ((len = is.read(btr)) != -1) {
                    result = new String(btr, 0, len);
                }
                is.close();
                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            UserStatus userStatus = gson.fromJson(result, UserStatus.class);
            switch (userStatus.getStatus()) {
                case "0":
                    Log.e("----------", "账号密码登录");
                    //在需要写入数据的地方创建Editor对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    User user = userStatus.getUser();
                    String userStr = gson.toJson(user);
                    editor.putString("user", userStr.toString());
                    if (automatic.isChecked()) {
                        //保存自动登录信息
                        editor.putBoolean("isAuto", true);//自动登录框
                        editor.putString("nickname", nickname.getText().toString());
                        editor.putString("password", password.getText().toString());
                    } else {
                        editor.putBoolean("isAuto", false);
                        editor.remove("password");
                    }
                    if (remember.isChecked()) {
                        //保存记住密码
                        editor.putString("password", password.getText().toString());
                        editor.putString("nickname", nickname.getText().toString());
                        editor.putBoolean("isRemember", true);//记住密码
                    } else {
                        editor.putBoolean("isRemember", false);
                        editor.remove("password");
                    }
                    //put并没有真正生效，必须commit
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case "1":
                    Toast.makeText(MainActivity.this,"密码错误!",Toast.LENGTH_SHORT).show();
                    break;
                case "3":
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    break;
                case "2":
                    Toast.makeText(MainActivity.this,"账号不存在，请先注册！",Toast.LENGTH_SHORT);
                    break;

            }
            super.onPostExecute(result);
        }
    }

    //3.显示头像
    public class ShowSymbolTask extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String imgName = "";
            try {
                URL url = new URL(Constant.URL_LOCAL+"SearchImgByNickname");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //用post方式传递数据
                httpURLConnection.setRequestMethod("POST");
                //发送数据到服务器端
                OutputStream os = httpURLConnection.getOutputStream();
                Log.e("nickname",strings[0]);
                os.write(strings[0].getBytes());
                //接收服务器端发回的数据
                InputStream is = httpURLConnection.getInputStream();
                byte[] btr = new byte[1024];
                int len;

                if((len = is.read(btr)) != -1) {
                    imgName = new String(btr,0,len);
                }
                Message message = new Message();
                message.obj = (Object)imgName;
                Log.e("图片名",message.obj.toString());
                imgHandler.sendMessage(message);
                is.close();
                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imgName;
        }
    }

    //自动登录&&记住密码
    //读取文件（保存的一系列数据），实现记住密码自动登录
    private void isAutoLogin() {
        boolean isAuto = sharedPreferences.getBoolean("isAuto", false);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        if (isAuto) {
            String nicknameAuto = sharedPreferences.getString("nickname", "");
            String passwordAuto = sharedPreferences.getString("password", "");
            nickname.setText(nicknameAuto);
            password.setText(passwordAuto);
            automatic.setChecked(true);

            //模拟请求服务器端登录成功
            Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
            startActivity(intent);
            finish();
        }

        if (isRemember) {
            String nicknameAuto = sharedPreferences.getString("nickname", "");
            String passwordAuto = sharedPreferences.getString("password", "");
            nickname.setText(nicknameAuto);
            password.setText(passwordAuto);
            remember.setChecked(true);
        }
    }

    //设置版本号
    public void setCopyRight() {
        String str = "试过@2019 All Service Reserved";
        copyright.setTextSize(15);
        copyright.setText(Html.fromHtml(str));
    }

}

