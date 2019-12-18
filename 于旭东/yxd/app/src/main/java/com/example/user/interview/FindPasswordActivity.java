package com.example.user.interview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class FindPasswordActivity extends AppCompatActivity {
    private EditText phoneNum;
    private EditText myCode;
    private EditText password;
    private String result = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        init();
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    //控件初始化
    public void init() {
        phoneNum = findViewById(R.id.et_telephoneFind);
        myCode = findViewById(R.id.et_codeFind);
        password = findViewById(R.id.et_pswFind);
    }

    public void onClickFind(View v) {
        switch (v.getId()) {
            //发送验证码
            case R.id.tv_findSendCode:
                SMSSDK.getVerificationCode("86", phoneNum.getText().toString());
                break;

            // 提交验证码，其中的code表示验证码，如“1357”
            case R.id.bt_find:
                SMSSDK.submitVerificationCode("86", phoneNum.getText().toString(), myCode.getText().toString());
                break;

        }
    }

    //验证码事件处理线程
    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;

            new android.os.Handler(Looper.getMainLooper(), new android.os.Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    try {
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                Toast.makeText(FindPasswordActivity.this, "验证码已发送！", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO 处理错误的结果
                                Toast.makeText(FindPasswordActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();

                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                if (password.getText().toString().equals("")) {
                                    Toast.makeText(FindPasswordActivity.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
                                }else {

                                    FindPasswordTask findPasswordTask = new FindPasswordTask();
                                    findPasswordTask.execute(Constant.URL_LOCAL+"FindPasswordServlet");
                                }
                            } else {
                                // TODO 处理错误的结果
                                Toast.makeText(FindPasswordActivity.this, "验证码不正确！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        //解决在子线程中调用Toast的异常情况处理
                        Looper.prepare();
                        Looper.loop();
                    }
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    //异步任务
    //手机号登录
    public class FindPasswordTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //用post方式传递数据
                httpURLConnection.setRequestMethod("POST");
                //发送数据到服务器端
                OutputStream os = httpURLConnection.getOutputStream();

                //发送json格式的字符串
                User user = new User();
                user.setPassword(password.getText().toString());
                user.setPhonenum(phoneNum.getText().toString());
                Gson gson = new Gson();
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

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            switch (result) {
                //找回密码成功
                case "0":
                    Toast.makeText(FindPasswordActivity.this, "找回密码成功，请登录", Toast.LENGTH_SHORT).show();
                    intent = new Intent(FindPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case "1":
                    Toast.makeText(FindPasswordActivity.this, "密码找回成功失败！", Toast.LENGTH_SHORT).show();
                    break;
                case "2":
                    Toast.makeText(FindPasswordActivity.this, "新密码与近期使用的密码不能相同", Toast.LENGTH_SHORT).show();
                    break;
                //手机号未注册
                case "3":
                    Toast.makeText(FindPasswordActivity.this, "该手机号未注册，请返回注册!", Toast.LENGTH_SHORT).show();
                    break;


            }
            super.onPostExecute(result);
        }
    }

}
