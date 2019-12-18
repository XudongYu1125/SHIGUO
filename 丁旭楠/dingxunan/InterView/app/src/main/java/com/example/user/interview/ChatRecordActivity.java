package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatRecordActivity extends AppCompatActivity {

    private Timer timer;
    private ListView mylistview;
    private List<ChatRecord> mData = new ArrayList<>();
    private ChatRecordAdapter myAdapter = null;
    private int newid = 123; // chatrecord 最新id
    private int recevieid;
    private String input;
    private EditText et;
    private int uuid;
    private Gson gson;
    private static User user;
    private SharedPreferences sharedPreferences = null;
    private String imageurl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myAdapter = new ChatRecordAdapter(ChatRecordActivity.this, mData, R.layout.message_talk);
                    mylistview.setAdapter(myAdapter);
                    break;
                case 1:
                    Toast.makeText(ChatRecordActivity.this, "发送失败", Toast.LENGTH_SHORT);
                    break;
                case 2:
                    ChatRecord chatRecord = (ChatRecord) msg.getData().getSerializable("ChatRecord");
                    myAdapter.addItem(chatRecord);
                    et.setText("");
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_talk);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString("user", null), User.class);

        uuid = getIntent().getIntExtra("UUID", 1);
        String parent = getIntent().getStringExtra("classname");
        String ownersid = getIntent().getStringExtra("ownersId");
        String[] arr = ownersid.split(";");

        if (String.valueOf(user.getUserid()).equals(arr[0])) {
            recevieid = Integer.parseInt(arr[1]);
        } else {
            recevieid = Integer.parseInt(arr[0]);
        }

        searchUserurl();
        Classinit();

        mylistview = (ListView) findViewById(R.id.con_lv);


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Classinit();
            }
        }, 0, 1000);


    }

    //发送消息
    public void send(View view) {
        //发送消息
        et = findViewById(R.id.con_et);
        input = et.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatRecord chatrecord = new ChatRecord();

                String urlStr = "http://10.7.88.185:8080/ShiguoServerSystem/ChatRecord/Add";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dt = new Date();
                String date = sdf.format(dt);

                chatrecord.setContent(input);
                chatrecord.setDate(date);
                chatrecord.setSenderid(user.getUserid());
                chatrecord.setReceiverid(recevieid);
                chatrecord.setUUID(uuid);

                Gson gson = new Gson();

                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(gson.toJson(chatrecord).getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());
                    boolean bl = (Boolean) response.get("isSuccess");
                    if (bl) {
                        String strid = (String) response.get("chatRecordId");
                        int crid = Integer.parseInt(strid);
                        chatrecord.setChatrecordid(crid);
                        newid = crid;
                        Log.e("这是新的chatrecordid", String.valueOf(newid));

                        Bundle bundle = new Bundle();
                        Message msg = new Message();
                        msg.what = 2;
                        bundle.putSerializable("ChatRecord", (Serializable) chatrecord);
                        msg.setData(bundle);
                        handler.sendMessage(msg);

                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }

                    inputStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void sendemoji(View view) {
        //表情
    }

    // 查看个人信息
    public void toself(View view) {
        Intent it1 = new Intent();
        it1.setClass(ChatRecordActivity.this, SelfActivity.class);
        // 判断点击的是sender还是receiver 传递id
        switch (view.getId()) {
            case R.id.con_imgi:
                it1.putExtra("userid", user.getUserid());
                break;
            case R.id.con_imgu:
                it1.putExtra("userid", recevieid);
                break;
        }
        startActivity(it1);
    }

    //从服务器读取ChatRecord,初始化数据
    private void Classinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new ArrayList<>();

                Gson gson = new Gson();

                JSONObject object = new JSONObject();
                try {
                    object.put("UUID", uuid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String urlStr = "http://10.7.88.185:8080/ShiguoServerSystem/ChatRecord/SearchByUUID";

                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());

                    JSONArray jsonArray = response.getJSONArray("list");
                    mData.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ChatRecord cr = new ChatRecord();
                        cr = gson.fromJson(jsonArray.get(i).toString(), ChatRecord.class);
                        mData.add(cr);
                        Log.e("123", cr.toString());
                    }

                    inputStream.close();//关闭数据流
                    conn.disconnect();

                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //刷新
    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new ArrayList<>();
                String urlStr = "http://10.7.88.185:8080/ShiguoServerSystem/ChatRecord/SearchById";
                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }

                    JSONObject response = new JSONObject(stringBuffer.toString());
                    List<ChatRecord> list = (List<ChatRecord>) response.get("list");

                    inputStream.close();//关闭数据流
                    conn.disconnect();
                    mData = list;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static int getUserId() {
        int id = user.getUserid();
        return id;
    }

    // 返回时向MessageActivity传递数据
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        Log.e("返回事件", "123");
        String content = "1231";
        String date = "1232";
        int id = 123;
        for (int j = 0; j < mData.size(); j++) {
            if (mData.get(j).getChatrecordid() == newid) {
                content = mData.get(j).getContent();
                date = mData.get(j).getDate();
                id = mData.get(j).getUUID();
            }
        }
        i.putExtra("content", content);
        i.putExtra("date", date);
        i.putExtra("UUID", id);
        ChatRecordActivity.this.setResult(1, i);
        ChatRecordActivity.this.finish();
    }

    private void searchUserurl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();

                JSONObject object = new JSONObject();
                try {
                    object.put("userID", recevieid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String urlStr = "http://10.7.89.54:8080/ShiguoServerSystem/User/SearchById";

                try {
                    URL url = new URL(urlStr);
                    //获得连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();
                    conn.getOutputStream().write(object.toString().getBytes("UTF-8"));
                    conn.getOutputStream().flush();

                    InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((len = inputStream.read(buffer)) != -1) {
                        stringBuffer.append(new String(buffer, 0, len));
                    }
                    JSONObject response = new JSONObject(stringBuffer.toString());
                    JSONObject jsonObject = response.getJSONObject("user");

                    imageurl = gson.fromJson(jsonObject.toString(), User.class).getImageurl();

                    inputStream.close();//关闭数据流
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}


