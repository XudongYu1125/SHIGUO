package com.example.user.interview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalCertificateActivity extends AppCompatActivity {
    private EditText etCompanyName;
    private EditText etVerification;
    private Button btnCertificate;
    private String name;
    private String vertificate;
    private VertificateMessage vertificateMessage;
    private SharedPreferences sharedPreferences;
    private User user;
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_certificate);
        findViews();
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user",null),User.class);
        Log.e("111",user.toString());
        okHttpClient = new OkHttpClient();

        btnCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etCompanyName.getText().toString();
                vertificate = etVerification.getText().toString();
                vertificateMessage = new VertificateMessage(user.getUserid(),vertificate,name);
                requestData(vertificateMessage);
            }
        });
    }
    private void requestData(VertificateMessage vertificateMessage) {
        Log.e("111",vertificateMessage.toString());
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),new Gson().toJson(vertificateMessage));
        final Request request = new Request.Builder().url(Constant.BASE_IP + Constant.URL_ADD_VERTIFICATE).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e("11",result);
                if (result.equals("0")){
                    Log.e("111","失败");
                }else {
                    Log.e("111","成功");
                    user.setCompanyid(Integer.parseInt(result));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user",new Gson().toJson(user));
                    editor.commit();
                }
            }
        });
    }

    private void findViews() {
        etCompanyName = findViewById(R.id.et_company_name);
        etVerification = findViewById(R.id.et_verification);
        btnCertificate = findViewById(R.id.btn_certificate);        
    }
}
